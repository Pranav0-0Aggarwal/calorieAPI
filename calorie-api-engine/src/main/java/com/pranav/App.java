package com.pranav;

import com.pranav.guice.CoreModule;
import com.pranav.resource.CalorieApiResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import in.vectorpro.dropwizard.swagger.SwaggerBundle;
import in.vectorpro.dropwizard.swagger.SwaggerBundleConfiguration;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class App extends Application<AppConfig> {
    private GuiceBundle guiceBundle;

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
        guiceBundle = GuiceBundle.builder()
                .modules(new CoreModule())
                .build();
        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(swaggerBundle());
    }

    @Override
    public void run(AppConfig configuration, Environment environment) {
        environment.jersey().register(guiceBundle.getInjector().getInstance(CalorieApiResource.class));

    }
}
