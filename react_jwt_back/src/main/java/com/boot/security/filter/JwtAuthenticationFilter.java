package com.boot.security.filter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.boot.dto.CustomUser;
import com.boot.security.constants.SecurityConstants;
import com.boot.security.provider.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

//인증 시도 메소드
//인증 성공 메소드
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	
//	생성자
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
		log.info("@# JwtAuthenticationFilter()");
		
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		
//		/login: 필터 url 경로 설정
		setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
	}
	
	//인증 시도 메소드
//	/login 경로로 아이디,비밀번호를 요청하면 이 필터에서 걸려 인증을 시도
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		log.info("@# attemptAuthentication()");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		log.info("@# username=>"+username);
		log.info("@# password=>"+password);
		
//		사용자 인증정보 객체 생성
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
		log.info("@# authentication=>"+authentication);
		
//		사용자 인증(로그인)
//		CustomUserDetailsService 를 통해서 인증
		authentication = authenticationManager.authenticate(authentication);
		log.info("@# authenticationManager=>"+authenticationManager);
		log.info("@# authentication=>"+authentication);
		log.info("@# 인증 여부=>"+authentication.isAuthenticated());
		
//		인증 실패(아이디, 비밀번호 불일치)
		if (!authentication.isAuthenticated()) {
			log.info("@# 인증 실패: 아이디와 비밀번호가 일치하지 않습니다.");
			response.setStatus(401);
		}
		
		return authentication;
	}
	
	//인증 성공 메소드
//	attemptAuthentication 메소드 호출된 후, authentication 객체가 인증되면 호출됨 
//	로그인 인증 성공-->JWT 토큰 생성
//	응답(response) 헤더에 JWT 토큰을 담아서 응답
//	Authorization : Bearer + JWT
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
		log.info("@# 인증 성공(auth success)");
		
		CustomUser user = (CustomUser) authResult.getPrincipal();
		int userNo = user.getUsers().getNo();
		String userId = user.getUsers().getUserId();
		
		List<String> roles = user.getAuthorities()
								.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList());
		
//		파라미터: 사용자번호, 아이디, 권한
//		JWT 토큰 생성
		String token = jwtTokenProvider.createToken(userNo, userId, roles);
		
//		Authorization : Bearer + JWT
		response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
		response.setStatus(200);
	}
}















