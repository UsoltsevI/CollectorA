package org.example.CollectorA.InstagramSearchEngine;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.apache.http.client.ClientProtocolException;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;

/**
 * Instagram Search Engine implementstion
 * @author UsoltsevI
 */
@Component
public class InstagramSearchEngineImpl implements InstagramSearchEngine {
    @Override
    public void collect(String username, String password) {
        System.out.println("instagram collecting");
        Instagram4j instagram = new Instagram4j(username, password);
        try {
            instagram.setup();
            instagram.login();
            InstagramSearchUsernameResult userResult = instagram.sendRequest(new InstagramSearchUsernameRequest("github"));
            System.out.println("ID for @github is " + userResult.getUser().getPk());
            System.out.println("Number of followers: " + userResult.getUser().getFollower_count());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }
}
