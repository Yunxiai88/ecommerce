package com.sistic.ecommerce.controller;

import java.util.List;

import javax.servlet.ServletRequest;

import com.sistic.ecommerce.model.Order;
import com.sistic.ecommerce.services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/orders")
    public String getAll(Model model) {
        List<Order> orders = orderService.getAll();
        model.addAttribute("orders", orders);
        return "order";
    }

    @PostMapping("/orders")
    public String addOrder(ServletRequest request) {
        String pid = request.getParameter("pid");
        //orderService.addOrder(pdi);
        return "order_detail";
    }
}