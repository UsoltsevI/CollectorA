package org.example.CollectorA.InstagramSearchEngine;

import java.io.IOException;
import java.util.List;
import java.util.Iterator;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.apache.http.client.ClientProtocolException;
import org.brunocvcunha.instagram4j.requests.*;
import org.brunocvcunha.instagram4j.requests.payload.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.example.CollectorA.Database.DatabaseDispatcher;
import org.example.CollectorA.Database.UserDataModel;

/**
 * Instagram Search Engine implementstion
 * @author UsoltsevI
 */
@Component
public class InstagramSearchEngineImpl implements InstagramSearchEngine {
    private Instagram4j instagram;
    private InstagramUser currentUser;
    private Iterator<InstagramUserSummary> users;
    @Autowired
    private DatabaseDispatcher database;
    private final Logger log = LoggerFactory.getLogger(InstagramSearchEngineImpl.class);

    @Override
    public void collect(String username, String password) {
        log.info("Collecting started");

        if (database.connect(getTableName())) {
            log.info("Connected to the database");
        } else {
            log.info("Connection to the database is failed");
            return;
        }

        instagram = new Instagram4j(username, password);
        try {
            instagram.setup();
            instagram.login();

            currentUser = instagram
                    .sendRequest(new InstagramSearchUsernameRequest(username))
                    .getUser();

            while (true) {
                saveUserInfo(getUserData(getNextUser()));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        data.setPrivate(user.is_private);
        data.setFollowersAmount(Long.valueOf(user.follower_count));
        data.setSubscriptionsAmount(Long.valueOf(user.following_count));

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
        try {
            if (users.hasNext()) {
                currentUser = instagram
                        .sendRequest(new InstagramSearchUsernameRequest(users.next().getUsername()))
                        .getUser();
            } else {
                List<InstagramUserSummary> usersList
                        = instagram
                        .sendRequest(new InstagramGetUserFollowersRequest(currentUser.getPk()))
                        .getUsers();
                usersList.addAll(instagram
                        .sendRequest(new InstagramGetUserFollowingRequest(currentUser.getPk()))
                        .getUsers());
                users = usersList.iterator();

                if (users.hasNext()) {
                    currentUser = instagram
                            .sendRequest(new InstagramSearchUsernameRequest(users.next().getUsername()))
                            .getUser();
                } else {
                    log.info("users is empty, last user: " + currentUser.toString());
                }
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        }

        return currentUser;
    }
}
