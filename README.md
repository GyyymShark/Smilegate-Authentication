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
- Jwt token을 발급해서 로그인, 인증 권한을 확인(access token만 구현돼있다)
- 비밀번호 암호화는 BCryptPasswordEncoder 사용
- 토큰 암호화는 HS512 알고리즘 사용


## 개발관련 과정에서 궁금했던 부분
- msa구조를 구현하지는 못했지만 인증 서버, 유저 서버 등 서버를
나누다보면 한 pc에서 여러개의 서버를 돌리는 cpu가 감당이 되는지 궁금합니다
- access token과 refresh token에 담긴 정보는 동일한지 궁금합니다
- 발급받은 jwt 토큰을 프론트에서는 쿠키에 저장해놨는데 이렇게 해도 문제가 없는건지 궁금합니다


## 코드 중 확인받고 싶은 부분




## UI
![1](https://user-images.githubusercontent.com/46774346/209458906-5cc3e265-53e4-4931-9006-a7d5abc2c635.png)
![2](https://user-images.githubusercontent.com/46774346/209458909-e020c914-0a90-40e9-a1f8-00e08ccb78fa.png)

