package com.assessmenttwo.app.repository;

import com.assessmenttwo.app.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PrivilegeRepository  extends JpaRepository<Privilege, Long> {

    @Query("SELECT p FROM Privilege p WHERE p.name = :name")
    Privilege findByName(String name);
}
