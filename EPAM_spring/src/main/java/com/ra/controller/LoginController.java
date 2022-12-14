package com.ra.controller;

import com.ra.model.entity.IntResult;
import com.ra.model.entity.User;
import com.ra.exceptions.DBException;
import com.ra.repository.UserRepository;
import com.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/login")
    @ResponseBody
    public IntResult login(@RequestParam(required = false) String password,
                           @RequestParam(required = false) String username,
                           HttpSession session) throws DBException {

        User user = userService.getUser(username, password);
        if (user != null && (user.getRole() == 1 || user.getRole() == 3)) {
            session.setAttribute("loggedUser", user);
            return new IntResult(user.getRole());
        } else {
            return new IntResult(-1);
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/showLogin";
    }

    @GetMapping("")
    public String start() {
        return "common/login";
    }

    @GetMapping("/showLogin")
    public String showLogin() {
        return "common/login";
    }


    @GetMapping("/showError")
    public String error(@RequestParam String code,
                        @RequestParam String description,
                        Model model) {

        model.addAttribute("code", code);
        model.addAttribute("description", description);

        return "common/error";
    }
}
