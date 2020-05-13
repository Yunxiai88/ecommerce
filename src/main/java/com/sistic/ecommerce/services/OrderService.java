package com.sistic.ecommerce.services;

import java.util.List;
import java.util.Optional;

import com.sistic.ecommerce.model.Order;
import com.sistic.ecommerce.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    /**
     * get all orders
     * 
     * @return
     */
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    /**
     * get order using order id
     * 
     * @param oid
     * @return
     */
    public Order getOrder(String oid) {
        Optional<Order> orderOptional = orderRepository.findById(Integer.valueOf(oid));
        if (orderOptional.isPresent())
            return orderOptional.get();
        return null;
    }

    /**
     * update order info
     * 
     * @param oid
     * @param status
     * @return
     */
    public Order updateOrder(String oid, String status) {
        Optional<Order> orderOptional = orderRepository.findById(Integer.valueOf(oid));
        Order order = null;
        if (orderOptional.isPresent()) {
            order = orderOptional.get();
            order.setStatus(Integer.valueOf(status));
        }
        return order;
    }

    /**
     * this part should be done in DB -- trigger when count of particular item
     * changed
     * 
     * @return
     * @throws Exception
     */
    public void calculateAmount(int oid) throws Exception {
        Optional<Order> orderOptional = orderRepository.findById(oid);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            Optional<Float> amountOptional = order.getOrderDetails().stream()
                    .map(orderDetail -> orderDetail.getAmount()).reduce((total, value) -> total + value);

            order.setAmount(amountOptional.get());
            orderRepository.save(order);
        } else {
            throw new Exception("No Order Found");
        }
    }

}