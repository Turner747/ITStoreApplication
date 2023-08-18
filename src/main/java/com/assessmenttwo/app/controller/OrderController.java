package com.assessmenttwo.app.controller;

import com.assessmenttwo.app.model.Order;
import com.assessmenttwo.app.repository.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrderController {
    
    private final OrderRepository repository;
    
    public OrderController(OrderRepository repo){
        repository = repo;
    }

    @GetMapping("/orders")
    public String orderList(Model model){
        List<Order> orders = repository.findAll();
        model.addAttribute("orders", orders);
        return "order-list";
    }

    @GetMapping("/orders/add")
    public String createOrder(Model model){
        Order order = new Order();
        model.addAttribute("order", order);
        return "order-add";
    }

    @PostMapping("/orders/add")
    public String saveOrder(@ModelAttribute("order")Order order){
        repository.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/orders/{orderId}")
    public String viewOrder(@PathVariable("orderId")Long orderId, Model model){
        Order order = repository.findById(orderId).get();
        model.addAttribute("order", order);
        return "order-view";
    }

    @GetMapping("/orders/{orderId}/edit")
    public String editOrder(@PathVariable("orderId")Long orderId, Model model){
        Order order = repository.findById(orderId).get();
        model.addAttribute("order", order);
        return "order-edit";
    }

    @PostMapping("/orders/{orderId}/edit")
    public String updateOrder(@PathVariable("orderId")Long orderId, @ModelAttribute("order")Order order){
        order.setId(orderId);
        repository.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/orders/{orderId}/delete")
    public String deleteOrder(@PathVariable("orderId")Long orderId){
        repository.deleteById(orderId);
        return "redirect:/orders";
    }
}
