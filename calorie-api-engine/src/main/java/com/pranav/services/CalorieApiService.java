package com.pranav.services;

import com.google.inject.Inject;
import com.pranav.dao.FoodDAO;
import com.pranav.dao.MealDAO;
import com.pranav.dao.SearchMapper;
import com.pranav.food.Food;
import com.pranav.macros.Macros;
import com.pranav.meal.Meal;
import com.pranav.meal.MealResponse;
import com.pranav.request.ImageRequest;
import com.pranav.request.TextRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class CalorieApiService {
    private final TextRequestService textRequestService;
    private final FoodDAO foodDAO;
    private final MealDAO mealDAO;
    private final SearchMapper searchMapper;

    @Inject
    public CalorieApiService(TextRequestService textRequestService, FoodDAO foodDAO, MealDAO mealDAO, SearchMapper searchMapper) {
        this.textRequestService = textRequestService;
        this.foodDAO = foodDAO;
        this.mealDAO = mealDAO;
        this.searchMapper = searchMapper;
    }

    public String getTextResponse(TextRequest textRequest) {
        List<Meal> mealsEaten = textRequestService.getMealsFromText(textRequest);

        for (Meal meal : mealsEaten) {
            Food matchingFood = foodDAO.getFoodByName(meal.getFoodName());

            if (matchingFood == null) {
                log.warn("Food '{}' not found in database.", meal.getFoodName());
                continue;
            }

            double mealSize = meal.getQuantityInGram() / 100.0;
            double calories = matchingFood.getCaloriesPer100g() * mealSize;
            double protein = matchingFood.getProteinPer100g() * mealSize;
            double carbs = matchingFood.getCarbsPer100g() * mealSize;
            double fats = matchingFood.getFatsPer100g() * mealSize;
            double sodium = matchingFood.getSodiumPer100g() * mealSize;

            Macros macros = new Macros(calories, protein, carbs, fats, sodium);

            MealResponse mealResponse = MealResponse.builder()
                    .mealId(UUID.randomUUID().toString())
                    .mealName(meal.getFoodName())
                    .timestamp(meal.getTimeStamp())
                    .macros(macros)
                    .userId(textRequest.getUserId())
                    .build();

            mealDAO.addMeal(mealResponse);
        }

        Pair<LocalDateTime, LocalDateTime> interval = textRequestService.getInterval(textRequest);
        LocalDateTime start = interval.getLeft();
        LocalDateTime end = interval.getRight();

        Macros consumedMacros = mealDAO.getMacrosBetween(start, end);
        String updatedText = textBuilder(consumedMacros, start, end, textRequest);

        log.info(updatedText, "{} is the query after build");
        TextRequest modifiedTextRequest = TextRequest.builder()
                .userId(textRequest.getUserId())
                .text(updatedText)
                .build();

        return textRequestService.simpleResponse(modifiedTextRequest);
    }

    public void addFood(Food food) {
        foodDAO.addFood(food);
        log.info("{} food is added", food);
    }

    public List<Food> getAllFood() {
        return foodDAO.getAllFoods();
    }

    public String getImageResponse(ImageRequest imageRequest) {
        // Placeholder response
        return "This API isn't working for now";
    }

    private String textBuilder(Macros macros, LocalDateTime start, LocalDateTime end, TextRequest textRequest) {
        return macros.toString() + " were consumed in interval starting from " +
                start.toString() + " - " + end.toString() +
                ". This was given for context, and the following is the query textual: " +
                textRequest.getText() + ". Generate a Response for the given Query, don't forget to add Macros, and make it mostly focussed on Health, ignore the food name and use the macros provided to discuss";
    }

    public List<MealResponse> getAllMeals() {
        return mealDAO.getMeals();
    }

    public Map<String,String> getAllMappings(){
        return searchMapper.getAllMappings();
    }

    public void addFoods(List<Food> foods) {
        for(Food food: foods){
            foodDAO.addFood(food);
            log.info("{} food is added", food);
        }
    }
}
