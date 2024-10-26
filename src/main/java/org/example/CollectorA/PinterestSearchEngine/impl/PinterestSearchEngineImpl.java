package org.example.CollectorA.PinterestSearchEngine;

import java.util.List;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.datafaker.Faker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.example.CollectorA.Database.DatabaseDispatcher;


/**
 * This class implements Pinterest Search Engine
 * @author UsoltsevI
 */
@Component
public class PinterestSearchEngineImpl implements PinterestSearchEngine {
    private final String userPagePrefix = "https://ru.pinterest.com/";
    private final String userPagePinsPostfix = "/_created";
    private final String pinPagePrefix  = "https://ru.pinterest.com/pin/";
    private final String tableName = "pinterest";
    private final int numberOfPinsToCheck = 10;
    private final Logger log  = LoggerFactory.getLogger(PinterestSearchEngineImpl.class);
    private Faker faker = new Faker();
    @Autowired
    private DatabaseDispatcher database;
    @Autowired
    private PinterestUserPageParser userParser;
    @Autowired
    private PinterestPinPageParser pinParser;

    @Override
    public void collect() {
        log.info("Collecting started");

        if (database.connect(tableName)) {
            log.info("Connected to the database");
        } else {
            log.info("Connection to the database is failed");
            return;
        }

        int missNumber = 100;
        while (true) {
            try {
                saveUserInfo(getUserData(getUserPage(getNextUsername())));
            } catch (IOException e) {
                missNumber--;
                log.info(e.getMessage());
            }

            if (missNumber <= 0) {
                return;
            }
        }
    }

    private Document getUserPage(String username) throws IOException {
        return Jsoup.connect(userPagePrefix + username + userPagePinsPostfix).get();
    }

    private Document getPinPage(String pinId) throws IOException {
        return Jsoup.connect(pinPagePrefix + pinId).get();
    }

    private void saveUserInfo(PinterestUserDataModel data) {
        if (database.saveData(data)) {
            log.info("user" + data.toString() + "is saved");
        } else {
            log.info("user saving failure");
        }
    }

    private String getNextUsername() {
        return faker.name().username();
    }

    private PinterestUserDataModel getUserData(Document page) throws IOException {
        userParser.loadPage(page);
        PinterestUserDataModel data = new PinterestUserDataModel();

        data.setId(userParser.getUsername());
        data.setSubscriptionsAmount(userParser.getFollowingsAmount());
        data.setFollowersAmount(userParser.getFollowersAmount());

        List<String> pinList = userParser.getPins(numberOfPinsToCheck);

        if (pinList.isEmpty()) {
            return data;
        }

        long likesAmount = 0;
        long commentsAmount = 0;
        long postsSize = 0;
        int processedNumber = 0;

        for (String pinId : pinList) {
            try {
                pinParser.loadPage(getPinPage(pinId));
                likesAmount     += pinParser.getLikesAmount();
                commentsAmount  += pinParser.getCommentsAmount();
                postsSize       += pinParser.getPostSize();
                processedNumber += 1;
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }

        if (processedNumber == 0) {
            throw new IOException("processedNumber == 0");
        }

        data.setAverageLikesAmount(likesAmount / processedNumber);
        data.setAverageCommentsAmount(commentsAmount / processedNumber);
        data.setAveragePostSize(commentsAmount / processedNumber);

        return data;
    }
}
