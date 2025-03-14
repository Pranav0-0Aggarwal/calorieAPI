package com.pranav.meal;


import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Meal {
    private String foodName;
    private double quantityInGrams;
    private LocalDateTime timestamp;
}
