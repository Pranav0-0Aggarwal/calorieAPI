package com.pranav.dao.impl.mock;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pranav.dao.MealDAO;
import com.pranav.macros.Macros;
import com.pranav.meal.MealResponse;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Singleton
public class MockMealDAOImpl implements MealDAO {

    private final Map<String, MealResponse> mockMealsStore;

    @Inject
    public MockMealDAOImpl() {
        mockMealsStore = new HashMap<>();
    }

    @Override
    public void addMeal(MealResponse mealResponse) {
        mockMealsStore.put(mealResponse.getMealId(), mealResponse);
        log.info("Added meal: " + mealResponse.getMealId());
    }

    @Override
    public List<MealResponse> getMeals() {
        return new ArrayList<>(mockMealsStore.values());
    }

    @Override
    public List<MealResponse> getMealsBetween(LocalDateTime start, LocalDateTime end) {
        List<MealResponse> filteredMeals = new ArrayList<>();

        for (MealResponse meal : mockMealsStore.values()) {
            LocalDateTime mealDate = meal.getTimestamp();

            if ((mealDate.isAfter(start) || mealDate.isEqual(start)) &&
                    (mealDate.isBefore(end) || mealDate.isEqual(end))) {
                filteredMeals.add(meal);
            }
        }

        log.info("Retrieved {} meals between {} and {}", filteredMeals.size(), start, end);
        return filteredMeals;
    }

    @Override
    public Macros getMacrosBetween(LocalDateTime start, LocalDateTime end) {
        Macros totalMacros = new Macros(0, 0, 0, 0, 0);

        List<MealResponse> meals = getMealsBetween(start, end);

        for (MealResponse meal : meals) {
            Macros mealMacros = meal.getMacros();
            totalMacros.setCalorie(totalMacros.getCalorie() + mealMacros.getCalorie());
            totalMacros.setProtein(totalMacros.getProtein() + mealMacros.getProtein());
            totalMacros.setCarbs(totalMacros.getCarbs() + mealMacros.getCarbs());
            totalMacros.setFats(totalMacros.getFats() + mealMacros.getFats());
            totalMacros.setSodium(totalMacros.getSodium() + mealMacros.getSodium());
        }
        return totalMacros;
    }

}