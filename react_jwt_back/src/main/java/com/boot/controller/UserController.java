package com.boot.controller;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.dto.CustomUser;
import com.boot.dto.Users;
import com.boot.service.UserService;

import lombok.extern.slf4j.Slf4j;

/*
 * 회원 정보
 * [GET] 	/users/info	- 회원 정보 조회
 * [POST] 	/users		- 회원 가입
 * [PUT] 	/users		- 회원 정보 수정
 * [DELETE]	/users		- 회원 탈퇴
 * */
@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
//	회원 정보 조회
	@GetMapping("/info")
	public ResponseEntity<?> userInfo(@AuthenticationPrincipal CustomUser customUser) {
		log.info("@# userInfo()");
		log.info("@# customUser=>"+customUser);
		
		Users users = customUser.getUsers();
		log.info("@# users=>"+users);
		
//		인증된 사용자 정보
		if (users != null) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
		
		return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
	}
	
//	회원 가입
	@PostMapping("")
	public ResponseEntity<?> join(@RequestBody Users users) {
		log.info("@# join()");
		log.info("@# users=>"+users);
		
		int result = userService.insert(users);
		
		if (result > 0) {
			log.info("@# 회원가입 성공!");
			return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
		} else {
			log.info("@# 회원가입 실패!");
			return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
		}
	}
	
//	회원 정보 수정
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody Users users) {
		log.info("@# update()");
		log.info("@# users=>"+users);
		
		int result = userService.update(users);
		
		if (result > 0) {
			log.info("@# 회원수정 성공!");
			return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
		} else {
			log.info("@# 회원수정 실패!");
			return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
		}
	}
	
//	회원 탈퇴
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> destroy(@PathVariable("userId") String userId) {
		log.info("@# destroy()");
		log.info("@# userId=>"+userId);
		
		int result = userService.delete(userId);
		
		if (result > 0) {
			log.info("@# 회원삭제 성공!");
			return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
		} else {
			log.info("@# 회원삭제 실패!");
			return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
		}
	}
}













