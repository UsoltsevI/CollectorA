package org.example.collectora.pinterest;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.StringBuilder;
import java.util.Random;
import java.util.Scanner;

import org.example.collectora.network.SearchEngine;

@Component
public class PinterestSE implements SearchEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(PinterestSE.class);
    private static final String ZERO_CF = "meta";
    private static final String PREFIX = "https://ru.pinterest.com/pin/";
    private final Random random = new Random();
    private final PinDatabase database = new PinDatabase();
    private final StringBuilder pinId = new StringBuilder("1477812373261109");

    @Override
    public void collect() {
        try {
            database.connect();
            LOGGER.info("Connected to the database");
            database.loadTable(ZERO_CF);
            LOGGER.info("Table is loaded");
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
            return;
        }

        Cycle cycle = new Cycle();
        Thread thread = new Thread(cycle);
        thread.start();
        interrupt(cycle);

        try {
            database.close();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }

        LOGGER.info("Collecting is comlete");
    }

    private void interrupt(Cycle cycle) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 'quit' to stop the program");
        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("quit")) {
            input = scanner.nextLine();
        }
        cycle.stop();
    }

    private String getRandomPinUrl() {
        pinId.setCharAt(random.nextInt(pinId.length()), (char) (random.nextInt(10) + '0'));
        return PREFIX + pinId.toString();
    }

    private class Cycle implements Runnable {
        private volatile boolean stop = false;

        public void stop() {
            stop = true;
        }

        @Override
        public void run() {
            while (!stop) {
                try {
                    String pinUrl = getRandomPinUrl();
                    LOGGER.info("Current pin url: '" + pinUrl + "'");
                    Pin pin = PinParser.parse(pinUrl);
                    if (database.isSaved(pin.getId())) {
                        LOGGER.info("Pin '" + pin.getId() + "' has been already saved");
                    } else {
                        database.savePin(pin);
                    }
                } catch (IOException | IllegalArgumentException e) {
                    LOGGER.info(e.getMessage());
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    LOGGER.info(e.getMessage());
                    break;
                }
            }
        }
    }
}
