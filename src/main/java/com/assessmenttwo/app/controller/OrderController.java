package com.assessmenttwo.app.controller;

import com.assessmenttwo.app.model.Customer;
import com.assessmenttwo.app.model.Order;
import com.assessmenttwo.app.model.OrderLine;
import com.assessmenttwo.app.model.Product;
import com.assessmenttwo.app.repository.CustomerRepository;
import com.assessmenttwo.app.repository.OrderRepository;
import com.assessmenttwo.app.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    
    public OrderController(OrderRepository orderRepo, ProductRepository productRepo, CustomerRepository customerRepo){
        orderRepository = orderRepo;
        productRepository = productRepo;
        customerRepository = customerRepo;
    }

    @GetMapping("/orders")
    public String orderList(Model model){
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "order-list";
    }

    @GetMapping("/orders/search")
    public String searchOrders(@RequestParam(value = "query")String query, Model model){
        Long id = Long.parseLong(query);
        List<Order> orders = orderRepository.searchOrders(id);
        model.addAttribute("orders", orders);
        return "order-list";
    }

    @GetMapping("/orders/add")
    public String createOrder(Model model){
        Order order = new Order();/*
        var line = OrderLine.builder().build();
        order.setOrderLines(new ArrayList<OrderLine>());
        order.getOrderLines().add(line);*/
        List<Product> products = productRepository.findAll();
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("order", order);
        model.addAttribute("customers", customers);
        model.addAttribute("products", products);
        return "order-add";
    }

    @PostMapping("/orders/add")
    public String saveOrder(@ModelAttribute("order")Order order){
        orderRepository.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/orders/{orderId}")
    public String viewOrder(@PathVariable("orderId")Long orderId, Model model){
        Order order = orderRepository.findById(orderId).get();
        model.addAttribute("order", order);
        return "order-view";
    }

    @GetMapping("/orders/{orderId}/edit")
    public String editOrder(@PathVariable("orderId")Long orderId, Model model){
        Order order = orderRepository.findById(orderId).get();
        List<Product> products = productRepository.findAll();
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        model.addAttribute("products", products);
        model.addAttribute("order", order);
        return "order-edit";
    }

    @PostMapping("/orders/{orderId}/edit")
    public String updateOrder(@PathVariable("orderId")Long orderId, @ModelAttribute("order")Order order){
        order.setId(orderId);
        orderRepository.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/orders/{orderId}/delete")
    public String deleteOrder(@PathVariable("orderId")Long orderId){
        orderRepository.deleteById(orderId);
        return "redirect:/orders";
    }
}
