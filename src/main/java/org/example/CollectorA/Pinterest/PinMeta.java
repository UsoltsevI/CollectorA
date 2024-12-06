package org.example.CollectorA.Pinterest;

import lombok.Getter;
import lombok.Builder;

@Getter
@Builder
public class PinMeta {
    private final String createdAt;
    private final String description;
    private final String seoDescription;
    private final int favoriteUserCount;
    private final int repinCount;
    private final int shareCount;
    private final int totalReactionCount;
}
