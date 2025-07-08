package com.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.boot.security.CustomUserDetailsService;
import com.boot.security.filter.JwtAuthenticationFilter;
import com.boot.security.filter.JwtRequestFilter;
import com.boot.security.provider.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
//	authenticationManager: 필터 설정
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		log.info("@# authenticationManager()");
		log.info("@# authenticationConfiguration=>"+authenticationConfiguration);
		
		this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
		return authenticationManager;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.info("@# securityFilterChain()");
		log.info("@# http=>"+http);
		
//		폼 기반 로그인 비활성화
		http.formLogin(login -> login.disable());
//		http 기본 인증 비활성화
		http.httpBasic(basic -> basic.disable());
//		CSRF(Cross-Site Request Forgery) Cors Policy 공격 방어 기능 비활성화
		http.csrf(csrf -> csrf.disable());
		
		http
			.addFilterAt(new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider)
					, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JwtRequestFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
		;
		
//		인가 설정
		http.authorizeHttpRequests(authorizeRequest -> authorizeRequest
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.requestMatchers("/").permitAll()
				.requestMatchers("/login").permitAll()
				.requestMatchers("/users/**").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
				);
		
//		인증 설정
		http.userDetailsService(customUserDetailsService);
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}


















