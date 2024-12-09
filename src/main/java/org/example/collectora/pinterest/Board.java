package org.example.collectora.pinterest;

import lombok.Getter;
import lombok.Builder;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Map;

import org.example.collectora.database.ColumnFamily;

@Getter
@Builder
public class Board {
    private final String id;
    private final String url;
    private final String name;
    private final String privacy;
    private final String ownerId;
    private final String ownerEntityId;
    private final boolean isCollaborative;

    public ColumnFamily toColumnFamily(byte[] columnFamilyName) {
        ColumnFamily columnFamily = new ColumnFamily(columnFamilyName);
        Map<byte[],byte[]> columnValues = columnFamily.getColumnValues();
        columnValues.put("id".getBytes(), Bytes.toBytes(id));
        return columnFamily;
    }
}
