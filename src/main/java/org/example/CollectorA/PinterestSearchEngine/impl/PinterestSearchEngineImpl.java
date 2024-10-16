package org.example.CollectorA.PinterestSearchEngine;

import org.springframework.stereotype.Component;
//import com.chrisdempewolf.pinterest.Pinterest;
import java.lang.StringBuilder;

/**
 * This class implements Pinterest Search Engine
 * @author UsoltsevI
 */
@Component
public class PinterestSearchEngineImpl implements PinterestSearchEngine {

    @Override
    public void collect(String accessToken) {
        System.out.println("Pinterest collecting");
//        Pinterest pinterest = new Pinterest(acsessToken);

    }
}