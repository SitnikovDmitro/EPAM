package com.ra.controller;

import com.ra.model.entity.IntResult;
import com.ra.model.entity.Product;
import com.ra.model.entity.User;
import com.ra.model.enums.Lang;
import com.ra.model.exceptions.DBException;
import com.ra.model.repository.UserRepository;
import com.ra.model.service.ProductService;
import com.ra.model.service.TextService;
import com.ra.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

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
            session.setAttribute("text", new TextService());
            session.setAttribute("lang", Lang.RU);
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

    @GetMapping("/showLogin")
    public String showLogin() {
        return "common/login";
    }


    @GetMapping("/showError")
    public String error() {
        return "common/error";
    }
}
