package com.example.authentication.Service;


import com.example.authentication.Domain.User;
import com.example.authentication.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    //회원 전체 조회
    public List<User> findUsers() {return userRepository.findAll();}


}
