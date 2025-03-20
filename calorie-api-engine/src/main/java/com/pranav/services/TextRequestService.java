package com.pranav.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pranav.meal.Meal;
import com.pranav.request.TextRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
@Slf4j
public class TextRequestService {

    private final LlmService llmService;
    private final ObjectMapper objectMapper;

    @Inject
    public TextRequestService(LlmService llmService, ObjectMapper objectMapper) {
        this.llmService = llmService;
        this.objectMapper = objectMapper;
    }

    public List<Meal> getMealsFromText(TextRequest textRequest) {
        List<Meal> meals = new ArrayList<>();

        if (textRequest == null || textRequest.getText() == null || textRequest.getText().trim().isEmpty()) {
            log.warn("Received empty or null text request");
            return meals;
        }

        String prompt = "Extract meal details from the following input and return them as a JSON array. " +
                "JSON array should have objects with 3 fields: foodName, quantityInGram, and timeStamp. " +
                "If no timestamp is in the message, give the current timestamp. Also, try to estimate the quantityInGram based on the query. " +
                "Query: " + textRequest.getText();

        try {
            String response = llmService.getResponse(prompt);
            if (response == null || response.trim().isEmpty()) {
                log.warn("LLM returned an empty response");
                return meals;
            }

            log.info("Raw LLM Response: {}", response);

            // Extract JSON content from response using regex
            String jsonContent = extractJson(response);
            if (jsonContent == null) {
                log.error("Failed to extract JSON from LLM response");
                return meals;
            }
            meals = objectMapper.readValue(jsonContent, new TypeReference<List<Meal>>() {});

        } catch (Exception e) {
            log.error("Error parsing meal JSON response from LLM", e);
        }
        log.info(meals.toString());
        return meals;
    }

    private String extractJson(String response) {
        Pattern pattern = Pattern.compile("```json\\n(.*?)\\n```", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(response);

        String jsonContent;

        if (matcher.find()) {
            jsonContent = matcher.group(1).trim();
        } else {
            log.warn("No matching JSON found");
            return "[]";
        }

        if (!jsonContent.startsWith("[")) {
            log.info("Wrapping non-list JSON in an array.");
            jsonContent = "[" + jsonContent + "]";
        }

        return jsonContent;
    }

    public Pair<LocalDateTime, LocalDateTime> getInterval(TextRequest textRequest) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59);
        return Pair.of(start, end);
    }

    public String simpleResponse(TextRequest textRequest) {
        if (textRequest == null || textRequest.getText() == null || textRequest.getText().trim().isEmpty()) {
            log.warn("Received empty or null text request");
            return "Invalid input. Please provide a valid query.";
        }

        String prompt = "For the following query, answer as a dietitian. " +
                "Try to make use of all the details provided and don't use any previous context:\n\n" +
                textRequest.getText();

        return llmService.getResponse(prompt);
    }
}
