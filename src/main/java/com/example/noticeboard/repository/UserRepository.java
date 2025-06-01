package com.example.noticeboard.repository;

import com.example.noticeboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserIdAndPassword(String userId, String password);

    Optional<User> findById(long id);

    boolean existsByUserId(String userId);

}
