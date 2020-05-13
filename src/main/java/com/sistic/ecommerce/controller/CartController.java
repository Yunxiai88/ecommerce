package com.sistic.ecommerce.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.ServletRequest;

import com.sistic.ecommerce.model.Cart;
import com.sistic.ecommerce.services.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("/carts")
    public String getAll(Model model) {
        List<Cart> carts = cartService.findAll();
        model.addAttribute("carts", carts);
        return "cart";
    }

    @GetMapping("/carts/{pid}")
    public String addCart(@PathVariable String pid, Principal principal) {
        try {
            cartService.addCart(pid, principal.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/carts";
    }

    @RequestMapping(value = "/carts/delete/{pid}")
    public String deleteCart(@PathVariable String pid) {
        cartService.deleteCart(pid);

        return "redirect:/carts";
    }

    @PostMapping(value = "/carts/{pid}")
    public ResponseEntity<?> updateCart(@PathVariable String pid, ServletRequest request) {
        String count = request.getParameter("count");
        Cart cart = null;
        try {
            cart = cartService.updateCart(pid, count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(cart);
    }
}