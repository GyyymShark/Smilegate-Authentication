package com.example.authentication.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.example.authentication.Dto.UserDto;
import com.example.authentication.Entity.Authority;
import com.example.authentication.Entity.User;
import com.example.authentication.Exception.DuplicateMemberException;
import com.example.authentication.Exception.NotFoundMemberException;
import com.example.authentication.Repository.UserRepository2;
import com.example.authentication.Util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository2 userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository2 userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }   //UserRepository2와 PasswordEncoder를 주입받는다

    @Transactional
    public UserDto signup(UserDto userDto) {    //회원가입 로직 수행하는 메소드
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");    //중복 체크
        }

        Authority authority = Authority.builder()   //중복이 아니면 권한 정보를 만들고
                .authorityName("ROLE_USER") //회원가입한 유저는 ROLE_USER 권한 하나만 가지고 있다
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))    //유저정보 넣고
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))  //권한정보 넣고
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user)); //유저정보를 save를 통해 db에저장
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {    //username을 parameter로 받아서 어떤 유저정보든 가져오는것
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() { //현재 securitycontext에 저장된 유저정보 가져오기
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

    @Transactional(readOnly = true)
    public List<User> getAllUserWithAuthorities() {
        return userRepository.findAll();
    }

}