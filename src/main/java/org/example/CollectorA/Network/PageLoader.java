package org.example.CollectorA.Network;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public interface PageLoader {
    public Document load(String url) throws IOException {
        return Jsoup.connect(url).followRedirects(true).get();
    }
}