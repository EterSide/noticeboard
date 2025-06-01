package com.example.noticeboard.service;

import com.example.noticeboard.entity.User;
import com.example.noticeboard.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {

        User u = new User();
        u.setId(user.getId());
        u.setUserId(user.getUserId());
        u.setEmail(user.getEmail());
        u.setPassword(user.getPassword());
        u.setBirthday(user.getBirthday());
        u.setNickname(user.getNickname());
        u.setPhone(user.getPhone());
        u.setUsername(user.getUsername());
        u.setGender(user.getGender());

        User save = userRepository.save(user);
        return save;
    }

    public Optional<User> login(String username, String password) {
        return userRepository.findByUserIdAndPassword(username, password);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public ResponseEntity<Boolean> checkUserId(String userId) {
        boolean exists = userRepository.existsByUserId(userId);
        return ResponseEntity.ok(exists);
    }

}
