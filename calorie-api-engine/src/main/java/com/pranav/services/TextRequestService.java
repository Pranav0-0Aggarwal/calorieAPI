package com.pranav.services;


import com.pranav.meal.Meal;
import com.pranav.request.TextRequest;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TextRequestService {

    /*
    merko chahiye, a ML
     */

    public List<Meal> getMealsFromText(TextRequest textRequest) {
        List<Meal> meals = new ArrayList<>();

        //what i want to do with this function is to call LLAMA.CPP server, and get meals from the text

        return meals;
    }

    public String getClosestFoodId(TextRequest textRequest, List<Meal> meals){
        //
        return "";
    }
    public Pair<LocalDateTime, LocalDateTime> getInterval(TextRequest textRequest) {
        LocalDateTime start = LocalDate.now().atStartOfDay(); // 12:00 AM today
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59); // 11:59 PM today
        return Pair.of(start, end);
    }

    public String simpleResponse(TextRequest textRequest){
        return "ApI is working";
    }
}
