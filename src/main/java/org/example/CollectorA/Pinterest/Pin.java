package org.example.CollectorA.Pinterest;

import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class Pin {
    private final PinMeta meta;
    private final Board board;
    private final Pinner origin;
    private final Pinner pinner;
    private final StreamingData streamingData;
}
