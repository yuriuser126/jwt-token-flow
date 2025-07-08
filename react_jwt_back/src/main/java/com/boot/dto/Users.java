package com.boot.dto;

import java.util.List;

import lombok.Data;

@Data
public class Users {
	private int no;
	private String userId;
	private String userPw;
	private String name;
	private String email;
	private int enabled;
	
//	권한 목록
	List<UserAuth> authList;
}
