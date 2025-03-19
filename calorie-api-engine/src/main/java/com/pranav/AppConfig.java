package com.pranav;

import in.vectorpro.dropwizard.swagger.SwaggerBundleConfiguration;
import io.dropwizard.Configuration;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class AppConfig extends Configuration {

    @Valid
    @NotNull
    private SwaggerBundleConfiguration swagger;

}
