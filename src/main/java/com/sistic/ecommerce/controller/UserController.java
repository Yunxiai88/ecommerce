package com.sistic.ecommerce.controller;

import java.util.List;

import com.sistic.ecommerce.model.Product;
import com.sistic.ecommerce.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    ProductService productService;

    @GetMapping("/*")
    public String index(Model model) {
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }
}