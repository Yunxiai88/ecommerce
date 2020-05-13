package com.sistic.ecommerce.services;

import java.util.List;
import java.util.Optional;

import com.sistic.ecommerce.model.Cart;
import com.sistic.ecommerce.model.Product;
import com.sistic.ecommerce.model.User;
import com.sistic.ecommerce.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart addCart(String id, String email) throws Exception {
        Product product = productService.findProduct(id).orElseThrow(() -> new Exception("No Product Found."));
        User user = userService.findByEmail(email).orElseThrow(() -> new Exception("No User Found."));

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    public void deleteCart(String cid) {
        cartRepository.deleteById(Long.valueOf(cid));
    }

    public Cart updateCart(String cid, String count) throws Exception {
        Optional<Cart> cartOptional = cartRepository.findById(Long.valueOf(cid));
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.setCount(Integer.valueOf(count));
            cart.setAmount(cart.getProduct().getPrice() * Integer.valueOf(count));
            return cartRepository.save(cart);
        } else {
            throw new Exception("No Cart Found.");
        }
    }
}