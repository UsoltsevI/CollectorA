package org.example.CollectorA.InstagramSearchEngine;

/**
 * An interface that performs the functions of collecting data from the instagram platform
 * @author UsoltsevI
 */
public interface InstagramSearchEngine {
    /**
     * This method starts the search engine work.
     * It collects data and saves it to the database
     * @param username to log in to Instagram
     * @param password to log in to Instagram
     */
    public void collect(String username, String password);
}
