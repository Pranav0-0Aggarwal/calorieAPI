package com.pranav.services;


import com.pranav.meal.Meal;
import com.pranav.request.TextRequest;

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

    public String simpleResponse(TextRequest textRequest){
        return "ApI is working";
    }
}
