package org.example.CollectorA.PinterestSearchEngine;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

/**
 * @author UsoltsevI
 */
@Component
public class PinterestPinPageParserImpl implements PinterestPinPageParser {
    Document page;
    @Override
    public void loadPage(Document page) {
        this.page = page;
    }

    @Override
    public long getPostSize() {
        return 0;
    }

    @Override
    public long getCommentsAmount() {
        return 0;
    }

    @Override
    public long getLikesAmount() {
        return 0;
    }

    @Override
    public String getCreatorName() {
        return "";
    }
}
