package com.tauhoa.train.repositories;

import com.tauhoa.train.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByUserId(int userId);
    Optional<User> findByEmail(String email);
}
