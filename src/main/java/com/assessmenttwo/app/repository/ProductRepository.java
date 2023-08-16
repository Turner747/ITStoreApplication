package com.assessmenttwo.app.repository;

import com.assessmenttwo.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
