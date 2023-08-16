package com.assessmenttwo.app.repository;

import com.assessmenttwo.app.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
