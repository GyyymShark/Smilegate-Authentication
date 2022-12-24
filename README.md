# Smilegate-Authentication


# 프로젝트 개요

## 인증시스템


[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

구현한 기능

- 회원가입, 로그인 페이지
- 유저 관리 페이지
- 인증 서버 API
- RDBMS DB 사용: MySQL
- Password Encryption


## 기술 스택

- 프론트는 Html, Ejs, Bootstrap, Css, Javascript를 사용
- ajax를 이용해 api 호출
- 인증 서버 API는 Spring Boot, Spring Security를 사용
- Jwt token을 발급해서 로그인, 인증 권한을 확인
- 비밀번호 암호화는 HS512 알고리즘을 사용
