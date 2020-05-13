package com.sistic.ecommerce.controller;

import java.util.List;

import com.sistic.ecommerce.model.Product;
import com.sistic.ecommerce.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductsController {

    @Autowired
    ProductService productService;

    @GetMapping(value = "/products")
    public String getAllProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/product")
    public String createProduct(Model model){
        model.addAttribute("product", new Product());
        return "product";
    }

    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable String id, Model model) throws Exception {
        Product product = productService.findProduct(id).orElseThrow(() -> new Exception("No Product Found."));
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return "redirect:/*";
    }

    @PostMapping("/products")
    public String createProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file) {
        System.out.println(product.toString());
        try {
            if (!file.isEmpty()) {
                // step1. store to local
                String imageName = productService.saveImage(file);

                // step2. set image name to product
                product.setImage(imageName);
            }

            // step3. save to db
            if (StringUtils.isEmpty(product.getId()))
                productService.saveProduct(product);
            else {
                Product existProduct = productService.findProduct(String.valueOf(product.getId())).get();
                // reset values
                existProduct.setCode(product.getCode());
                existProduct.setName(product.getName());
                existProduct.setPrice(product.getPrice());
                existProduct.setQuantity(product.getQuantity());
                productService.saveProduct(existProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/*";
    }
}