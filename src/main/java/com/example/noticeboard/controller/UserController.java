package com.example.noticeboard.controller;

import com.example.noticeboard.entity.User;
import com.example.noticeboard.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/sign_up")
    public String signUp() {
        return "sign_up";
    }

    @PostMapping("/sign_up")
    public String signUp(User user, HttpSession session) {

        String phone = user.getPhone().replaceAll("-", "");
        System.out.println("phone: " + phone);
        user.setPhone(phone);

        

        User saveUser = userService.save(user);
        session.setAttribute("member", saveUser);
        return "redirect:/";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId, @RequestParam String password, HttpSession session, Model model) {

        Optional<User> login = userService.login(userId, password);

        if (login.isPresent()) {
            session.setAttribute("member", login.get());
            return "redirect:/";
        }

        model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
        model.addAttribute("userId", userId);
        return "login";
    }

    @GetMapping("/check_id")
    public ResponseEntity<Boolean> checkId(@RequestParam String userId) {
        return userService.checkUserId(userId);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/";
    }

    @GetMapping("/update")
    public String update(HttpSession session, Model model) {

        User user = (User) session.getAttribute("member");
        
        // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
        if (user == null) {
            return "redirect:/user/login";
        }
        
        model.addAttribute("user", user);

        return "user_update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute User user) {

        userService.save(user);
        return "redirect:/";
    }

}
