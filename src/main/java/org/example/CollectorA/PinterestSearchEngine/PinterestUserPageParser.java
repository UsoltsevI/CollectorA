package org.example.CollectorA.PinterestSearchEngine;

import java.util.List;
import org.jsoup.nodes.Document;

/**
 * @author UsoltsevI
 */
public interface PinterestUserPageParser {
    public void loadPage(Document page);

    public String getUsername();

    public long getFollowersAmount();

    public long getFollowingsAmount();

    public List<String> getPins(int pinsNumber);
}
