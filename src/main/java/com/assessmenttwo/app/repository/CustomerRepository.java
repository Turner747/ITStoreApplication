package com.assessmenttwo.app.repository;

import com.assessmenttwo.app.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.firstName LIKE CONCAT('%', :query, '%') OR c.lastName LIKE CONCAT('%', :query, '%')")
    List<Customer> searchCustomers(String query);
}
