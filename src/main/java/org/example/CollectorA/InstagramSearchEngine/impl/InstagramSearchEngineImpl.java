package org.example.CollectorA.InstagramSearchEngine;

import org.springframework.stereotype.Component;

@Component
public class InstagramSearchEngineImpl implements InstagramSearchEngine {
    @Override
    public void collect() {
        System.out.println("instagram collecting");
    }
}
