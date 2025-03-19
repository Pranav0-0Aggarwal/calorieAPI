package com.pranav;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.vectorpro.dropwizard.swagger.SwaggerBundleConfiguration;
import io.dropwizard.Configuration;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppConfig extends Configuration {

    @NotNull
    @JsonProperty("serviceName")
    private String serviceName;

    @Valid
    @NotNull
    private SwaggerBundleConfiguration swagger;

}
