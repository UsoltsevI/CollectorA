package org.example.CollectorA.Pinterest;

import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class Board {
    private final String id;
    private final String url;
    private final String name;
    private final String privacy;
    private final String ownerId;
    private final String ownerEntityId;
    private final boolean isCollaborative;
}