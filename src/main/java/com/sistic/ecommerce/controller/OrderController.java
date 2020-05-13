package com.sistic.ecommerce.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import com.sistic.ecommerce.model.CustomSessionScope;
import com.sistic.ecommerce.model.Order;
import com.sistic.ecommerce.services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

    @Resource(name = "customSessionScope")
    CustomSessionScope customSessionScope;

    /**
     * get all order history
     * 
     * @param model
     * @return
     */
    @GetMapping("/orders")
    public String getAll(Model model) {
        List<Order> orders = orderService.getAll();
        model.addAttribute("orders", orders);
        return "order";
    }

    /**
     * update order -- checkout
     * 
     * @param oid
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/orders/{oid}")
    public String updateOrder(@PathVariable String oid, Model model, ServletRequest request) {
        String status = request.getParameter("status");
        Order order = orderService.updateOrder(oid, status);
        model.addAttribute("order", order);

        // remove order from session
        customSessionScope.setOrder(null);
        return "order_detail";
    }

    /**
     * get order details
     * 
     * @param oid
     * @param model
     * @return
     */
    @GetMapping("/orders/{oid}")
    public String getOrder(@PathVariable String oid, Model model) {
        Order order = orderService.getOrder(oid);
        if (order != null) {
            model.addAttribute("order", order);
            return "order_detail";
        } else
            return "";
    }
}