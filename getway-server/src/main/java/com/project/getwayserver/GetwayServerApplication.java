package com.project.getwayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication	
public class GetwayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetwayServerApplication.class, args);
	}
	@Bean 
	DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc,DiscoveryLocatorProperties dlp) {
		return new DiscoveryClientRouteDefinitionLocator(rdc, dlp); }

	//@Bean  
	//RouteLocator routes(RouteLocatorBuilder builder) {
//		return builder.routes().route(r -> r.path("/books/**").uri("http://localhost:3000")).build();  
//		} 
	//	
	// @Bean   RouteLocator routes1(RouteLocatorBuilder builder) {
//	 	return builder.routes().route(r -> r.path("/societes/**").uri("lb://SERVICE-SOCIETE")).build();
//	 	} 
	
@Bean  
RouteLocator routes(RouteLocatorBuilder builder) {
	return builder.routes().route(r ->
		
		
	r.path("/stage-server/**").uri("lb://STAGE-SERVER")
	
	
	)
	.route(r -> r.path("/inscrit-server/**").uri("http://localhost:3001")).build();  
	} 
	}
