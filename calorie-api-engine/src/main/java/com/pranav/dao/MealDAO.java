package com.pranav.dao;

import com.pranav.macros.Macros;
import com.pranav.meal.MealResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface MealDAO {
    void addMeal(MealResponse mealResponse);
    List<MealResponse> getMeals();
    List<MealResponse> getMealsBetween(LocalDateTime start, LocalDateTime end);
    Macros getMacrosBetween(LocalDateTime start, LocalDateTime end);
}
