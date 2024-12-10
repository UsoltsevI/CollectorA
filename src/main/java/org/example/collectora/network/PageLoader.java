package org.example.collectora.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class PageLoader {
    public static Document load(String url) throws IOException {
        return Jsoup.connect(url).followRedirects(true).get();
    }
}
