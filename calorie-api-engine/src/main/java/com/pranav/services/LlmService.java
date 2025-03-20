package com.pranav.services;

import com.pranav.food.Food;

import java.util.List;

public interface LlmService {
    String getResponse(String text);
    Food getClosestFood(String foodName, List<Food> foods);
}
