package com.pranav.services;

import com.pranav.food.Food;

import java.util.List;

public interface LlmService {
    String getResponse(String text);
    List<Double> getClosestFood(String foodName, List<Food> foods);
}
