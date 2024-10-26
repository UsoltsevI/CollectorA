package org.example.CollectorA.PinterestSearchEngine;

import java.io.File;
import java.io.IOException;
import java.lang.ClassLoader;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

public class PinterestUserPageParserTest {
    private final String fileNameOne = "/pinterest/user-page-one.html";
    @Test
    public void testScrapingMethods() throws IOException, URISyntaxException {
        PinterestUserPageParser parser = new PinterestUserPageParserImpl();
        Document page = Jsoup.parse(getFile(fileNameOne));
        parser.loadPage(page);
        System.out.println(page.toString());
        Assertions.assertEquals("Freepik", parser.getUsername());
        Assertions.assertEquals(4_900_000, parser.getFollowersAmount());

    }

    private File getFile(String fileName) throws IOException, URISyntaxException {
        URI uri = PinterestUserPageParserTest.class.getResource(fileNameOne).toURI();
        return Paths.get(uri).toFile();
    }
}