package com.boot.service;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.dao.UserMapper;
import com.boot.dto.UserAuth;
import com.boot.dto.Users;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public int insert(Users users) {
		log.info("@# insert()");
		log.info("@# users=>"+users);
		
		String userPw = users.getUserPw();
//		비밀번호 암호화
		String encodePw = passwordEncoder.encode(userPw);
		log.info("@# encodePw=>"+encodePw);
		users.setUserPw(encodePw);
		
//		회원 등록
		int result = userMapper.insert(users);
		
//		권한 등록
		if (result > 0) {
			UserAuth userAuth = new UserAuth();
			userAuth.setUserId(users.getUserId());
			userAuth.setAuth("ROLE_USER");//ROLE_USER: 기본 권한
			result = userMapper.insertAuth(userAuth);
		}
		log.info("@# result=>"+result);
		
		return result;
	}

	@Override
	public Users select(int userNo) {
		log.info("@# select()");
		log.info("@# userNo=>"+userNo);
		
		return userMapper.select(userNo);
	}

	@Override
	public void login(Users users, HttpServletRequest request) {
		log.info("@# login()");
		log.info("@# users=>"+users);
		log.info("@# request=>"+request);
		
		String username = users.getUserId();
		String password = users.getUserPw();
		log.info("@# username=>"+username);
		log.info("@# password=>"+password);
		
//		아이디, 비밀번호 인증 토큰 생성
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		
//		토큰에 요청정보를 등록
		token.setDetails(new WebAuthenticationDetails(request));
		
//		토큰을 이용해서 로그인(인증)
		Authentication authentication = authenticationManager.authenticate(token);
		
//		authentication(인증 정보)를 SecurityContextHolder(보안정보)에 저장
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@Override
	public int update(Users users) {
		log.info("@# update()");
		log.info("@# users=>"+users);
		
		String userPw = users.getUserPw();
		
//		비밀번호 암호화
		String encodePw = passwordEncoder.encode(userPw);
		log.info("@# encodePw=>"+encodePw);
		users.setUserPw(encodePw);
		
	 	int result = userMapper.update(users);
	 	log.info("@# result=>"+result);
		
		return result;
	}

	@Override
	@Transactional
	public int delete(String user_id) {
		log.info("@# delete()");
		log.info("@# user_id=>"+user_id);
		
		int result = userMapper.delete(user_id);
		int result2 = userMapper.deleteAuth(user_id);
		log.info("@# result=>"+result);
		log.info("@# result2=>"+result2);
		
		return result2;
	}

}





