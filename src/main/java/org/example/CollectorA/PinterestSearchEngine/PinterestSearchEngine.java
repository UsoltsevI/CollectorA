package org.example.CollectorA.PinterestSearchEngine;

/**
 * Pinterest Search Engine interface
 * @author UsoltsevI
 */
public interface PinterestSearchEngine {
    /**
     * This method starts the search engine work.
     * It collects data and saves it to the database
     * @param accessToken to log in to Pinterest
     */
    public void collect(String accessToken);
}
