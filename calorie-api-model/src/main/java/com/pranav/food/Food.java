package com.pranav.food;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Food {

    private String foodId;
    private String name;
    private double caloriesPer100g;
    private double proteinPer100g;
    private double carbsPer100g;
    private double fatsPer100g;
    private double sodiumPer100g;
}

