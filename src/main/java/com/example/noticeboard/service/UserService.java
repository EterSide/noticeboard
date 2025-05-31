package com.example.noticeboard.service;

import com.example.noticeboard.entity.User;
import com.example.noticeboard.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> login(String username, String password) {
        return userRepository.findByUserIdAndPassword(username, password);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

}
