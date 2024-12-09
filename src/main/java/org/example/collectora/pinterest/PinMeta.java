package org.example.collectora.pinterest;

import lombok.Getter;
import lombok.Builder;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Map;

import org.example.collectora.database.ColumnFamily;

@Getter
@Builder
public class PinMeta {
    private final String createdAt;
    private final String description;
    private final String seoDescription;
    private final int favoriteUserCount;
    private final int repinCount;
    private final int shareCount;
    private final int totalReactionCount;

    public ColumnFamily toColumnFamily(byte[] columFamilyName) {
        ColumnFamily columnFamily = new ColumnFamily(columFamilyName);
        Map<byte[],byte[]> columnValues = columnFamily.getColumnValues();
        columnValues.put("createdAt".getBytes(),        createdAt.getBytes());
        columnValues.put("description".getBytes(),      description.getBytes());
        columnValues.put("seoDescription".getBytes(),   seoDescription.getBytes());
        columnValues.put("favoriteUserCount".getBytes(), Bytes.toBytes(favoriteUserCount));
        columnValues.put("repinCount".getBytes(),       Bytes.toBytes(repinCount));
        columnValues.put("shareCount".getBytes(),       Bytes.toBytes(shareCount));
        columnValues.put("totalReactionCount".getBytes(), Bytes.toBytes(totalReactionCount));
        return columnFamily;
    }
}
