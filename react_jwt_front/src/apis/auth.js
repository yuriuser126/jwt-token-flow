import React from 'react'
import api from './api'
import { data } from 'react-router-dom';

/*
    !! 작은따옴표말고 백틱 ` 이거사용해야 사용이 된다 !!
*/

//1.회원가입 [POST]
// http://localhost:8585/users 여기 users가 아래 post users
// { 이게 데이터
// "userId" : "user1"
//  ,"userPw" : "1234"
//  ,"name" : "user1"
//  ,"email" : "user1@a.com"
// }
export const join=(data)=>api.post(`users`,data)

//2.로그인 [GET]
//http://localhost:8585/login?username=user1&password=1234
// export const login = (username,password)=> api.get('/login?username=${username}&password=${password}',data);
//백틱처리(함수 파라미터 정상인식)
export const login = (username,password)=> api.get(`/login?username=${username}&password=${password}`,data);

//3.사용자 정보 조회 [GET]
//http://localhost:8585/users/info
export const info = ()=> api.get(`/users/info`);


//4.회원 정보 수정[PUT]
//http://localhost:8585/users
export const update = ()=>api.put(`/users`,data);


//5.회원 탈퇴 / 삭제[DELETE]
//http://localhost:8585/users
export const remove = ()=> api.delete(`/users`);