package org.example.CollectorA.InstagramSearchEngine;

import java.io.IOException;
import java.util.List;

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
    @Autowired
    private DatabaseDispatcher database;
    private InstagramUser currentUser;

    @Override
    public void collect(String username, String password) {
        System.out.println("instagram collecting");

        if (database.connect(getTableName())) {
            log.info("Instagram connection to the database is successful");
        } else {
            log.info("Instagram connection to the database is failed");
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

    private String getTableName() {
        return "Instagram";
    }

    private void saveUserInfo(UserDataModel data) {
        if (database.saveData(data)) {
            log.info("user:" + data.toString() + "saved");
        } else {
            log.info("user saving failure");
        }
    }

    private UserDataModel getUserData(InstagramUser user) {
        UserDataModel data = new UserDataModel();
        data.setId(Long.toString(user.getPk()));
        data.setIsPrivate(user.is_private);
        data.setFollowersAmount(Long.valueOf(user.follower_count));
        data.setSubscriptionsAmount(Long.valueOf(user.following_count));

//        InstagramGetUserReelMediaFeedResult postsResult
//                = instagram.sendRequest(new InstagramGetUserReelMediaFeedRequest(user.getPk()));
        InstagramFeedResult postsResult;
        try {
            postsResult = instagram.sendRequest(new InstagramUserFeedRequest(user.getPk()));
        } catch (IOException e) {
            log.info(e.getMessage());
            return data;
        }

        List<InstagramFeedItem> items = postsResult.getItems();

        if (items.size() < 0) {
            data.setAverageLikesAmount   (0l);
            data.setAverageCommentsAmount(0l);
            data.setAverageRepostAmount  (0l);
            data.setAveragePostSize      (0l);
            data.setAveragePostingPeriod (0l);
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
            totalPostSize       += item.original_width * item.original_height * 4; // ???
        }

        long postingPeriod = items.get(items.size() - 1).device_timestamp - items.get(0).device_timestamp;

        data.setAverageLikesAmount   (totalLikesAmount    / items.size());
        data.setAverageCommentsAmount(totalCommentsAmount / items.size());
        data.setAverageRepostAmount  (totalRepostAmount   / items.size());
        data.setAveragePostSize      (totalPostSize       / items.size());
        data.setAveragePostingPeriod (postingPeriod);
        return data;
    }

    private InstagramUser getNextUser() {
        return null;
    }
}
