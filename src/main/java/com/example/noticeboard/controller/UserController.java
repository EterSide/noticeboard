package com.example.noticeboard.controller;

import com.example.noticeboard.entity.User;
import com.example.noticeboard.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @GetMapping("/sign_up")
    public String signUp() {
        return "sign_up";
    }

    @PostMapping("/sign_up")
    public String signUp(User user, HttpSession session) {
        User saveUser = userService.save(user);
        session.setAttribute("member", saveUser);
        return "redirect:/";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId, @RequestParam String password, HttpSession session) {

        Optional<User> login = userService.login(userId, password);

        if (login.isPresent()) {
            session.setAttribute("member", login.get());
            return "redirect:/";
        }

        return "login";

    }

}
