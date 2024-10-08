package org.example.CollectorA.InstagramSearchEngine;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.jsoup.nodes.Document;

/**
 * InstagramSearchEngine implementstion
 * @author UsoltsevI
 */

@Component
public class InstagramSearchEngineImpl implements InstagramSearchEngine {
    @Autowired
    private InstagramDatabaseDispatcher database;
    @Autowired
    private InstagramUserPageParser soup;
    private String currentPageAddress;
    /**
     * Thes method collects information from Instagram pages
     * and saves it to the database;
     */
    @Override
    public void collect() {
        System.out.println("instagram collecting");

    }

}
