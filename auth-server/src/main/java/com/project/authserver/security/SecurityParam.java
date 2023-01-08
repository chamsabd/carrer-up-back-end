package com.project.authserver.security;

public interface SecurityParam {
	public static final String JWT_HEADER_NAME="Authorization";
	public static final String SECRET="chams-carrer-up@gmail.tn"; 
	public static final long EXPIRATION=20*24*3600; 
	public static final String HEADER_PREFIX="Bearer "; 
}
