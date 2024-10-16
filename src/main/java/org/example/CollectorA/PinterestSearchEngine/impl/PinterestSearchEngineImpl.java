package org.example.CollectorA.PinterestSearchEngine;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.chrisdempewolf.pinterest.Pinterest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.example.CollectorA.Database.DatabaseDispatcher;
import org.example.CollectorA.Database.UserDataModel;

/**
 * This class implements Pinterest Search Engine
 * @author UsoltsevI
 */
@Component
public class PinterestSearchEngineImpl implements PinterestSearchEngine {
    private final Logger log = LoggerFactory.getLogger(PinterestSearchEngineImpl.class);
    @Autowired
    private DatabaseDispatcher database;
    private Pinterest pinterest;

    @Override
    public void collect(String accessToken) {
        log.info("Pinterest collecting");

        if (database.connect(getTableName())) {
            log.info("Pinterest connection to the database is successful");
        } else {
            log.info("Pinterest connection to the database is failed");
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