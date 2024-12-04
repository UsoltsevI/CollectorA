package org.example.CollectorA.Pinterest;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.IllegalArgumentException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.example.CollectorA.Network.PageLoader;

public class PinParser {
//    private static final Logger LOG = LoggerFactory.getLogger(PinParser.class);
    private static final Pattern DATA_JSON_REGEX = Pattern.compile("(\\{.*\\})");
    private static final String[] contentTypes = { "VideoObject" };

    public static Pin parse(String url) throws IOException, IllegalArgumentException {
        if (!UrlManager.isUrlSupported(url)) {
            throw new IllegalArgumentException("Url not supported");
        }
//        System.out.println(PageLoader.load(url).toString());
        JsonObject json = parseJsonData(PageLoader.load(url));
//
        System.out.println(json.toString());
//
        return new Pin(parseMeta(json), parseStreamingData(json));
    }

    private static JsonObject parseJsonData(Document page) throws IllegalArgumentException {
        Matcher matcher;
        for (Element e : page.select("script")) {
            matcher = DATA_JSON_REGEX.matcher(e.data());
            if (matcher.find()) {
                try {
                    JsonObject json = new Gson().fromJson(matcher.group(1), JsonObject.class);
                    if (json.has("@type")) {
                        String type = json.get("@type").getAsString();
                        for (String t : contentTypes) {
                            if (type.equals(t)) {
                                return json;
                            }
                        }
                    }
                } catch (JsonSyntaxException ignored) { }
            }
        }
        throw new IllegalArgumentException();
    }

    private static PinMeta parseMeta(JsonObject json) {
        String name = json.get("name").getAsString();
        String description = json.get("description").getAsString();
        String url = json.get("@id").getAsString();
        return new PinMeta(name, description, url);
    }

    private static StreamingData parseStreamingData(JsonObject json) {
        int width = Integer.parseInt(json.get("width").getAsString());
        int heigh = Integer.parseInt(json.get("heigh").getAsString());
        String url = json.get("contentUrl").getAsString();
        return new StreamingData(width, heigh, url);
    }
}
