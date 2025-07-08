# jwt-token-flow

![Build Status](https://img.shields.io/github/actions/workflow/status/yuriuser126/jwt-token-flow/gradle.yml?branch=main&label=build&style=flat-square)
![License](https://img.shields.io/github/license/yuriuser126/jwt-token-flow?style=flat-square)
![Java](https://img.shields.io/badge/Java-17+-blue?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?style=flat-square)

JWT 기반 인증과 권한 관리를 위한 Spring Boot 프로젝트

## 소개
`jwt-token-flow`는 JSON Web Token(JWT)을 사용하여 사용자 인증과 권한 관리를 구현한 Spring Boot 백엔드 예제 프로젝트입니다.  
JWT 생성, 검증, 토큰 해석을 담당하는 `JwtTokenProvider` 클래스를 중심으로 구성되어 있습니다.

## 주요 기능
- JWT 토큰 생성 (사용자 번호, 아이디, 권한 포함)
- JWT 토큰 파싱 및 인증 정보 추출
- 토큰 유효성 검사 (만료, 변조 여부 확인)
- 사용자 권한(Role) 기반 인증 처리
- DB 연동을 통한 사용자 정보 조회 및 권한 부여

## 기술 스택
- Java 17+
- Spring Boot 3.x
- Spring Security
- MyBatis (UserMapper)
- JWT (jjwt 라이브러리)
- Lombok
- MariaDB/MySQL

## 프로젝트 구조
- `JwtTokenProvider` : JWT 생성, 검증, 인증 토큰 생성 담당
- `UserMapper` : DB에서 사용자 정보 조회
- `Users`, `UserAuth` : 사용자 및 권한 DTO
- `CustomUser` : Spring Security UserDetails 구현체

## 설정 정보
- `secret-key` : JWT 서명에 사용되는 Base64 인코딩된 비밀키 (application.properties 에 설정)

## 설치 및 실행 방법
1. 프로젝트 클론  
```bash
git clone https://github.com/yuriuser126/jwt-token-flow.git
cd jwt-token-flow
```
2.환경 설정
src/main/resources/application.properties 에서 DB 접속 정보 및 secret-key 값 설정

3.빌드 및 실행
```bash
./gradlew bootRun

```

4.API 테스트
Postman 등으로 인증 API 테스트 가능



JWT 관련 키 길이는 최소 256비트 이상 권장 (HS256 기준)
토큰 만료 시간은 현재 1일로 설정되어 있음 (필요 시 조절 가능)





