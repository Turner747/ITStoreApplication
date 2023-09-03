package com.assessmenttwo.app;

import com.assessmenttwo.app.model.Privilege;
import com.assessmenttwo.app.model.Role;
import com.assessmenttwo.app.repository.PrivilegeRepository;
import com.assessmenttwo.app.repository.RoleRepository;
import com.assessmenttwo.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        Privilege customerPrivilege
                = createPrivilegeIfNotFound("CUSTOMER_PRIVILEGE");
        Privilege productPrivilege
                = createPrivilegeIfNotFound("PRODUCT_PRIVILEGE");
        Privilege orderPrivilege
                = createPrivilegeIfNotFound("ORDER_PRIVILEGE");
        Privilege userPrivilege
                = createPrivilegeIfNotFound("USER_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
                customerPrivilege, productPrivilege, orderPrivilege, userPrivilege);
        List<Privilege> userPrivileges = Arrays.asList(
                customerPrivilege, productPrivilege, orderPrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", userPrivileges);

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");

        alreadySetup = true;
    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
     public Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
