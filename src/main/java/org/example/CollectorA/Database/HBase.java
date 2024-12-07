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
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements Database interface to communicate with HBase
 * @author UsoltsevI
 */
@Component
public class HBase implements Database {
    private TableName tableName;
    private Connection connection;
    private Table table;
    private List<String> columnFamilies;
    private final Logger log = LoggerFactory.getLogger(HBase.class);

    @Override
    public boolean connect(String tableName) {
        this.tableName = TableName.valueOf(tableName);
        columnFamilies = new ArrayList<>();
        columnFamilies.add("users");
        try {
            loadTable();
        } catch (IOException e) {
            log.info(e.getMessage());
            return false;
        }
        return true;
    }

    private void loadTable() throws IOException {
        Configuration config  = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "localhost");
        config.set("hbase.zookeeper.property.clientPort", "2181");

        try (Connection connection = ConnectionFactory.createConnection(config)){
            Admin admin = connection.getAdmin();
            log.info("Connection is created");

            if (!admin.tableExists(tableName)) {
                log.info("Creating table '" + tableName.getNameAsString() + "'");
                ColumnFamilyDescriptor columnFamilyDescriptor
                        = new ModifyableColumnFamilyDescriptor(
                        Bytes.toBytes(getUsersColumnFamilyName()));
                TableDescriptorBuilder htable = TableDescriptorBuilder
                        .newBuilder(tableName)
                        .setColumnFamily(columnFamilyDescriptor);
                admin.createTable(htable.build());
                log.info("Table is created");
            } else {
                log.info("Table '" + tableName.getNameAsString() + "' already exists");
            }
            table = connection.getTable(tableName);
            admin.close();
        } catch (IOException ignored) {}
    }

    private String getUsersColumnFamilyName() {
        return "users";
    }

    @Override
    public boolean saveData(DataModel data) {
        Put put = new Put(data.getId());
        HashMap<byte[],byte[]> fields = data.getFieldsAsBytes();
        for (Map.Entry<byte[],byte[]> field : fields.entrySet()) {
            put.addColumn(Bytes.toBytes(columnFamilies.get(0))
                            , field.getKey()
                            , field.getValue());
        }
        try {
            table.put(put);
            return true;
        } catch (IOException e) {
            log.info(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isAlreadyRecorded(DataModel data) {
        try {
            table.exists(new Get(data.getId()));
            return true;
        } catch (IOException e) {
            log.info(e.getMessage());
            return false;
        }
    }

    @Override
    public void close() throws Exception {
        try {
            connection.close();
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        log.info("Connection is closed");
    }
}
