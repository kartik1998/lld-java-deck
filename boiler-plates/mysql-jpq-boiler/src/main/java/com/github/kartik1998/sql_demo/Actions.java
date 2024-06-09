package com.github.kartik1998.sql_demo;

import com.github.kartik1998.sql_demo.entity.User;
import com.github.kartik1998.sql_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Actions {

    @Autowired
    private UserRepository userRepository;

    public void save() {
        User user = User.builder()
                .age(25)
                .email("user_a@email.com")
                .id(1L)
                .name("user_a")
                .build();
        userRepository.save(user);
    }
}
