package com.example.authentication.Domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Getter
@Entity
@RequiredArgsConstructor
public class User {

    @Id
    private Long id;
    private String user_id;
    private String password;
}
