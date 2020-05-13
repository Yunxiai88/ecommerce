package com.sistic.ecommerce.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import com.sistic.ecommerce.model.CustomSessionScope;
import com.sistic.ecommerce.model.Order;
import com.sistic.ecommerce.model.OrderDetail;
import com.sistic.ecommerce.model.Product;
import com.sistic.ecommerce.model.User;
import com.sistic.ecommerce.repository.OrderDetailRepository;
import com.sistic.ecommerce.repository.OrderRepository;
import com.sistic.ecommerce.repository.ProductRepository;
import com.sistic.ecommerce.repository.UserRepository;

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

    /**
     * add one item -- add to cart
     * 
     * @param pid
     * @return
     * @throws Exception
     */
    @PostMapping("order-detail")
    public String addOrderDetail(String pid) throws Exception {
        // check user info
        User user = customSessionScope.getUser();
        if(user == null){
            throw new Exception("user session expired.");
        }

        // product info
        Product product = productRepository.findById(Long.valueOf(pid)).get();

        // create order object
        Order order = customSessionScope.getOrder();
        if (!Optional.ofNullable(order).isPresent() || order.getStatus() != 0) {
            System.out.println("create order session");
            order = new Order();
            order.setStatus(0);
            order.setUser(user);
            order.setOrderId(order.generateOrderId());
            orderRepository.save(order);
            customSessionScope.setOrder(order);
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
        orderDetail.setAmount(product.getPrice());

        orderDetailRepository.save(orderDetail);
        return "order_detail";
    }

    /**
     * find all orders
     * 
     */
    public List<OrderDetail> findAll() {
        Order order = customSessionScope.getOrder();
        if (order != null) {
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
            
            return orderDetails;
        } else {
            return null;
        }
    }

    /**
     * delete order detail
     */
    public void deleteOrderDetail(String odid) {
        orderDetailRepository.deleteById(Long.valueOf(odid));
    }

    /**
     * update amount when customer changed the count
     * 
     * @param odid
     * @param count
     * @return
     * @throws Exception
     */
    public OrderDetail updateOrderDetail(String odid, int count) throws Exception {
        Optional<OrderDetail> odOptional = orderDetailRepository.findById(Long.valueOf(odid));
        if (odOptional.isPresent()) {
            OrderDetail od = odOptional.get();
            od.setCount(Integer.valueOf(count));

            float amount = od.getProduct().getPrice() * count;
            BigDecimal bd = new BigDecimal(amount);
            od.setAmount(bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
            return orderDetailRepository.save(od);
        } else {
            throw new Exception("No Cart Found.");
        }
    }

}