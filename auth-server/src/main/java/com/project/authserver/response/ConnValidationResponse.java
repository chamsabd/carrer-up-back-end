package com.project.authserver.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;


@Getter
@ToString
public class ConnValidationResponse {
    private String status;
    private boolean isAuthenticated;
    private String methodType;
    private String username;
    private String token;
    private List<GrantedAuthority> authorities;
	public ConnValidationResponse(String status, boolean isAuthenticated, String methodType) {
		super();
		this.status = status;
		this.isAuthenticated = isAuthenticated;
		this.methodType = methodType;
	}
	public ConnValidationResponse(String status, String methodType, String username, String token,
			List<GrantedAuthority> authorities) {
		super();
		this.status = status;
		this.methodType = methodType;
		this.username = username;
		this.token = token;
		this.authorities = authorities;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isAuthenticated() {
		return isAuthenticated;
	}
	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
	public String getMethodType() {
		return methodType;
	}
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
    
    
	
}
