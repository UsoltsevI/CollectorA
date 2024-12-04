package org.example.CollectorA.Printerest;

import java.util.regex.Pattern;

public class UrlManager {
    private static final Pattern SHORT_LINK_REGEX = Pattern.compile("https://pin\\.it/([0-9a-zA-Z_\\-]{7})");
    private static final Pattern FULL_LINK_REGEX = Pattern.compile("https://www.pinterest.com/pin/([0-9]*)");

    public boolean isUrlSupported(String url) {
        if (url == null) { return false; }
        return SHORT_LINK_REGEX.matches(url) || FULL_LINK_REGEX.matches(url);
    }
}