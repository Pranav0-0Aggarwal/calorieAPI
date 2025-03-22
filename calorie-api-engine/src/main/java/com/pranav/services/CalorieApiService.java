package com.pranav.services;

import com.google.inject.Inject;
import com.pranav.dao.FoodDAO;
import com.pranav.dao.MealDAO;
import com.pranav.dao.SearchMapper;
import com.pranav.dao.UserDAO;
import com.pranav.food.Food;
import com.pranav.macros.Macros;
import com.pranav.meal.Meal;
import com.pranav.meal.MealResponse;
import com.pranav.request.ImageRequest;
import com.pranav.request.TextRequest;

import com.pranav.utils.Idutil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
public class CalorieApiService {
    private final TextRequestService textRequestService;
    private final FoodDAO foodDAO;
    private final MealDAO mealDAO;
    private final UserDAO userDAO;
    private final SearchMapper searchMapper;

    @Inject
    public CalorieApiService(TextRequestService textRequestService, FoodDAO foodDAO, MealDAO mealDAO, UserDAO userDAO, SearchMapper searchMapper) {
        this.textRequestService = textRequestService;
        this.foodDAO = foodDAO;
        this.mealDAO = mealDAO;
        this.userDAO = userDAO;
        this.searchMapper = searchMapper;
    }

    public String getTextResponse(TextRequest textRequest) {
        if(!userDAO.isValidUser(textRequest.getUserId())){
            log.warn("Invalid User hitting the service");
            return "Invalid UserId";
        }

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
                    .mealId(Idutil.generateMealId())
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

        Macros consumedMacros = mealDAO.getMacrosBetween(textRequest.getUserId(), start, end);
        String updatedText = textBuilder(consumedMacros, start, end, textRequest);

        log.info(updatedText, "{} is the query after build");
        TextRequest modifiedTextRequest = TextRequest.builder()
                .userId(textRequest.getUserId())
                .text(updatedText)
                .build();

        return textRequestService.simpleResponse(modifiedTextRequest);
    }

    public void addFood(Food food) {
        food.setFoodId(Idutil.generateFoodId());
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

    public List<MealResponse> getAllMeals(String userId) {
        return mealDAO.getMeals(userId);
    }

    public Map<String,String> getAllMappings(){
        return searchMapper.getAllMappings();
    }

    public void addFoods(List<Food> foods) {
        for(Food food: foods){
            food.setFoodId(Idutil.generateFoodId());
            foodDAO.addFood(food);
            log.info("{} food is added", food);
        }
    }

    public Food getFoodDetails(String foodName){
        return foodDAO.getFoodByName(foodName);
    }
}
