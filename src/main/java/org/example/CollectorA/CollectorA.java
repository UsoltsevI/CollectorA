package org.example.CollectorA;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.example.CollectorA.InstagramSearchEngine.InstagramSearchEngine;
import org.example.CollectorA.FacebookSearchEngine.FacebookSearchEngine;

/**
 * @author UsoltsevI
 */

public class CollectorA {
    public static void main(String[] args) {
        System.out.println("CollectorA");
        try (ClassPathXmlApplicationContext context
                     = new ClassPathXmlApplicationContext("CollectorA.xml")) {
            InstagramSearchEngine inst = (InstagramSearchEngine) context.getBean("instagram");
            inst.collect();
        }
    }
}
