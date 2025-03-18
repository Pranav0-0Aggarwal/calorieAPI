package com.pranav;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import in.vectorpro.dropwizard.swagger.SwaggerBundle;
import in.vectorpro.dropwizard.swagger.SwaggerBundleConfiguration;

public class App extends Application<AppConfig> {

    SwaggerBundle<AppConfig> swaggerBundle() {
        return new SwaggerBundle<AppConfig>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AppConfig configuration) {
                return configuration.getSwagger();
            }
        };
    }


    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        bootstrap.addBundle(swaggerBundle());
    }

    @Override
    public void run(AppConfig configuration, Environment environment) {
        System.out.println("Starting Calorie API...");
    }
}
