package com.pranav.dao.impl;


import com.pranav.dao.FoodDAO;
import com.pranav.food.Food;

import java.util.List;

public class FoodDAOImpl implements FoodDAO {

    @Override
    public Food getFoodByName(String foodName) {
        return null;
    }

    @Override
    public Food getFoodById(String foodId) {
        return null;
    }

    @Override
    public void addFood(Food food) {

    }

    @Override
    public boolean foodExists(String foodName) {
        return false;
    }

    @Override
    public List<Food> getAllFoods() {
        return List.of();
    }

}
