package com.project.getwayserver.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class ConnValidationResponse {
    private String status;
    private boolean isAuthenticated;
    private String methodType;
    private String username;
    private String token;
    private List<Authorities> authorities;
	public String getStatus() {
		return status;
	}
	public boolean isAuthenticated() {
		return isAuthenticated;
	}
	public String getMethodType() {
		return methodType;
	}
	public String getUsername() {
		return username;
	}
	public String getToken() {
		return token;
	}
	public List<Authorities> getAuthorities() {
		return authorities;
	}


}
