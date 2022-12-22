package com.example.authentication.Repository;



import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

/*
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }*/


}
