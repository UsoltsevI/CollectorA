package org.example.CollectorA;

import org.example.CollectorA.InstagramSearchEngine.InstagramSearchEngine;
import org.example.CollectorA.PinterestSearchEngine.PinterestSearchEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

/**
 * The main class of the CollectoA profram. It starts the work of all the collectors on different threads.
 * @author UsoltsevI
 */
@SpringBootApplication
public class CollectorA {
    /**
     * The main function and entry point to program.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("CollectorA");
        ApplicationContext context = SpringApplication.run(CollectorA.class, args);
        InstagramSearchEngine inst = (InstagramSearchEngine) context.getBean(InstagramSearchEngine.class);
        inst.collect(getInstagramUsername(), getInstagramPassword());
//        This should be parallel
//        PinterestSearchEngine pinterestSearchEngine = (PinterestSearchEngine) context.getBean(PinterestSearchEngine.class);
//        pinterestSearchEngine.collect();
    }

    private static String getInstagramUsername() {
        System.out.println("Instagram username:");
        return new Scanner(System.in).next();
    }

    private static String getInstagramPassword() {
        System.out.println("Instagram password:");
        return new String(System.console().readPassword());
    }

}
