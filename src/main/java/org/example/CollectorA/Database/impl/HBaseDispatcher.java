package org.example.CollectorA.Database;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements HBase Dispatcher
 * @author UsoltsevI
 */
@Component
public class HBaseDispatcher implements DatabaseDispatcher, Closeable {
    private TableName tableName;
    private Connection connection;
    private Table table;
    private List<String> columnFamilies;
    private final Logger log = LoggerFactory.getLogger(HBaseDispatcher.class);;

    /**
     * Creates a connection with the database and and creates a table
     * if it doensn't exist
     * @param tableName
     */
    HBaseDispatcher(String tableName) throws IOException {
        this.tableName = TableName.valueOf(tableName);
        columnFamilies = new ArrayList<>();
        columnFamilies.add("users");
        loadTable();
    }

    private void loadTable() throws IOException {
        Configuration config  = HBaseConfiguration.create();
        connection = ConnectionFactory.createConnection(config);
        Admin admin = connection.getAdmin();
        log.info("Connection is created");

        if (!admin.tableExists(tableName)) {
            log.info("Creating table '" + tableName.getNameAsString() + "'");
            HTableDescriptor htable = new HTableDescriptor(tableName);
            for (String name : columnFamilies) {
                htable.addFamily(new HColumnDescriptor(name));
            }
            admin.createTable(htable);
            log.info("Table is created");
        } else {
            log.info("Table '" + tableName.getNameAsString() + "' already exists");
        }

        table = connection.getTable(tableName);
        admin.close();
    }

    @Override
    public boolean saveData(UserDataModel data) {
        Put put = new Put(Bytes.toBytes(data.getUserId()));
        HashMap<byte[],byte[]> fields = data.getFieldsAsBytes();
        for (Map.Entry<byte[],byte[]> field : fields.entrySet()) {
            put.addColumn(Bytes.toBytes(columnFamilies.get(0))
                            , field.getKey()
                            , field.getValue());
        }
        try {
            table.put(put);
        } catch (IOException e) {
            log.info(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean isAlreadyRecorded(UserDataModel data) {
        try {
            table.exists(new Get(Bytes.toBytes(data.getUserId())));
            return true;
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return false;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        log.info("Connection is closed");
    }
}
