package com.boot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.boot.dao.UserMapper;
import com.boot.dto.CustomUser;
import com.boot.dto.Users;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("@# loadUserByUsername()");
		log.info("@# username=>"+username);
		
//		사용자 아이디로 사용자정보를 가져옴
		Users users = userMapper.login(username);
		
		if (users == null) {
			log.info("@# 사용자 없음");
			throw new UsernameNotFoundException("사용자를 찾을 수 없음:"+username);
		}
		
//		CustomUser를 생성해서 반환(사용자 정보 포함)
		CustomUser customUser = new CustomUser(users);
		log.info("@# customUser=>"+customUser);
		
		return customUser;
	}

}















