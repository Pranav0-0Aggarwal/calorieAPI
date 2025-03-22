package com.pranav.utils;

import java.util.UUID;

public class Idutil {

    public static String generateMealId(){
        return "M_" + UUID.randomUUID().toString();
    }

    public String generateUserId(){
        return "U_" + UUID.randomUUID().toString();
    }

    public static String generateFoodId(){
        return "F_" + UUID.randomUUID().toString();
    }
}
