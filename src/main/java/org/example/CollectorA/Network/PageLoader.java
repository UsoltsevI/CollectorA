package org.example.CollectorA.Network;

import java.io.IOException;

import org.jsoup.nodes.Document;

public interface PageLoader {
    public Document loadPage(String url) throws IOException;
}