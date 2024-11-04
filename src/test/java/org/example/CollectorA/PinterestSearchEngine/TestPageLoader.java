package org.example.CollectorA.PinterestSearchEngine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.PolyglotException;
import org.jsoup.Jsoup;
import org.jsoup.Connection;

public class TestPageLoader {
    private static final Pattern fileNamePattern = Pattern.compile("[a-zA-Z0-9]+");

    public static void loadPage(String address) {
        String fileName = getFileName(address);
        String html = compileHTML(wrapHTML(getHTML(address)));
        saveToFile(fileName, html);
    }

    private static void saveToFile(String fileName, String html) {
        try {
            FileWriter writer = new FileWriter(new File(fileName));
            writer.write(html);
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String compileHTML(String jsCode) {
        try (Context context = Context.create()) {
            Value result = context.eval("js", jsCode);
            return result.asString();
        } catch (PolyglotException | IllegalArgumentException | IllegalStateException e) {
            System.out.println("COMPILE HTML FAILURE: " + e.getMessage());
            return "";
        }
    }

    private static String getHTML(String address) {
        try {
            return Jsoup.connect(address).get().html();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    private static String wrapHTML(String html) {
        return "function generateHtml() {\n" +
                " return  `" + html + "`;\n" +
                "}\n" +
                "generateHtml();";
    }

    private static String getFileName(String address) {
        Matcher matcher = fileNamePattern.matcher(address);
        StringBuilder builder = new StringBuilder();
        while (matcher.find()) {
            builder.append(address.substring(matcher.start(), matcher.end()));
            builder.append("-");
        }
        if (builder.length() == 0) {
            return "";
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(".html");
        return builder.toString();
    }
}