package org.example.CollectorA;

import org.example.CollectorA.InstagramSearchEngine.InstagramSearchEngine;
import org.example.CollectorA.FacebookSearchEngine.FacebookSearchEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

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
        inst.collect();
    }

//    This method prints all the beans founded by ApplicationContext
//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
//
//        };
//    }
}
