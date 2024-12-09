package org.example.collectora.network;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PageLoader {
    public static Document load(String url) throws IOException {
        return Jsoup.connect(url).followRedirects(true).get();
    }
}
