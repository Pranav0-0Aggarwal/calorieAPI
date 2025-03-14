package com.pranav;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class App extends Application<AppConfig> {
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {}

    @Override
    public void run(AppConfig configuration, Environment environment) {
        System.out.println("Starting Calorie API...");
    }
}
