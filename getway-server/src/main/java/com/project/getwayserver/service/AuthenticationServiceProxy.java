package com.project.getwayserver.service;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.project.getwayserver.model.ConnValidationResponse;


//@FeignClient(name = "authentication-service")
public interface AuthenticationServiceProxy {

	@GetMapping(value = "/api/v1/validateConnection", produces = {MediaType.APPLICATION_JSON_VALUE})
	public static ResponseEntity<ConnValidationResponse> validateConnection(@RequestHeader("Authorization") String authToken) {
		// TODO Auto-generated method stub
		return null;
	}

	
	//
//	@PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE},
//			produces = {MediaType.APPLICATION_JSON_VALUE})
//	public ResponseEntity<EntityModel<SalesGroupResponseModel>> createGroup(@Validated(ValidationLevel.onCreate.class) @RequestBody SalesGroupRequestModel salesGpRequest) ;
}
