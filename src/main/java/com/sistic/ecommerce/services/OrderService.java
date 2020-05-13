package com.sistic.ecommerce.services;

import java.util.List;

import com.sistic.ecommerce.model.Order;
import com.sistic.ecommerce.model.OrderDetail;
import com.sistic.ecommerce.model.Product;
import com.sistic.ecommerce.model.User;
import com.sistic.ecommerce.repository.OrderRepository;
import com.sistic.ecommerce.repository.ProductRepository;
import com.sistic.ecommerce.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order addOrder(Order order) {
        return null;
    }

}