package com.sistic.ecommerce.services;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import com.sistic.ecommerce.model.CustomSessionScope;
import com.sistic.ecommerce.model.Order;
import com.sistic.ecommerce.model.OrderDetail;
import com.sistic.ecommerce.model.Product;
import com.sistic.ecommerce.model.ShoppingCart;
import com.sistic.ecommerce.model.User;
import com.sistic.ecommerce.repository.OrderDetailRepository;
import com.sistic.ecommerce.repository.OrderRepository;
import com.sistic.ecommerce.repository.ProductRepository;
import com.sistic.ecommerce.repository.UserRepository;
import com.sistic.ecommerce.util.CommonFun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
public class OrderDetailService {
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Resource(name = "customSessionScope")
    CustomSessionScope customSessionScope;

    @PostMapping("order-detail")
    public String addOrderDetail(String pid) {
        // product info
        Product product = productRepository.findById(Long.valueOf(pid)).get();

        // create order object
        Order order = null;
        if (!Optional.ofNullable(customSessionScope.getOrder()).isPresent()) {
            order = new Order();
            order.setStatus(0);
            order.setUser(customSessionScope.getUser());
            order.setOrderId(order.generateOrderId());
            orderRepository.save(order);
            customSessionScope.setOrder(order);
        } else {
            order = customSessionScope.getOrder();
        }

        // create order object
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setOrder(order);

        // additional info
        orderDetail.setCount(1);
        orderDetail.setImage(product.getImage());
        orderDetail.setTitle(product.getName());
        orderDetail.setPrice(product.getPrice());

        orderDetailRepository.save(orderDetail);
        return "order_detail";
    }

    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

}