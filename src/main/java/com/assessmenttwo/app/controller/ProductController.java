package com.assessmenttwo.app.controller;

import com.assessmenttwo.app.model.Product;
import com.assessmenttwo.app.repository.ProductRepository;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repo){
        repository = repo;
    }

    @GetMapping("/products")
    public String productList(Model model){
        List<Product> products = repository.findAll();
        model.addAttribute("products", products);
        return "product/product-list";
    }

    @GetMapping("/products/search")
    public String searchProducts(@RequestParam(value = "query")String query, Model model){
        List<Product> products = repository.searchProducts(query);
        model.addAttribute("products", products);
        return "product/product-list";
    }

    @GetMapping("/products/add")
    public String createProduct(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "product/product-add";
    }

    @PostMapping("/products/add")
    public String saveProduct(@Valid @ModelAttribute("product")Product product,
                              BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("product", product);
            return "product/product-add";
        }

        repository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/{productId}")
    public String viewProduct(@PathVariable("productId")Long productId, Model model){
        Product product = repository.findById(productId).get();
        model.addAttribute("product", product);
        return "product/product-view";
    }

    @GetMapping("/products/{productId}/edit")
    public String editProduct(@PathVariable("productId")Long productId, Model model){
        Product product = repository.findById(productId).get();
        model.addAttribute("product", product);
        return "product/product-edit";
    }

    @PostMapping("/products/{productId}/edit")
    public String updateProduct(@PathVariable("productId")Long productId,
                                @Valid @ModelAttribute("product")Product product,
                                BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("product", product);
            return "product/product-edit";
        }

        repository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/{productId}/delete")
    public String deleteProduct(@PathVariable("productId")Long productId){
        repository.deleteById(productId);
        return "redirect:/products";
    }
    
}
