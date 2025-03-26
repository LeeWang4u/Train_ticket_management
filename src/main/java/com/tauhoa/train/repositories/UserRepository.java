package com.tauhoa.train.repositories;

import com.tauhoa.train.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    Optional<User> findByCccd(String cccd);
    Optional<User> findByPhone(String phone);
}
