package com.project.formation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class securityconfig {
	public static final String AUTHORITIES_CLAIM_NAME = "roles";
	@Autowired  
	UserService userDetailsService;
	
	
//	@Autowired
//	 private AccessDeniedHandler accessDeniedHandler;
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
		@Autowired
	private AuthenticationConfiguration authConfig; 
		
	 
	
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
		 .antMatchers("/Sessions").hasAuthority("ROLE_USER")
		 .anyRequest().authenticated();
	
		// http.exceptionHandling().accessDeniedPage("/accessDenied");
		 http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) ;  
		 http.addFilterBefore(new JWTVerifierFilter(), UsernamePasswordAuthenticationFilter.class);
			
	//	 http.addFilter(new JWTAuthenticationFilter(authenticationManager())); 
		  	 
		
		// http.authenticationProvider(authenticationProvider());
		  
		 
	      
	    return http.build();
	  }
	 
//		@Bean
//		public AuthTokenFilter authenticationJwtTokenFilter() {
//			return new AuthTokenFilter();
//		}
	 


	
	
	 
	 @Bean
	  public AuthenticationManager authenticationManager() throws Exception {	 
	    return authConfig.getAuthenticationManager();
	  }

		
	 
//	 @Bean
//	 public AccessDeniedHandler accessDeniedHandler(){ 
//	 return new AccessDeniedHandlerImpl(); 
//	 } 
	 
	}