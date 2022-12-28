package com.project.getwayserver.config;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.project.getwayserver.filters.AuthenticationPrefilter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class DefaultConfig {

	//@Value("${spring.gateway.excludedURLsNew}")
    private String urlsStrings="/api/v1/validateToken,/signin,/code,/signup,/send";

    @Bean
    @Qualifier("excludedUrls")
    public List<String> excludedUrls() {
        return Arrays.stream(urlsStrings.split(",")).collect(Collectors.toList());
    }

    @Bean
    public ObjectMapper objectMapper() {
        JsonFactory factory = new JsonFactory();
        factory.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

        ObjectMapper objectMapper = new ObjectMapper(factory);
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return objectMapper;
    }


    @Bean
    public RouteLocator routes(
            RouteLocatorBuilder builder,
            AuthenticationPrefilter authFilter) {
        return builder.routes()
                .route("auth-server", r -> r.path("/auth-server/**")
                        .filters(f ->
                                f.rewritePath("/auth-server(?<segment>/?.*)", "$\\{segment}")
                                        .filter(authFilter.apply(
                                                new AuthenticationPrefilter.Config())))
                        .uri("lb://AUTH-SERVER"))
                .route("email-server", r -> r.path("/email-server/**")
                        .filters(f ->
                                f.rewritePath("/email-server(?<segment>/?.*)", "$\\{segment}")
                                        .filter(authFilter.apply(
                                                new AuthenticationPrefilter.Config())))
                        .uri("lb://EMAIL-SERVER"))
                .route("formation-server", r -> r.path("/formation-server/**")
                        .filters(f ->
                                f.rewritePath("/formation-server(?<segment>/?.*)", "$\\{segment}")
                                        .filter(authFilter.apply(
                                          new AuthenticationPrefilter.Config())))
                        .uri("lb://FORMATION-SERVER"))

                .route("stage-server", r -> r.path("/stage-server/**")
                        .filters(f ->
                                f.rewritePath("/stage-server(?<segment>/?.*)", "$\\{segment}")
                                        .filter(authFilter.apply(
                                          new AuthenticationPrefilter.Config())))
                        .uri("lb://STAGE-SERVER"))
                .build();
    }
}
