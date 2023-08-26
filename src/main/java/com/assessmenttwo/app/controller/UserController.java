package com.assessmenttwo.app.controller;

import com.assessmenttwo.app.model.User;
import com.assessmenttwo.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository repository;

    public UserController(UserRepository repo){
        repository = repo;
    }

    @GetMapping("/users/register")
    public String registerUserForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "user-register";
    }

    @PostMapping("/users/register")
    public String addUser(@ModelAttribute("order")User user){
        if(repository.findByUsername(user.getUsername()) != null){
            // return error
        }

        // validate password
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        repository.save(user);

        return "redirect:/login";
    }
}
