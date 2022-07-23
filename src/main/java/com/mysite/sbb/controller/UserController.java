package com.mysite.sbb.controller;

import com.mysite.sbb.repository.UserRepository;
import com.mysite.sbb.ut.Ut;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/doJoin")
    @ResponseBody
    public String doJoin(String email, String password, String name) {
        if (Ut.empty(email)) {
            return "이메일을 작성해주세요.";
        }
        if (userRepository.existsByEmail(email)) {
            return "이메일이 이미 존재 합니다.";
        }
        if (Ut.empty(password)) {
            return "비밀번호를 작성해주세요.";
        }
        if (Ut.empty(name)) {
            return "이름을 작성해주세요.";
        }

        User user = new User();
        user.setRegDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);

        userRepository.save(user);

        return "회원가입이 완료되었습니다.";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public String doLogin(String email, String password, HttpSession session) {
        boolean isLogin = false;
        long logindeUserId = 0;

        if(session.getAttribute()){

        }

        if (Ut.empty(email)) {
            return "이메일을 작성해주세요.";
        }
        if (Ut.empty(password)) {
            return "비밀번호를 작성해주세요.";
        }
        if (!userRepository.existsByEmail(email)) {
            return "이메일이 존재하지 않습니다.";
        }

        Optional<User> opUser = userRepository.finByEmail(email);
        User user = opUser.get();

        if (!user.getPassword().equals(password)) {
            return "비밀번호가 일치하지 않습니다.";
        }

        session.setAttribute("loginedUserId",user.getId());

        return "%s님 환영합니다.".formatted(user.getName());
    }
}
