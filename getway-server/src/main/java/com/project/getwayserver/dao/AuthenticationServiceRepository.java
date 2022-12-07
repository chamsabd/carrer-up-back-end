package com.project.getwayserver.dao;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.project.getwayserver.model.ConnValidationResponse;

import reactor.core.publisher.Mono;

@Repository
public class AuthenticationServiceRepository {

//	@GetMapping(value = "/api/v1/validateConnection", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ConnValidationResponse validateConnection(String authToken) throws Exception {

		try {

			WebClient client = WebClient.builder().baseUrl("lb://authentication-service").build();
			ConnValidationResponse response = client.get().uri("/api/v1/validateConnection")
					.header("Authorization", authToken)
					.retrieve().toEntity(ConnValidationResponse.class).flux().blockFirst().getBody();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new Exception( "Gateway Error");
	}

}