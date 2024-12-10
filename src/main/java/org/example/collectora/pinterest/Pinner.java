package org.example.collectora.pinterest;

import lombok.Getter;
import lombok.Builder;
import org.apache.hadoop.hbase.util.Bytes;

import org.example.collectora.database.ColumnFamily;

@Getter
@Builder
public class Pinner {
    private final String id;
    private final String entityId;
    private final String fullName;
    private final String username;
    private final int followerCount;

    public ColumnFamily toColumnFamily(byte[] columnFamilyName) {
        return new ColumnFamily(columnFamilyName)
                .put("id".getBytes(), Bytes.toBytes(id))
                .put("entityId".getBytes(), Bytes.toBytes(entityId))
                .put("fullName".getBytes(), Bytes.toBytes(fullName))
                .put("username".getBytes(), Bytes.toBytes(username))
                .put("followerCount".getBytes(), Bytes.toBytes(followerCount));
    }
}
