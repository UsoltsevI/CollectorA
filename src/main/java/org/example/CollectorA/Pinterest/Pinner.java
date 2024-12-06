package org.example.CollectorA.Pinterest;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class Pinner {
    private final String id;
    private final String entityId;
    private final String fullName;
    private final String username;
    private final int followerCount;
}
