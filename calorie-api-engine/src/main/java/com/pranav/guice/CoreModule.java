package com.pranav.guice;

import com.google.inject.AbstractModule;
import com.pranav.dao.ApiKeyDAO;
import com.pranav.dao.FoodDAO;
import com.pranav.dao.MealDAO;
import com.pranav.dao.SearchMapper;
import com.pranav.dao.UserDAO;
import com.pranav.dao.impl.mock.*;
import com.pranav.services.LlmService;
import com.pranav.services.impl.GeminiLlmService;

public class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ApiKeyDAO.class).to(MockApiKeyDAOImpl.class);
        bind(FoodDAO.class).to(MockFoodDAOImpl.class);
        bind(MealDAO.class).to(MockMealDAOImpl.class);
        bind(SearchMapper.class).to(MockSearchMapperImpl.class);
        bind(UserDAO.class).to(MockUserDAOImpl.class);
        bind(LlmService.class).to(GeminiLlmService.class);
    }
}
