package com.pranav.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.pranav.AppConfig;
import com.pranav.dao.ApiKeyDAO;
import com.pranav.dao.FoodDAO;
import com.pranav.dao.MealDAO;
import com.pranav.dao.SearchMapper;
import com.pranav.dao.UserDAO;
import com.pranav.dao.impl.mariaDB.MariaDbFoodDAOImpl;
import com.pranav.dao.impl.mariaDB.MariaDbMealDAOImpl;
import com.pranav.dao.impl.mariaDB.MariaDbSearchMapperImpl;
import com.pranav.dao.impl.mariaDB.MariaDbUserDAOImpl;
import com.pranav.dao.impl.mock.*;
import com.pranav.services.LlmService;
import com.pranav.services.impl.GeminiLlmService;
import org.jdbi.v3.core.Jdbi;

public class CoreModule extends AbstractModule {
    private final Provider<Jdbi> jdbiProvider;

    public CoreModule(Provider<Jdbi> jdbiProvider) {
        if(jdbiProvider == null) {
            throw new NullPointerException("jdbiProvider cannot be null");
        }
        this.jdbiProvider = jdbiProvider;
    }

    @Override
    protected void configure() {
        bind(ApiKeyDAO.class).to(MockApiKeyDAOImpl.class);
        bind(FoodDAO.class).to(MariaDbFoodDAOImpl.class);
        bind(MealDAO.class).to(MariaDbMealDAOImpl.class);
        bind(SearchMapper.class).to(MariaDbSearchMapperImpl.class);
        bind(UserDAO.class).to(MariaDbUserDAOImpl.class);
        bind(LlmService.class).to(GeminiLlmService.class);
        bind(Jdbi.class).toProvider(jdbiProvider);
    }

}