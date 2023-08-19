package com.assessmenttwo.app.controller;

import com.assessmenttwo.app.model.Customer;
import com.assessmenttwo.app.repository.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repo){
        repository = repo;
    }

    @GetMapping("/customers")
    public String customerList(Model model){
        List<Customer> customers = repository.findAll();
        model.addAttribute("customers", customers);
        return "customer-list";
    }

    @GetMapping("/customers/search")
    public String searchCustomers(@RequestParam(value = "query")String query, Model model){
        List<Customer> customers = repository.searchCustomers(query);
        model.addAttribute("customers", customers);
        return "customer-list";
    }

    @GetMapping("/customers/add")
    public String createCustomer(Model model){
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customer-add";
    }

    @PostMapping("/customers/add")
    public String saveCustomer(@ModelAttribute("customer")Customer customer){
        repository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/{customerId}")
    public String viewCustomer(@PathVariable("customerId")Long customerId, Model model){
        Customer customer = repository.findById(customerId).get();
        model.addAttribute("customer", customer);
        return "customer-view";
    }

    @GetMapping("/customers/{customerId}/edit")
    public String editCustomer(@PathVariable("customerId")Long customerId, Model model){
        Customer customer = repository.findById(customerId).get();
        model.addAttribute("customer", customer);
        return "customer-edit";
    }

    @PostMapping("/customers/{customerId}/edit")
    public String updateCustomer(@PathVariable("customerId")Long customerId, @ModelAttribute("customer")Customer customer){
        customer.setId(customerId);
        repository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/{customerId}/delete")
    public String deleteCustomer(@PathVariable("customerId")Long customerId){
        repository.deleteById(customerId);
        return "redirect:/customers";
    }
}
