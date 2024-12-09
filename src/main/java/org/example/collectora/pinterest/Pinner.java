package org.example.collectora.pinterest;

import lombok.Getter;
import lombok.Builder;

@Getter
@Builder
public class Pinner {
    private final String id;
    private final String entityId;
    private final String fullName;
    private final String username;
    private final int followerCount;
}
