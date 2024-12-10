package org.example.collectora.database;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Hashtable;

@Getter
public class ColumnFamily {
    private final byte[] name;
    private final Map<byte[],byte[]> columnValues; // <columnName (qualifier),value>

    public ColumnFamily(String name) {
        this.name = name.getBytes();
        columnValues = new Hashtable<>();
    }

    public ColumnFamily(byte[] name) {
        this.name = Arrays.copyOf(name, name.length);
        columnValues = new Hashtable<>();
    }

    public ColumnFamily put(byte[] column, byte[] value) {
        columnValues.put(Arrays.copyOf(column, column.length), Arrays.copyOf(value, value.length));
        return this;
    }

    public byte[] get(byte[] column) {
        return columnValues.get(column);
    }

    public byte[] delete(byte[] column) {
        return columnValues.remove(column);
    }
}
