package com.pranav.meal;

import com.pranav.macros.Macros;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class MealResponse {
    private String userId;
    private String mealId;
    private String mealName;
    private Macros macros;
    private LocalDateTime timestamp;
}
