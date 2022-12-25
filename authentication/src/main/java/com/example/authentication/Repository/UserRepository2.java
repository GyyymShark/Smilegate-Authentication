package com.example.authentication.Repository;


import com.example.authentication.Entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public interface UserRepository2 extends JpaRepository<User, Long> {    //JpaRepository를 extends하면 findAll, save등의 메소드를 기본적으로 사용할 수 있다



    @EntityGraph(attributePaths = "authorities")    //@EntityGraph는 쿼리가 수행될때 Lazy조회가 아니라 Eager조회로
                                                    // authorities 정보를 같이 가져오게 된다
    Optional<User> findOneWithAuthoritiesByUsername(String username); //username을 기준으로 user정보를 가져올때 권한 정보도 같이 가져온다



    @EntityGraph(attributePaths = "authorities")
    List<User> findAll();


}