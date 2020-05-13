package com.sistic.ecommerce.controller;

import java.util.List;

import com.sistic.ecommerce.model.OrderDetail;
import com.sistic.ecommerce.services.OrderDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderDetailController {
    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/order-details")
    public String getAll(Model model) {
        List<OrderDetail> items = orderDetailService.findAll();
        model.addAttribute("items", items);
        return "shoppingcart";
    }

    @PostMapping("/order-details")
    public String checkout(@RequestParam String pid) {
        orderDetailService.addOrderDetail(pid);
        return "cart";
    }

    @GetMapping("/order-details/{odid}")
    public String getOrderDetail(@PathVariable String odid) {
        return "order_detail";
    }
}