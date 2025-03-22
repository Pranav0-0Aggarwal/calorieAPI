package com.pranav.utils;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pranav.food.Food;
import com.pranav.services.LlmService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Singleton
public class ClosestFinder {
    private final LlmService llmService;

    @Inject
    public ClosestFinder(LlmService llmService) {
        this.llmService = llmService;
    }

    public Food getClosestFood(String foodName, List<Food> foodList) {
        if (foodName == null || foodList == null || foodList.isEmpty()) {
            log.warn("Invalid input: foodName or foodList is null/empty");
            return null;
        }

        List<Double> scores = llmService.getClosestFood(foodName, foodList);
        if (scores == null || scores.size() != foodList.size()) {
            log.error("Mismatch in sizes of score list and food list for '{}'. Scores: {}, Foods: {}",
                    foodName, scores != null ? scores.size() : "null", foodList.size());
            return null;
        }

        double highestScore = 0.0;
        Food bestMatch = null;

        for (int i = 0; i < scores.size(); i++) {
            double score = scores.get(i);
            log.debug("Score for '{}' vs '{}': {}", foodName, foodList.get(i).getName(), score);

            if (score > highestScore) {
                highestScore = score;
                bestMatch = foodList.get(i);
            }
        }

        if (bestMatch != null) {
            log.info("Best match for '{}' is '{}' with similarity score {}", foodName, bestMatch.getName(), highestScore);
        } else {
            log.warn("No suitable match found for '{}'", foodName);
        }

        return bestMatch;
    }
}
