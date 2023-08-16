package com.assessmenttwo.app.repository;

import com.assessmenttwo.app.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
