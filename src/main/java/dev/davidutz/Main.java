package dev.davidutz;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Radio radio = new Radio();

    public static void main(String[] args) {
        /* Printing first message */
        System.out.println("  _      __    __   ___          ___      ___  __                 \n" +
                " | | /| / /__ / /  / _ \\___ ____/ (_)__  / _ \\/ /__ ___ _____ ____\n" +
                " | |/ |/ / -_) _ \\/ , _/ _ `/ _  / / _ \\/ ___/ / _ `/ // / -_) __/\n" +
                " |__/|__/\\__/_.__/_/|_|\\_,_/\\_,_/_/\\___/_/  /_/\\_,_/\\_, /\\__/_/   \n" +
                "                                                   /___/          ");

        showMenu();
    }

    public static void showMenu() {
        System.out.println("-------------------------Menu-------------------------");

        switch (askInstructions()) {
            case SETUP -> setupStream();
            case PLAY -> {
                try {
                    radio.start();
                } catch (Exception e) {
                    System.out.println("Unknown error, retuning to menu...");
                    showMenu();
                }
            }
            case STOP -> {
                try {
                    radio.stop();
                } catch (Exception e) {
                    System.out.println("Unknown error, retuning to menu...");
                    showMenu();
                }
            }
            case INVALID -> {
                System.out.println("Invalid input !");
                showMenu();
            }
        }
    }

    private static Instructions askInstructions() {
        System.out.println("1. => Setup stream url");
        System.out.println("2. => Play radio");
        System.out.println("3. => Stop radio");

        final String response = scanner.nextLine();

        return switch (response) {
            case "1" -> Instructions.SETUP;
            case "2" -> Instructions.PLAY;
            case "3" -> Instructions.STOP;
            default -> Instructions.INVALID;
        };
    }

    private static void setupStream() {
        System.out.println("Write the link of the stream (mp3)");
        try {
            radio.setStream(new URL(scanner.nextLine()).openStream());
            showMenu();
        } catch (IOException e) {
            System.out.println("Invalid url, returning to menu...");
            showMenu();
        }
    }

    private enum Instructions {
        SETUP(),
        PLAY(),
        STOP(),
        INVALID();
    }
}