package org.example.collectora.pinterest;

import lombok.Getter;
import lombok.Builder;

@Getter
@Builder
public class StreamingData {
    private final int width;
    private final int height;
    private final String url;
}
