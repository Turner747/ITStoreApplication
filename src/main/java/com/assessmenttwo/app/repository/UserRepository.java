package com.assessmenttwo.app.repository;

import com.assessmenttwo.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    public default void addUser(User user, RoleRepository roleRepository) {

        if (this.findAll().isEmpty()){
            user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_ADMIN")));
        }
        else{
            user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        }
        user.setEnabled(true);
        this.save(user);
    }
}
