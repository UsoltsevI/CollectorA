package org.example.CollectorA.InstagramSearchEngine;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.apache.http.client.ClientProtocolException;
import org.brunocvcunha.instagram4j.requests.*;
import org.brunocvcunha.instagram4j.requests.payload.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.example.CollectorA.Database.*;

/**
 * Instagram Search Engine implementstion
 * @author UsoltsevI
 */
@Component
public class InstagramSearchEngineImpl implements InstagramSearchEngine {
    private final Logger log = LoggerFactory.getLogger(HBaseDispatcher.class);
    private Instagram4j instagram;
    private DatabaseDispatcher database;
    private userResult currentUser;

    @Override
    public void collect(String username, String password) {
        System.out.println("instagram collecting");

        try {
            database = new HBaseDispatcher("Instagram");
        } catch (IOException e) {
            log.info(e.getMessage());
            return;
        }

        instagram = new Instagram4j(username, password);
        try {
            instagram.setup();
            instagram.login();

            while (true) {
                saveUserInfo(getUserData(getNextUser()));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    private void saveUserInfo(UserDataModel data) {
        database.saveUser(data);
    }

    private UserDataModel getUserData(InstagramUser user) {
        UserDataModel data = new UserDataModel();
        data.setUserId(user.getPk().toString());
        data.setUserId(user.getIsPrivate());
        data.setFollowersAmount(user.getFollowersCount());
        data.setSubscriptionsAmount(user.getFollowingCount());

        InstagramGetUserReelMediaFeedResult postsResult
                = instagram.sendRequest(new InstagramGetUserReelMediaFeedRequest(user.getPk()));
        List<InstagramFeedItem> items = postsResult.getItems();

        if (items.size < 0) {
            data.setAverageLiskesAmount(0);
            data.setAverageCommentsAmount(0);
            data.setAverageRepostAmount(0);
            data.setAveragePostSize(0);
            data.setAveragePostingPeriod(0);
            return data;
        }

        long totalLikesAmount    = 0;
        long totalCommentsAmount = 0;
        long totalRepostAmount   = 0;
        long totalPostSize       = 0;

        for (InstagramFeedItem item : items) {
            totalLikesAmount    += item.like_count;
            totalCommentsAmount += item.comment_count;
            totalRepostAmount   += 0;
            totalPostSize       = 0;
        }

        long postingPeriod = items.get(items.size() - 1).devise_timestamp - items.get(0).devise_timestamp;

        data.setAverageLiskesAmount(totalLikesAmount / items.size());
        data.setAverageCommentsAmount(totalCommentsAmount / items.size());
        data.setAverageRepostAmount(totalRepostAmount / items.size());
        data.setAveragePostSize(totalPostSize / items.size());
        data.setAveragePostingPeriod(postringPeriod);
        return data;
    }


}
