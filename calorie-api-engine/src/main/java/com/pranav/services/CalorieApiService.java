package com.pranav.services;

import com.google.inject.Inject;
import com.pranav.dao.FoodDAO;
import com.pranav.dao.MealDAO;
import com.pranav.food.Food;
import com.pranav.macros.Macros;
import com.pranav.meal.Meal;
import com.pranav.meal.MealResponse;
import com.pranav.request.ImageRequest;
import com.pranav.request.TextRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
public class CalorieApiService {
    private final TextRequestService textRequestService;
    private final FoodDAO foodDAO;
    private final MealDAO mealDAO;

    @Inject
    public CalorieApiService(TextRequestService textRequestService, FoodDAO foodDAO, MealDAO mealDAO) {
        this.textRequestService = textRequestService;
        this.foodDAO = foodDAO;
        this.mealDAO = mealDAO;
    }

    public String getTextResponse(TextRequest textRequest) {
        List<Meal> mealsEaten = textRequestService.getMealsFromText(textRequest);

        for (Meal meal : mealsEaten) {
            Food matchingFood = foodDAO.getFoodByName(meal.getFoodName());

            if (matchingFood == null) {
                log.warn("Food '{}' not found in database.", meal.getFoodName());
                continue;
            }

            double mealSize = meal.getQuantityInGrams() / 100.0;
            double calories = matchingFood.getCaloriesPer100g() * mealSize;
            double protein = matchingFood.getProteinPer100g() * mealSize;
            double carbs = matchingFood.getCarbsPer100g() * mealSize;
            double fats = matchingFood.getFatsPer100g() * mealSize;
            double sodium = matchingFood.getSodiumPer100g() * mealSize;

            Macros macros = new Macros(calories, protein, carbs, fats, sodium);

            MealResponse mealResponse = MealResponse.builder()
                    .mealId(UUID.randomUUID().toString())
                    .mealName(meal.getFoodName())
                    .timestamp(meal.getTimestamp())
                    .macros(macros)
                    .userId(textRequest.getUserId())
                    .build();

            mealDAO.addMeal(mealResponse);
        }

        return textRequestService.simpleResponse(textRequest);
    }

    public void addFood(Food food){
        foodDAO.addFood(food);
        log.info("{}food is added", food);
    }

    public List<Food> getAllFood(){
        return foodDAO.getAllFoods();
    }

    public String getImageResponse(ImageRequest imageRequest) {
        //placeholder
        return "This API isn't working for now";
    }
}
