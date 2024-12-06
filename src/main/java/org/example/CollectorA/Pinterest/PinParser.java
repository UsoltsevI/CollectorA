package org.example.CollectorA.Pinterest;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.lang.IllegalArgumentException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.example.CollectorA.Network.PageLoader;

public class PinParser {
    private static final Pattern DATA_JSON_REGEX = Pattern.compile("(\\{.*\\})");

    public static Pin parse(String url) throws IOException, IllegalArgumentException {
        if (!UrlManager.isUrlSupported(url)) {
            throw new IllegalArgumentException("Url not supported");
        }

        Document page = PageLoader.load(url);

        JsonObject data  = parseResponseData(parseDetailData(page));

        PinMeta meta = parseMeta(data);
        Board board = parseBoard(data.getAsJsonObject("board"));
        Pinner origin = parsePinner(data.getAsJsonObject("originPinner"));
        Pinner pinner = parsePinner(data.getAsJsonObject("pinner"));
        StreamingData streamingData = parseStreamingData(data.getAsJsonObject("imageSpec_orig"));

        return new Pin(meta, board, origin, pinner, streamingData);
    }

    private static JsonObject parseResponseData(JsonObject json) {
        return json.getAsJsonObject("response")
                .getAsJsonObject("data")
                .getAsJsonObject("v3GetPinQuery")
                .getAsJsonObject("data");
    }

    private static JsonObject parseDetailData(Document page) throws IllegalArgumentException {
        return parseData(page, (json) -> {
                if (json.has("requestParameters")
                        && "CloseupDetailQuery"
                            .equals(json.getAsJsonObject("requestParameters")
                                .get("name").getAsString())) {
                    return true;
                }
                return false;
            });
    }

    private static JsonObject parseVideoData(Document page) throws IllegalArgumentException {
        return parseData(page, (json) -> {
                if (json.has("@type") && json.get("@type").getAsString().equals("VideoObject")) {
                    return true;
                }
                return false;
            });
    }

    private static JsonObject parseData(Document page, Predicate<JsonObject> isRequired)
                throws IllegalArgumentException {
        Matcher matcher;
        for (Element e : page.select("script")) {
            matcher = DATA_JSON_REGEX.matcher(e.data());
            if (matcher.find()) {
                try {
                    JsonObject json = new Gson().fromJson(matcher.group(1), JsonObject.class);
                    if (isRequired.test(json)) {
                        return json;
                    }
                } catch (JsonSyntaxException ignored) { }
            }
        }
        throw new IllegalArgumentException();
    }

    private static PinMeta parseMeta(JsonObject data) {
        String createdAt = data.get("createdAt").getAsString();
        String description = data.get("description").getAsString();
        String seoDescription = data.get("seoDescription").getAsString();
        int favoriteUserCount = data.get("favoriteUserCount").getAsInt();
        int repinCount = data.get("repinCount").getAsInt();
        int shareCount = data.get("shareCount").getAsInt();
        int totalReactionCount = data.get("totalReactionCount").getAsInt();
        return new PinMeta(createdAt, description, seoDescription
                , favoriteUserCount, repinCount, shareCount, totalReactionCount);
    }

    private static Board parseBoard(JsonObject board) {
        String id   = board.get("id").getAsString();
        String url  = board.get("url").getAsString();
        String name = board.get("name").getAsString();
        String privacy = board.get("public").getAsString();
        JsonObject owner     = board.getAsJsonObject("owner");
        String ownerId       = owner.get("id").getAsString();
        String ownerEntityId = owner.get("entityId").getAsString();
        boolean isCollaborative = board.get("isCollaborative").getAsBoolean();
        return new Board(id, url, name, privacy, ownerId, ownerEntityId, isCollaborative);
    }

    private static Pinner parsePinner(JsonObject pinner) {
        String id       = pinner.get("id").getAsString();
        String entityId = pinner.get("entityId").getAsString();
        String fullName = pinner.get("fullName").getAsString();
        String username = pinner.get("username").getAsString();
        int followerCount = pinner.get("followerCount").getAsInt();
        return new Pinner(id, entityId, fullName, username, followerCount);
    }

    private static StreamingData parseStreamingData(JsonObject imageSpec) {
        int width = imageSpec.get("width").getAsInt();
        int heigh = imageSpec.get("heigh").getAsInt();
        String url = imageSpec.get("url").getAsString();
        return new StreamingData(width, heigh, url);
    }
}
