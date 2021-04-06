package com.example.spring_test_tutorial.security1.repository;

import com.example.spring_test_tutorial.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    //findBy는 규칙
    //select * from user where userneme = ?(파라미터에 있는것이 ?에 들어감)
   //JPA Query methods
    public User findByUsername(String username);
}
