package org.example.CollectorA.PinterestSearchEngine;

import java.util.List;
import java.util.HashMap;
import java.util.Set;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ClassLoader;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.net.URI;
import java.net.URISyntaxException;
import java.lang.IllegalStateException;
import java.lang.IllegalArgumentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

public class PinterestUserPageParserTest {
    private final String fileNameOne = "/pinterest/user-page-one.html";

    @Test
    public void testScrapingMethods() throws IOException, URISyntaxException {
        TestPageLoader.loadPage("https://ru.pinterest.com/LevysDesigns/_created/");
//        String html = Jsoup.parse(new File)
        PinterestUserPageParser parser = new PinterestUserPageParserImpl();

//        Set<String> fileNames = contents.keySet();
//
//        for (String fileName : fileNames) {
//            parser.loadPage(Jsoup.parse(contents.get(fileName)));
//            List<String> ans = answers.get(fileName);
//            Assertions.assertEquals(ans.get(1), parser.getUsername());
//            Assertions.assertEquals(ans.get(2), Long.toString(parser.getFollowersAmount()));
//        }
    }



}