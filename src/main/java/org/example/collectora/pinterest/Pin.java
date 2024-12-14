package org.example.collectora.pinterest;

import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.ArrayList;

import org.example.collectora.database.Row;

@Getter
@Builder
@AllArgsConstructor
public class Pin {
    private static final byte[] META_CF = "meta".getBytes();
    private static final byte[] PINNER_CF = "pinner".getBytes();
    private static final byte[] ORIGIN_CF = "origin".getBytes();
    private static final byte[] BOARD_CF = "board".getBytes();
    private static final byte[] STREAMING_DATA_CF = "streaming_data".getBytes();
    private final String id;
    private final PinMeta meta;
    private final Board board;
    private final Pinner origin;
    private final Pinner pinner;
    private final StreamingData streamingData;

    public static List<byte[]> getColumnFamilies() {
        List<byte[]> list = new ArrayList<>();
        list.add(META_CF);
        list.add(PINNER_CF);
        list.add(ORIGIN_CF);
        list.add(BOARD_CF);
        list.add(STREAMING_DATA_CF);
        return list;
    }

    public Row toRow() {
        return new Row(id.getBytes())
                .put(META_CF, meta.toColumnFamily(META_CF))
                .put(PINNER_CF, pinner.toColumnFamily(PINNER_CF))
                .put(ORIGIN_CF, origin.toColumnFamily(ORIGIN_CF))
                .put(BOARD_CF, board.toColumnFamily(BOARD_CF))
                .put(STREAMING_DATA_CF, streamingData.toColumnFamily(STREAMING_DATA_CF));
    }
}
