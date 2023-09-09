package com.assessmenttwo.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "First name must be provided")
    private String firstName;
    @NotEmpty(message = "Last name must be provided")
    private String lastName;
    @NotEmpty(message = "Email must be provided")
    @Email
    private String email;
    @NotEmpty(message = "Phone number must be provided")
    private String phone;
    @NotEmpty(message = "Street must be provided")
    private String street;
    @NotEmpty(message = "Suburb must be provided")
    private String suburb;
    @NotEmpty(message = "State must be provided")
    private String state;
    @NotEmpty(message = "Postcode must be provided")
    private String postcode;

    @CreationTimestamp
    @Column(name= "created_on", nullable = false, updatable = false)
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
