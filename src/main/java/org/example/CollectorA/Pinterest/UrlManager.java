package org.example.CollectorA.Pinterest;

import java.util.regex.Pattern;

public class UrlManager {
    private static final Pattern SHORT_LINK_REGEX = Pattern.compile("https://pin\\.it/([0-9a-zA-Z_\\-]{7})");
    private static final Pattern FULL_LINK_REGEX = Pattern.compile("https://([a-z]*).pinterest.com/pin/([0-9]*)(/?)");

    public static boolean isUrlSupported(String url) {
        if (url == null) { return false; }
        return SHORT_LINK_REGEX.matcher(url).matches()
                || FULL_LINK_REGEX.matcher(url).matches();
    }
}
