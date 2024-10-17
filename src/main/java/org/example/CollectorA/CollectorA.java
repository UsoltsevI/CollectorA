package org.example.CollectorA;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import org.example.CollectorA.InstagramSearchEngine.InstagramSearchEngine;
import org.example.CollectorA.PinterestSearchEngine.PinterestSearchEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import lombok.AllArgsConstructor;

/**
 * The main class of the CollectoA profram. It starts the work of all the collectors on different threads.
 * @author UsoltsevI
 */
@SpringBootApplication
public class CollectorA {
    /**
     * The main function and entry point to program.
     * @param args - nothing
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(CollectorA.class, args);

        InstagramSearchEngine instagram = (InstagramSearchEngine) context.getBean(InstagramSearchEngine.class);
        PinterestSearchEngine pinterest = (PinterestSearchEngine) context.getBean(PinterestSearchEngine.class);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        String instUsername     = getInstagramUsername();
        String instPassword     = getInstagramPassword();
        String pintAccessToken  = getPinterestAccessToken();

        executor.submit(() -> instagram.collect(instUsername, instPassword));
        executor.submit(() -> pinterest.collect(pintAccessToken));

        executor.shutdown();
    }

    private static String getInstagramUsername() {
        System.out.println("Instagram username:");
        return new Scanner(System.in).next();
    }

    private static String getInstagramPassword() {
        System.out.println("Instagram password:");
        return new String(System.console().readPassword());
    }

    private static String getPinterestAccessToken() {
        System.out.println("PinterestAccessToken:");
        return new Scanner(System.in).next();
    }
}
