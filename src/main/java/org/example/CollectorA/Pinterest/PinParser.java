package org.example.CollectorA.Pinterest;

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

public class PinParser {
    private static final Logger LOG = LoggerFactory.getLogger(PinParser.class);
    private static final Pattern DATA_JSON_REGEX = Pattern.compile("(\\{.*\\})");

    public static Pin parse(String url) throws IOException,NotSupportedFormatException {
        if (!UrlManager.isUrlSupported(url)) {
            throw new IllegalArgumentException("Url not supported");
        }
        JsonObject entry = parseJsonData(PageLoader.load(url));
        return new Pin(parseMeta(entry), parseStreamingData(entry));
    }

    private static JsonObject parseJsonData(Document page) throws IllegalArgumentException {
        Matcher matcher;
        for (Element e : page.select("script")) {
            matcher = DATA_JSON_REGEX.matcher(e.data());
            if (matcher.find()) {
                try {
                    JsonObject json = new Gson().fromGson(matcher.group(1), JsonObject.class);
                    if (json.has("@type") && json.get("@type").getAsString().equals("VideoObject")) {
                        return object;
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
