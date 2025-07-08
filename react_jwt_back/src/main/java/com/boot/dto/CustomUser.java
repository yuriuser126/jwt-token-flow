package com.boot.dto;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Data
public class CustomUser implements UserDetails{
	private Users users;
	
	public CustomUser(Users users) {
		this.users = users;
	}
	
//	권한 getter 메소드
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return users.getAuthList().stream()
				.map((auth) -> new SimpleGrantedAuthority(auth.getAuth()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return users.getUserPw();
	}

	@Override
	public String getUsername() {
		return users.getUserId();
	}

}
