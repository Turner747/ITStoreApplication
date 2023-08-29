package com.assessmenttwo.app.controller;

import com.assessmenttwo.app.model.User;
import com.assessmenttwo.app.repository.RoleRepository;
import com.assessmenttwo.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;


    @GetMapping("/users/register")
    public String registerUserForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "user-register";
    }

    @PostMapping("/users/register")
    public String addUser(@Valid @ModelAttribute("user")User user,
                          BindingResult result, Model model){

        if (result.hasErrors()){
            model.addAttribute("user", user);
            return "user-register";
        }

        if (!user.getPassword().equals(user.getPasswordConfirm())){
            String passwordError = "Passwords do not match";
            model.addAttribute("passwordError", passwordError);
            model.addAttribute("user", user);
            return "user-register";
        }

        if(repository.findByUsername(user.getUsername()) != null){
            String userExists = "Username already exists";
            model.addAttribute("userExists", userExists);
            model.addAttribute("user", user);
            return "user-register";
        }

        user.setPassword(
            passwordEncoder.encode(user.getPassword())
        );

        repository.addUser(user, roleRepository);

        return "redirect:/login";
    }
}
