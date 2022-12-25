package com.example.authentication.Service;


import com.example.authentication.Entity.User;
import com.example.authentication.Repository.UserRepository2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {   //UserDetailService implement
    private final UserRepository2 userRepository;

    public CustomUserDetailsService(UserRepository2 userRepository) {
        this.userRepository = userRepository;
    }
    //userRepository를 생성자 주입받는다

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {  //UserDetailsService의 loadUserByUsername 메소드를 오버라이드해서
        return userRepository.findOneWithAuthoritiesByUsername(username) //로그인 시에 DB에서 유저정보와 권한정보를 가져온다
                .map(user -> createUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, User user) {
        if (!user.isActivated()) {      //해당 유저가 활성화 돼있지 않을때
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()  //활성화 돼있다면 유저의 권한정보
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(),   //유저의 이름
                user.getPassword(), //비밀번호
                grantedAuthorities);    //권한을 가지고있는 userdetails.User 객체를 생성해서 리턴한다
    }
}
