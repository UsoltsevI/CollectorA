package org.example.collectora.database;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.AutoCloseable;
import java.util.Vector;
import java.util.List;
import java.util.Map;

public class HBase implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(HBase.class);
    private Connection connection;
    private Admin admin;

    public void connect(Map<String,String> properties) throws IOException {
        Configuration config = HBaseConfiguration.create();

        for (Map.Entry<String,String> entry : properties.entrySet()) {
            config.set(entry.getKey(), entry.getValue());
        }

        connection = ConnectionFactory.createConnection(config);
        admin = connection.getAdmin();
    }

    public void createTable(byte[] tableName, List<byte[]> columnFamilies) throws IOException {
        TableName tName = TableName.valueOf(tableName);
        if (!admin.tableExists(tName)) {
            HTableDescriptor tableDescriptor = new HTableDescriptor(tName);
            for (byte[] columnFamily : columnFamilies) {
                tableDescriptor.addFamily(new HColumnDescriptor(columnFamily));
            }
            admin.createTable(tableDescriptor);
            LOGGER.info("Table '" + new String(tableName) + "' created.");
        } else {
            LOGGER.info("Table '" + new String(tableName) + "' already exists.");
        }
    }

    public void put(byte[] tableName, Row row) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(row.getName());
        for (Map.Entry<byte[],ColumnFamily> cf : row.getColumnFamilies().entrySet()) {
            for (Map.Entry<byte[],byte[]> cv : cf.getValue().getColumnValues().entrySet()) {
                put.addColumn(cf.getKey(), cv.getKey(), cv.getValue());
            }
        }
        table.put(put);
    }

    public Row get(byte[] tableName, byte[] rowName) throws IOException {
        Result result = connection.getTable(TableName.valueOf(tableName)).get(new Get(rowName));
        if (result.isEmpty()) {
            return new Row(rowName);
        }
        Cell[] cells = result.rawCells();
        Row row = new Row(rowName);
        Map<byte[],ColumnFamily> columnFamilies = row.getColumnFamilies();
        for (Cell cell : cells) {
            byte[] family = cell.getFamilyArray();
            byte[] qualifier = cell.getQualifierArray();
            byte[] value = cell.getValueArray();
            columnFamilies.putIfAbsent(family, new ColumnFamily(family));
            columnFamilies.get(family).put(qualifier, value);
        }
        return row;
    }

    public Row deleteRow(byte[] tableName, byte[] rowName) throws IOException {
        Row row = get(tableName, rowName);
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(rowName);
        table.delete(delete);
        LOGGER.info("Row '" + new String(rowName) + "' deleted");
        return row;
    }

    public void deleteTable(byte[] tableName) throws IOException {
        TableName tName = TableName.valueOf(tableName);
        if (admin.tableExists(tName)) {
            admin.disableTable(tName);
            admin.deleteTable(tName);
            LOGGER.info("Table '" + new String(tableName) + "' deleted.");
        } else {
            LOGGER.info("Table '" + new String(tableName) + "' does not exist.");
        }
    }

    @Override
    public void close() throws Exception {
        try {
            connection.close();
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        LOGGER.info("Connection is closed");
    }
}
