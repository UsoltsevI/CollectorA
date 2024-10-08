package org.example.CollectorA.InstagramSearchEngine;

import org.jsoup.nodes.Document;
import org.example.CollectorA.Database.UserDataModel;

/**
 * This class parces Instagram pages and
 *
 * @author UsoltsevI
 */

public interface InstagramUserPageParser {
    /**
     * Retrieves data about User Id (as String) from the page
     * @param page Instagram user page as Jsoup document
     * @return userID as String, empty string if user ID is not found
     */
    public String getUserID(Document page);

    /**
     * Retrieves data about subscriptions amount from the page
     * @param page Instagram user page as Jsoup document
     * @return amount of Subscriptions, -1 in case of an error
     */
    public Long getSubscriptionsAmount(Document page);

    /**
     * Retrives data about followera amount from the page.
     * @param page Instagram user page as Jsoup document
     * @return amount of Followers, -1 in case of an error
     */
    public Long getFollowersAmount(Document page);

    /**
     * Retrieves data about privacy from the page.
     * @param page Instagram user page as Jsoup document
     * @return true, if the page is private, false, if not
     */
    public boolean isPrivate(Document page);

    /**
     * Retrieves data from the page and calculates the average likes amount for the last 10 posts.
     * If the user has less than 10 posts, then he calculates the average of the existing ones.
     * @param page - Instagram user page as Jsoup document
     * @return Average likes amound for the last 10 posts. -1 in case of an error
     */
    public Long getAverageLikesAmount(Document page);

    /**
     * Retrieves data from the page and calculates the average comments amount for the last 10 posts.
     * If the user has less than 10 posts, then he calculates the average of the existing ones.
     * @param page - Instagram user page as Jsoup document
     * @return Average comment amount for the last 10 pages. -1 in case of an error
     */
    public Long getAverageCommentsAmount(Document page);

    /**
     * Retrieves data from the page and calculates the average repost amount for the last 10 posts.
     * If the user has less than 10 posts, then he calculates the average of the existing ones.
     * @param page - Instagram user page as Jsoup document
     * @return Avegare repost amount for the last 10 pages. -1 in case of an error
     */
    public Long getAverageRepostAmount(Document page);

    /**
     * Retrieves data from the page and calculates the average post size for the last 10 posts.
     * If the user has less than 10 posts, then he calculates the average of the existing ones.
     * @param page - Instagram user page as Jsoup document
     * @return Average Post Size for the last 10 pages. -1 in case of an error
     */
    public Long getAveragePostSize(Document page);

    /**
     * Retrieves data from the page and calculates the average postring posts frequency for the last 10 posts.
     * If the user has less than 10 posts, then he calculates the average of the existing ones.
     * @param page - Instagram user page as Jsoup document
     * @return Average Posting Posts Frequency for the last 10 posts. -1 in case of an error.
     */
    public Long getAveragePostingPostsFrequency(Document page);

    /**
     * Retrieves all data described in UserDataModel from the page.
     * @param page - Instagram user page as Jsoup document
     * @return All data described in UserDataModel from the page, null in case of an error.
     */
    public UserDataModel getData(Document page);
}
