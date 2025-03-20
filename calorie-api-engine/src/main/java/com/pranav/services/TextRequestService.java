package com.pranav.services;


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

@Singleton
@Slf4j
public class TextRequestService {

    private final LlmService llmService;

    @Inject
    public TextRequestService(LlmService llmService){
        this.llmService = llmService;
    }

    public List<Meal> getMealsFromText(TextRequest textRequest) {
        List<Meal> meals = new ArrayList<>();

        String prompt = "Extract meal details from the following input and return them as a JSON array. " +
                "Ensure the JSON follows this exact structure:\n\n" +
                "[\n" +
                "  {\n" +
                "    \"foodName\": \"String\", // Name of the food item\n" +
                "    \"quantityInGrams\": double, // Weight of the meal in grams\n" +
                "    \"timestamp\": \"yyyy-MM-dd'T'HH:mm:ss\", // Timestamp in ISO format\n" +
                "    \"macros\": {\n" +
                "      \"calories\": double, // Total calories in kcal\n" +
                "      \"protein\": double, // Protein content in grams\n" +
                "      \"carbs\": double, // Carbohydrates content in grams\n" +
                "      \"fat\": double, // Fat content in grams\n" +
                "      \"fiber\": double // Fiber content in grams\n" +
                "    }\n" +
                "  }\n" +
                "]\n\n" +
                "Extract meals from the following text and return them in the JSON format above:\n\n";
        return meals;
    }

    public String getClosestFoodId(TextRequest textRequest, List<Meal> meals){
        //
        return "";
    }
    public Pair<LocalDateTime, LocalDateTime> getInterval(TextRequest textRequest) {
        LocalDateTime start = LocalDate.now().atStartOfDay(); // 12:00 AM today
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59); // 11:59 PM today
        return Pair.of(start, end);
    }

    public String simpleResponse(TextRequest textRequest){
        return "ApI is working";
    }
}
