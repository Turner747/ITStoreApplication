package com.assessmenttwo.app.repository;

import com.assessmenttwo.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username LIKE CONCAT('%', :query, '%')")
    List<User> searchUsers(String query);

    public default void addUser(User user, RoleRepository roleRepository) {

        if (this.findAll().isEmpty()){
            user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_ADMIN")));
        }
        else{
            user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        }
        this.save(user);
    }
}
