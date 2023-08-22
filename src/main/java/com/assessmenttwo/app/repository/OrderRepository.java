package com.assessmenttwo.app.repository;

import com.assessmenttwo.app.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.id = :id")
    List<Order> searchOrders(Long id);
}
