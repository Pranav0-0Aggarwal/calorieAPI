package com.pranav.apiKey;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigLoader {
    private static final String API_KEY_PATH = "Absolute path to your Gemini ApiKey file";

    public static String loadApiKey() {
        try {
            return new String(Files.readAllBytes(Paths.get(API_KEY_PATH))).trim();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load API key from file: " + API_KEY_PATH, e);
        }
    }

}