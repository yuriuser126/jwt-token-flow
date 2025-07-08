package com.boot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
public class UserAuth {
	private int authNo;
	private String userId;
	private String auth;
	
	public UserAuth() {
	}
	
	public UserAuth(String userId, String auth) {
		this.userId = userId;
		this.auth = auth;
	}
}
