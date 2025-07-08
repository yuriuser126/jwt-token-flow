package com.boot.service;

import com.boot.dto.Users;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
	public int insert(Users users);//회원 등록
	public Users select(int userNo);//회원 조회
//	public Users login(String user_id);//로그인
	public void login(Users users, HttpServletRequest request);//로그인
	public int update(Users users);//회원 수정
	public int delete(String user_id);//회원 삭제
}
