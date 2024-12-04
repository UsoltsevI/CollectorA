package org.example.CollectorA.Pinterest;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class Pin {
    private final PinMeta meta;
    private final StreamingData streamingData;
}