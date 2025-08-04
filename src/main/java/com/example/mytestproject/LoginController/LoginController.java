package com.example.mytestproject.LoginController;

import com.example.mytestproject.dto.UserForm;
import com.example.mytestproject.entity.Users;
import com.example.mytestproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/article/sign_up")
    public String signUp(){

        return "articles/sign_up";
    }
    @GetMapping("/article/login")
    public String login(){

        return "articles/login";
    }
    @PostMapping("/signup")
    public String signup(UserForm form) {
        System.out.println(form.toString());

        Users user = form.toEntity();
        Users saved = userRepository.save(user);
        System.out.println(saved.toString());

        return "redirect:/signup";
    }
    @PostMapping("/article/login")
    public String login(UserForm form) {
        System.out.println("로그인 시도: " + form.toString());

        // 아이디(email 형식)와 비밀번호 추출
        String username = form.getUsername();
        String password = form.getPassword();

        // DB에서 username으로 사용자 조회
        Users users = userRepository.findByUsername(username);

        // 사용자 존재 여부 및 비밀번호 비교
        if (users != null && users.getPassword().equals(password)) {
            System.out.println("로그인 성공!");
            return "redirect:/hello"; // 로그인 성공 시 메인페이지로 이동
        } else {
            System.out.println("로그인 실패...");
            return "redirect:/article/login"; // 로그인 실패 시 다시 로그인 페이지로
        }
    }

}