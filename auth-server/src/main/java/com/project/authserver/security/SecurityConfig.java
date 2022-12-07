package com.project.authserver.security;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.project.authserver.Service.UserService;
import com.project.authserver.entities.ERole;


@Configuration
public class SecurityConfig {
	public static final String AUTHORITIES_CLAIM_NAME = "roles";
	@Autowired  
	UserService userDetailsService;
	
	JWTAuthenticationFilter jwtauth;
//	@Autowired
//	 private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	@Autowired
	private AuthenticationConfiguration authConfig; 

	 @Bean
	  public DaoAuthenticationProvider authenticationProvider() {
	      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	       
	      authProvider.setUserDetailsService(userDetailsService);
	      authProvider.setPasswordEncoder(passwordEncoder());
	   
	      return authProvider;
	  }
	
	 @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	 http.csrf().disable() 
		 .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//		 .authenticationProvider(getProvider())
//		 .formLogin() 
//		 .loginProcessingUrl("/signin")
//		 .successHandler(new AuthentificationLoginSuccessHandler())
//		 .failureHandler(new SimpleUrlAuthenticationFailureHandler()) 
//		 .and() 
//		 .logout()
//		 .logoutUrl("/logout")
//		 .logoutSuccessHandler(new AuthentificationLogoutSuccessHandler())
//		 .invalidateHttpSession(true)
//		 .and() 
		 .authorizeRequests() 
		 .antMatchers("/signin").permitAll() 
		 .antMatchers("/code").permitAll() 
		 .antMatchers("/signup").permitAll() 
		 .antMatchers("/logout").permitAll()
		 .antMatchers("/api/v1/validateConnection").permitAll()
        
		 .antMatchers("/user").hasAuthority("ROLE_ADMIN")
		 .anyRequest().authenticated();
	
		// http.exceptionHandling().accessDeniedPage("/accessDenied");
		 http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) ;  
		http.addFilter(new JWTAuthenticationFilter(authenticationManager())); 
		  http.addFilterAfter(new JWTAuthorizationFiler(), UsernamePasswordAuthenticationFilter.class);
			 
		
		// http.authenticationProvider(authenticationProvider());
		  
		 
	      
	    return http.build();
	  }
	 
//		@Bean
//		public AuthTokenFilter authenticationJwtTokenFilter() {
//			return new AuthTokenFilter();
//		}
	 


	
	 @Bean
	 public AuthenticationProvider getProvider() { 
	 AppAuthProvider provider = new AppAuthProvider(); 
	 provider.setUserDetailsService(userDetailsService); 
	 provider.setPasswordEncoder(passwordEncoder());
	 return provider; 
	 } 
	 
	 @Bean
	  public AuthenticationManager authenticationManager() throws Exception {
		 
	    return authConfig.getAuthenticationManager();
	  }

		
	 @Bean
	 public PasswordEncoder passwordEncoder () { 
	 return new BCryptPasswordEncoder(); 
	 } 
//	 @Bean
//	 public AccessDeniedHandler accessDeniedHandler(){ 
//	 return new AccessDeniedHandlerImpl(); 
//	 } 
	 
	}
