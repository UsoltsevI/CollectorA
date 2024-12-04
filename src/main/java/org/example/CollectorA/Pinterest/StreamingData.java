package org.example.CollectorA.Pinterest;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class StreamingData {
    private final int width;
    private final int height;
    private final String url;
}