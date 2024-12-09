package org.example.collectora.pinterest;

import org.springframework.stereotype.Component;

import java.io.IOException;

import org.example.collectora.network.SearchEngine;

@Component
public class PinterestSE implements SearchEngine {
    @Override
    public void collect() {
        // logic to inereate pins

        // get next page
        // parse pin
        // save it to the database
        // repeat
//        Database database = new HBase();
//        database.connect("pinterest");

        try {
//            PinParser.parse("https://ru.pinterest.com/pin/1477812373261109/");
            PinParser.parse("https://ru.pinterest.com/pin/1046383294652899970");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}