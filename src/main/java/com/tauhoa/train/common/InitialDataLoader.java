package com.tauhoa.train.common;

import com.tauhoa.train.models.User;
import com.tauhoa.train.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public InitialDataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0) {
            User user = new User();
            user.setUserName("admin");
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("123"));

            userRepository.save(user);
            System.out.println("Initial user created.");
        }
    }
}
