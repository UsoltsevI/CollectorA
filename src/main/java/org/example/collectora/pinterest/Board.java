package org.example.collectora.pinterest;

import lombok.Getter;
import lombok.Builder;
import org.apache.hadoop.hbase.util.Bytes;

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
        return new ColumnFamily(columnFamilyName)
                .put("id".getBytes(), Bytes.toBytes(id))
                .put("url".getBytes(), Bytes.toBytes(url))
                .put("name".getBytes(), Bytes.toBytes(name))
                .put("privacy".getBytes(), Bytes.toBytes(privacy))
                .put("ownerId".getBytes(), Bytes.toBytes(ownerId))
                .put("ownerEntityId".getBytes(), Bytes.toBytes(ownerEntityId))
                .put("isCollaborative".getBytes(), Bytes.toBytes(isCollaborative));
    }
}
