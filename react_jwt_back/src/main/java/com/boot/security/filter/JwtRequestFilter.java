package com.boot.security.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.boot.security.constants.SecurityConstants;
import com.boot.security.provider.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

//JwtAuthenticationFilter 앞단에서 실행
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter{
	private final JwtTokenProvider jwtTokenProvider;

//	생성자
	public JwtRequestFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("@# doFilterInternal()");
		
//		Authorization: HTTP 헤더에서 토큰을 가져옴
		String header = request.getHeader(SecurityConstants.TOKEN_HEADER);
		log.info("@# header=>"+header);
		
		if (header == null || header.length() == 0 || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			log.info("@# header is null");
			log.info("@# request=>"+request);
			log.info("@# response=>"+response);
			
//			JwtAuthenticationFilter 필터로 진행
			filterChain.doFilter(request, response);
			
			return;
		}
		
//		Bearer + JWT 에서 "Bearer " 를 제거 --> JWT
		String jwt = header.replace(SecurityConstants.TOKEN_PREFIX, "");
		log.info("@# jwt=>"+jwt);
		
//		authentication 객체 생성
		Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
		log.info("@# authentication=>"+authentication);
		
//		토큰 유효 검사(만료되지 않음)
		if (jwtTokenProvider.validateToken(jwt)) {
			log.info("@# 유효한 JWT 토큰입니다.");
			
//			사용자 인증정보 객체를 설정
//			SecurityContextHolder: 사용자의 보안정보를 담는 객체
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
//		JwtAuthenticationFilter 필터로 진행
		filterChain.doFilter(request, response);
	}
	
	
}











