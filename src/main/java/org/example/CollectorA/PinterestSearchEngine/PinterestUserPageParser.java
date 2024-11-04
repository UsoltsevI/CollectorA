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

    /**
     * Return the pin ids found on the page. The number of returned
     * pins is less or equal than pinsNumber.
     * @param pinsNumber - maximum number of pins to return
     * @return list of pins id
     */
    public List<String> getPins(int pinsNumber);
}
