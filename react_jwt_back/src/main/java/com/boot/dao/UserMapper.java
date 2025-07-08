package com.boot.dao;

import org.apache.ibatis.annotations.Mapper;

import com.boot.dto.UserAuth;
import com.boot.dto.Users;

@Mapper
public interface UserMapper {
	public int insert(Users users);//회원 등록
	public Users select(int userNo);//회원 조회
	public Users login(String user_id);//로그인
	public int update(Users users);//회원 수정
	public int delete(String user_id);//회원 삭제
	public int deleteAuth(String user_id);//권한 삭제
	public int insertAuth(UserAuth userAuth);//권한 등록
}
