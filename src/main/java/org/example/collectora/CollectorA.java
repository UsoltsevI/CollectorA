package org.example.CollectorA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import org.example.collectora.network.SearchEngine;
import org.example.collectora.pinterest.PinterestSE;

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

        SearchEngine pinterestSE = (SearchEngine) context.getBean(PinterestSE.class);

        pinterestSE.collect();
    }
}
