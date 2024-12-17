package org.example.collectora.pinterest;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.StringBuilder;
import java.util.Random;
import java.util.Scanner;

import org.example.collectora.network.SearchEngine;

@Component
public class PinterestSE implements SearchEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(PinterestSE.class);
    private static final String ADDR_FILE_NAME = "/addresses.txt";
    private final PinDatabase database = new PinDatabase();
    private BufferedReader addrReader;

    @Override
    public void collect() {
        try {
            database.connect();
            LOGGER.info("Connected to the database");
            database.loadTable(Pin.getColumnFamilies());
            LOGGER.info("Table is loaded");
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
            return;
        }

        try {
            loadAddressesFile();
        } catch (IOException e) {
            LOGGER.info("Failed to load addresses file");
            return;
        }

        Cycle cycle = new Cycle();
        Thread cycleThread = new Thread(cycle);
        cycleThread.start();
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
        System.out.println("Enter 'quit' or 'q' to stop the program");
        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("quit") && !input.equalsIgnoreCase("q")) {
            input = scanner.nextLine();
        }
        cycle.stop();
    }

    private void loadAddressesFile() throws IOException {
        addrReader = new BufferedReader(
                new InputStreamReader(
                        PinterestSE.class.getResourceAsStream(ADDR_FILE_NAME)));
    }

    private String getNextPinUrl() throws IOException {
        return addrReader.readLine();
    }

    private class Cycle implements Runnable {
        private volatile boolean stop = false;

        public void stop() {
            stop = true;
        }

        @Override
        public void run() {
            while (!stop) {
                String pinUrl;
                try {
                    pinUrl = getNextPinUrl();
                    if (pinUrl == null || pinUrl == "") {
                        LOGGER.info("END");
                        stop();
                        break;
                    }
                } catch (IOException e) {
                    LOGGER.info(e.getMessage());
                    stop();
                    break;
                }
                LOGGER.info("Current pin url: '" + pinUrl + "'");
                try {
                    String pinId = PinParser.getPinId(pinUrl);
                    if (database.isSaved(pinId)) {
                        LOGGER.info("Pin '" + pinId + "' has been already saved");
                    } else {
                        Pin pin = PinParser.parse(pinUrl);
                        database.savePin(pin);
                    }
                } catch (IOException | IllegalArgumentException e) {
                    LOGGER.info(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
