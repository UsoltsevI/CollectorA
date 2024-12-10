package org.example.collectora.pinterest;

import lombok.Getter;
import lombok.Builder;
import org.apache.hadoop.hbase.util.Bytes;

import org.example.collectora.database.ColumnFamily;

@Getter
@Builder
public class StreamingData {
    private final int width;
    private final int height;
    private final String url;

    public ColumnFamily toColumnFamily(byte[] columnFamilyName) {
        return new ColumnFamily(columnFamilyName)
                .put("width".getBytes(), Bytes.toBytes(width))
                .put("height".getBytes(), Bytes.toBytes(height))
                .put("url".getBytes(), Bytes.toBytes(url));
    }
}
