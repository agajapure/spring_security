package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.security.jwt.JWTTokenFilter;
import com.example.security.service.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private AuthenticationEntryPointHandler authenticationEntryPointHandler;

	private static final String[] WHITELIST_API = { "/h2-console/**", "/api/auth/**" };

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JWTTokenFilter jwtTokenFilter() {
		return new JWTTokenFilter();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsServiceImpl);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();// for h2 console
		http.cors().and().csrf().disable()
				.exceptionHandling().authenticationEntryPoint(authenticationEntryPointHandler)
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests().antMatchers("/api/auth/**").permitAll().antMatchers("/api/test/**").permitAll()
				.antMatchers(WHITELIST_API).permitAll().anyRequest().authenticated();

		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
