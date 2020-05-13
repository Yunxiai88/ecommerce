package com.sistic.ecommerce.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import com.sistic.ecommerce.model.CustomSessionScope;
import com.sistic.ecommerce.model.Order;
import com.sistic.ecommerce.model.OrderDetail;
import com.sistic.ecommerce.services.OrderDetailService;
import com.sistic.ecommerce.services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderDetailController {
    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    OrderService orderService;

    @Resource(name = "customSessionScope")
    CustomSessionScope customSessionScope;

    /**
     * get all items in current order
     * 
     * @param model
     * @return
     */
    @GetMapping("/items")
    public String getAll(Model model) {
        List<OrderDetail> items = orderDetailService.findAll();
        model.addAttribute("items", items);

        String orderId = "";
        Order order = customSessionScope.getOrder();
        if (order != null)
            orderId = "" + order.getId();

        model.addAttribute("orderId", orderId);
        return "shoppingcart";
    }

    /**
     * add one item into shopping cart
     * 
     * @param pid
     * @return
     * @throws Exception
     */
    @PostMapping("/items")
    public String addToCart(@RequestParam String pid) {
        try {
            orderDetailService.addOrderDetail(pid);
        } catch (Exception e) {
            return "redirect:/login";
        }
        return "redirect:/items";
    }

    /**
     * update one item etc number of products
     * 
     * @param odid
     * @param request
     * @return
     */
    @PostMapping("/items/{odid}")
    public ResponseEntity<?> updateOrderDetail(@PathVariable String odid, ServletRequest request) {
        String count = request.getParameter("count");
        OrderDetail item = null;
        try {
            item = orderDetailService.updateOrderDetail(odid, Integer.valueOf(count));

            // update total amount for order
            Order order = customSessionScope.getOrder();
            orderService.calculateAmount(order.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(item);
    }

    /**
     * delete item
     */
    @RequestMapping(value = "/items/delete/{odid}")
    public String deleteCart(@PathVariable String odid) {
        orderDetailService.deleteOrderDetail(odid);

        return "redirect:/items";
    }
}