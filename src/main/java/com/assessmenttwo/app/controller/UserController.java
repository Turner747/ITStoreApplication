package com.assessmenttwo.app.controller;

import com.assessmenttwo.app.model.Customer;
import com.assessmenttwo.app.model.User;
import com.assessmenttwo.app.repository.RoleRepository;
import com.assessmenttwo.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

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

        if(userRepository.findByUsername(user.getUsername()) != null){
            String userExists = "Username already exists";
            model.addAttribute("userExists", userExists);
            model.addAttribute("user", user);
            return "user-register";
        }

        user.setPassword(
            passwordEncoder.encode(user.getPassword())
        );

        userRepository.addUser(user, roleRepository);

        return "redirect:/login";
    }

    @GetMapping("/users/manage")
    public String userList(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user/user-list";
    }

    @GetMapping("/users/manage/search")
    public String searchCustomers(@RequestParam(value = "query")String query, Model model){
        List<User> users = userRepository.searchUsers(query);
        model.addAttribute("users", users);
        return "user/user-list";
    }

    @GetMapping("/users/manage/{userId}/delete")
    public String deleteCustomer(@PathVariable("userId")Long userId){
        userRepository.deleteById(userId);
        return "redirect:/users/manage";
    }

    @GetMapping("/users/profile/{username}/edit")
    public String editUserProfile(@PathVariable("username")String username, Model model){
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "user/user-edit";
    }

    @PostMapping("/users/profile/{userId}/edit")
    public String updateUserProfile(@PathVariable("userId")Long userId, @ModelAttribute("user") User user,
                                    BindingResult result, Model model){

        User existingUser = userRepository.findById(userId).get();
        Boolean usernameChanged = false;

        if(user.getFirstName() != null && !user.getFirstName().equals("")){
            existingUser.setFirstName(user.getFirstName());
        }

        if(user.getLastName() != null && !user.getLastName().equals("")){
            existingUser.setLastName(user.getLastName());
        }

        if(user.getUsername() != null && !user.getUsername().equals("")){
            if (!user.getUsername().equals(existingUser.getUsername())){
                if(userRepository.findByUsername(user.getUsername()) != null){
                    String userExists = "Username already exists";
                    model.addAttribute("userExists", userExists);
                    model.addAttribute("user", user);
                    return "user/user-edit";
                }
                existingUser.setUsername(user.getUsername());
                usernameChanged = true;
            }
        }

        if (user.getPassword() != null && !user.getPassword().equals("")) {
            if (!user.getPassword().equals(user.getPasswordConfirm())) {
                String passwordError = "Passwords do not match";
                model.addAttribute("passwordError", passwordError);
                model.addAttribute("user", user);
                return "user/user-edit";
            }

            existingUser.setPassword(
                    passwordEncoder.encode(user.getPassword())
            );
        }

        userRepository.save(existingUser);

        if(usernameChanged){
            return "redirect:/login";
        }

        return "redirect:/";
    }
}
