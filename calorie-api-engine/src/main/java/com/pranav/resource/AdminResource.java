package com.pranav.resource;

import com.google.inject.Inject;
import com.pranav.food.Food;
import com.pranav.services.CalorieApiService;
import com.pranav.services.UserRegisterService;
import com.pranav.user.User;
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
@Path("/v1/admin")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Admin APIs")
public class AdminResource {
    private final CalorieApiService calorieApiService;
    private final UserRegisterService userRegisterService;

    @Inject
    public AdminResource(CalorieApiService calorieApiService, UserRegisterService userRegisterService) {
        this.calorieApiService = calorieApiService;
        this.userRegisterService = userRegisterService;
    }

    @Path("/addUser")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user){
        return Response.ok(userRegisterService.addUser(user)).build();
    }

    @Path("/Addfood")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFood(Food food) {
        calorieApiService.addFood(food);
        return Response.ok("{\"message\": \"Food is Added\"}").build();
    }

    @Path("/Addfoods")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFood(List<Food> foods) {
        calorieApiService.addFoods(foods);
        return Response.ok("{\"message\": \"Foods are Added\"}").build();
    }

    @Path("/allFood")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllFood(){
        return Response.ok(calorieApiService.getAllFood()).build();
    }


    @Path("/allMeals")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllMeals(@QueryParam("userId") String userId) {
        return Response.ok(calorieApiService.getAllMeals(userId)).build();
    }

    @Path("/AllMappings")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllMappings(){
        return Response.ok(calorieApiService.getAllMappings()).build();
    }

    @Path("/getFoodDetails")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getFoodDetails(@QueryParam("foodName") String foodName){
        return Response.ok(calorieApiService.getFoodDetails(foodName)).build();
    }

}
