package org.example.collectora.database;

import lombok.Getter;

import java.util.Map;
import java.util.Hashtable;
import java.util.Arrays;

@Getter
public class Row {
    private final byte[] name;
    private final Map<byte[],ColumnFamily> columnFamilies;

    public Row(String name) {
        this.name = name.getBytes();
        columnFamilies = new Hashtable<>();
    }

    public Row(byte[] name) {
        this.name = Arrays.copyOf(name, name.length);
        columnFamilies = new Hashtable<>();
    }

    public void put(byte[] columnFamilyName, ColumnFamily columnFamily) {
        columnFamilies.put(Arrays.copyOf(columnFamilyName, columnFamilyName.length), columnFamily);
    }

    public ColumnFamily get(byte[] columnFamilyName) {
        return columnFamilies.get(columnFamilyName);
    }

    public ColumnFamily delete(byte[] columnFamilyName) {
        return columnFamilies.remove(columnFamilyName);
    }
}
