package com.pranav.dao;

import com.pranav.food.Food;

import java.util.List;

public interface FoodDAO {
    Food getFoodByName(String foodName);

    Food getFoodById(String foodId);

    void addFood(Food food);

    boolean foodExists(String foodName);

    List<Food> getAllFoods();
}
