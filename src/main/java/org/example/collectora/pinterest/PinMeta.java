package org.example.collectora.pinterest;

import lombok.Getter;
import lombok.Builder;
import org.apache.hadoop.hbase.util.Bytes;

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
        return new ColumnFamily(columFamilyName)
                .put("createdAt".getBytes(), createdAt.getBytes())
                .put("description".getBytes(), description.getBytes())
                .put("seoDescription".getBytes(), seoDescription.getBytes())
                .put("favoriteUserCount".getBytes(), Bytes.toBytes(favoriteUserCount))
                .put("repinCount".getBytes(), Bytes.toBytes(repinCount))
                .put("shareCount".getBytes(), Bytes.toBytes(shareCount))
                .put("totalReactionCount".getBytes(), Bytes.toBytes(totalReactionCount));
    }
}
