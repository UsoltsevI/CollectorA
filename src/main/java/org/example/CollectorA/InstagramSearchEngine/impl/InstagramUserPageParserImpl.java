package org.example.CollectorA.InstagramSearchEngine;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import org.example.CollectorA.Database.UserDataModel;


@Component
public class InstagramUserPageParserImpl implements InstagramUserPageParser {
    @Override
    public String getUserID(Document page) {
        return "";
    }
    @Override
    public Long getSubscriptionsAmount(Document page) {
        return -1l;
    }

    @Override
    public Long getFollowersAmount(Document page) {
        return -1l;
    }

    @Override
    public boolean isPrivate(Document page) {
        return false;
    }

    @Override
    public Long getAverageLikesAmount(Document page) {
        return -1l;
    }

    @Override
    public Long getAverageCommentsAmount(Document page) {
        return -1l;
    }

    @Override
    public Long getAverageRepostAmount(Document page) {
        return -1l;
    }

    @Override
    public Long getAveragePostSize(Document page) {
        return -1l;
    }

    @Override
    public Long getAveragePostingPostsFrequency(Document page) {
        return -1l;
    }

    @Override
    public UserDataModel getData(Document page) {
        return null;
    }
}