package com.pranav.resource;


import com.google.inject.Inject;
import com.pranav.food.Food;
import com.pranav.request.TextRequest;
import com.pranav.services.CalorieApiService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Slf4j
@Singleton
@Path("/v1")
public class CalorieApiResource {
    private final CalorieApiService calorieApiService;

    @Inject
    public CalorieApiResource(CalorieApiService calorieApiService) {
        this.calorieApiService = calorieApiService;
    }

    @Path("/food")
    @POST
    public Response addFood(Food food) {
        calorieApiService.addFood(food);
        return Response.ok("Food is Added").build();
    }

    @Path("/text")
    @POST
    public String addText(TextRequest textRequest) {
        return calorieApiService.getTextResponse(textRequest);
    }
}
