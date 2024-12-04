package org.example.CollectorA.Pinterest;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class PinMeta {
    private final String name;
    private final String description;
    private final String creator;
    private final String url;
}