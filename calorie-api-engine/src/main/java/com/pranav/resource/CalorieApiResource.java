package com.pranav.resource;

import com.google.inject.Inject;
import com.pranav.food.Food;
import com.pranav.request.TextRequest;
import com.pranav.services.CalorieApiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Slf4j
@Singleton
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "User APIs")
public class CalorieApiResource {
    private final CalorieApiService calorieApiService;

    @Inject
    public CalorieApiResource(CalorieApiService calorieApiService) {
        this.calorieApiService = calorieApiService;
    }

    @Path("/text")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addText(TextRequest textRequest) {
        log.info("Received text: {}", textRequest.getText());
        String response = calorieApiService.getTextResponse(textRequest);
        return Response.ok(response).build();
    }

    @Path("/getFoodDetails")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getFoodDetails(@QueryParam("foodName") String foodName){
        return Response.ok(calorieApiService.getFoodDetails(foodName)).build();
    }

    @Path("/allMeals")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllMeals(@QueryParam("userId") String userId) {
        return Response.ok(calorieApiService.getAllMeals(userId)).build();
    }

}
