package org.example.collectora.pinterest;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.lang.IllegalArgumentException;

import org.example.collectora.network.PageLoader;

public class PinParser {
    private static final Pattern DATA_JSON_REGEX = Pattern.compile("(\\{.*\\})");
    private static final Pattern PIN_ID_REGEX = Pattern.compile("[0-9]{2,}");

    public static Pin parse(String url) throws IOException, IllegalArgumentException {
        if (!UrlManager.isUrlSupported(url)) {
            throw new IllegalArgumentException("Url not supported");
        }

        Document page = PageLoader.load(url);
        JsonObject data;

        try {
            data = parseResponseData(parseDetailData(page));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + "; IT's not a pin page!");
        }

        return Pin.builder()
                .id(parsePinId(data))
                .meta(parseMeta(data))
                .board(parseBoard(getOrNull(data, "board")))
                .origin(parsePinner(getOrNull(data , "originPinner")))
                .pinner(parsePinner(getOrNull(data, "pinner")))
                .streamingData(parseStreamingData(getOrNull(data, "imageSpec_orig")))
                .build();
    }

    private static JsonObject getOrNull(JsonObject json, String field) {
        if (json == null) { return null; }
        if (json.has("field")) {
            if (!json.get("field").equals(JsonNull.INSTANCE)) {
                return json.getAsJsonObject("field");
            }
        }
        return null;
    }

    public static String getPinId(String url) {
        if (!UrlManager.isUrlSupported(url)) {
            return "";
        }
        Matcher matcher = PIN_ID_REGEX.matcher(url);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
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
                if (json.has("@type") && "VideoObject".equals(json.get("@type").getAsString())) {
                    return true;
                }
                return false;
            });
    }

    private static JsonObject parseData(Document page, Predicate<JsonObject> isRequired)
                throws IllegalArgumentException {
        for (Element e : page.select("script")) {
            Matcher matcher = DATA_JSON_REGEX.matcher(e.data());
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
        if (data == null) { return null; }
        return PinMeta.builder()
                .createdAt(data.get("createdAt").getAsString())
                .description(data.get("description").getAsString())
                .seoDescription(data.get("seoDescription").getAsString())
                .favoriteUserCount(data.get("favoriteUserCount").getAsInt())
                .repinCount(data.get("repinCount").getAsInt())
                .shareCount(data.get("shareCount").getAsInt())
                .totalReactionCount(data.get("totalReactionCount").getAsInt())
                .build();
    }

    private static String parsePinId(JsonObject data) {
        if (data == null) { return null; }
        return data.get("entityId").getAsString();
    }

    private static Board parseBoard(JsonObject board) {
        if (board == null) { return null; }
        JsonObject owner = board.getAsJsonObject("owner");
        return Board.builder()
                .id(board.get("id").getAsString())
                .url(board.get("url").getAsString())
                .name(board.get("name").getAsString())
                .privacy(board.get("privacy").getAsString())
                .ownerId(owner.get("id").getAsString())
                .ownerEntityId(owner.get("entityId").getAsString())
                .isCollaborative(board.get("isCollaborative").getAsBoolean())
                .build();
    }

    private static Pinner parsePinner(JsonObject pinner) {
        if (pinner == null) { return null; }
        return Pinner.builder()
                .id(pinner.get("id").getAsString())
                .entityId(pinner.get("entityId").getAsString())
                .fullName(pinner.get("fullName").getAsString())
                .username(pinner.get("username").getAsString())
                .followerCount(pinner.get("followerCount").getAsInt())
                .build();
    }

    private static StreamingData parseStreamingData(JsonObject imageSpec) {
        if (imageSpec == null) { return null; }
        return StreamingData.builder()
                .width(imageSpec.get("width").getAsInt())
                .height(imageSpec.get("height").getAsInt())
                .url(imageSpec.get("url").getAsString())
                .build();
    }
}
