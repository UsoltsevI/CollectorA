package org.example.CollectorA.PinterestSearchEngine;

import org.jsoup.nodes.Document;

/**
 * This interface defines a methods that parse a pinterest pin page
 * and collects data from it
 * @author UsoltsevI
 */
public interface PinterestPinPageParser {
    public void loadPage(Document page);

    /**
     * This method returns a pin (image or video) size in bytes
     * @return
     */
    public long getPostSize();

    public long getCommentsAmount();

    public long getLikesAmount();

    public String getCreatorName();
}
