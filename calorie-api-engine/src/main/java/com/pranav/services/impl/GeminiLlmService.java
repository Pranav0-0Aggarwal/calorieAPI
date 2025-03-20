package com.pranav.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranav.apiKey.ConfigLoader;
import com.pranav.food.Food;
import com.pranav.services.LlmService;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class GeminiLlmService implements LlmService {

    private static final String GEMINI_API_KEY = ConfigLoader.loadApiKey();
    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + GEMINI_API_KEY;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getResponse(String text) {
        log.info("{}, is the prompt", text);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(GEMINI_URL);
            post.setHeader("Content-Type", "application/json");

            // Correct JSON payload structure
            String requestBody = "{ \"contents\": [ { \"parts\": [ { \"text\": \"" + text + "\" } ] } ] }";

            post.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = httpClient.execute(post)) {
                int statusCode = response.getCode();
                String rawResponse = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);

                log.info("Gemini API Response ({}): {}", statusCode, rawResponse);

                if (statusCode != 200) {
                    log.error("Received non-200 response: {}", rawResponse);
                    return null;
                }

                JsonNode jsonResponse = objectMapper.readTree(rawResponse);

                if (jsonResponse.has("error")) {
                    log.error("Gemini API Error: {}", jsonResponse.get("error").toString());
                    return null;
                }

                if (jsonResponse.has("candidates") && jsonResponse.get("candidates").isArray() && jsonResponse.get("candidates").size() > 0) {
                    JsonNode contentNode = jsonResponse.get("candidates").get(0).path("content").path("parts");

                    if (contentNode.isArray() && contentNode.size() > 0) {
                        return contentNode.get(0).path("text").asText();
                    } else {
                        log.error("Unexpected API response structure: {}", rawResponse);
                        return null;
                    }
                }else {
                    log.error("Unexpected API response structure: {}", rawResponse);
                    return null;
                }
            }
        } catch (Exception e) {
            log.error("Error calling Gemini API", e);
            return null;
        }
    }


    @Override
    public Food getClosestFood(String foodName, List<Food> foods) {
        if (foodName == null || foods == null || foods.isEmpty()) {
            log.warn("Invalid input: foodName or foods list is null/empty");
            return null;
        }

        for (Food food : foods) {
            String prompt = "Is '" + foodName + "' and '" + food.getName() + "' the similar food, with different names? Reply in boolean only: true/false";
            String response = getResponse(prompt);

            if (response != null && response.trim().equalsIgnoreCase("true")) {
                log.info("{} and {} are the same thing", foodName, food.getName());
                return food;
            }
        }

        log.info("No matching food found for: {}", foodName);
        return null;
    }
}
