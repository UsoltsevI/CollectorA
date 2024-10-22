package org.example.CollectorA.PinterestSearchEngine;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.chrisdempewolf.pinterest.Pinterest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.example.CollectorA.Database.DatabaseDispatcher;

/**
 * This class implements Pinterest Search Engine
 * @author UsoltsevI
 */
@Component
public class PinterestSearchEngineImpl implements PinterestSearchEngine {
    private Pinterest pinterest;
    @Autowired
    private DatabaseDispatcher database;
    private final Logger log = LoggerFactory.getLogger(PinterestSearchEngineImpl.class);

    @Override
    public void collect(String accessToken) {
        log.info("Collecting started");
        if (database.connect(getTableName())) {
            log.info("Connected to the database");
        } else {
            log.info("Connection to the database is failed");
            return;
        }
        pinterest = new Pinterest(accessToken);

//        while (true) {
//            saveUserInfo(getUserData(getNextUser()));
//        }
    }

    private String getTableName() {
        return "Pinterest";
    }
}
