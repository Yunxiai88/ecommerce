package com.sistic.ecommerce.services;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import com.sistic.ecommerce.model.Product;
import com.sistic.ecommerce.repository.ProductRepository;
import com.sistic.ecommerce.util.CompressImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProductService {
    @Value("${image.file-path}")
    String filePath = "";

    @Autowired
    ProductRepository productRepository;

    public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    public Optional<Product> findProduct(String id) {
        Optional<Product> product = productRepository.findById(Long.valueOf(id));
        return product;
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(Long.valueOf(id));
    }

    public String saveImage(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path target = Paths.get(filePath + "/images/").toAbsolutePath().normalize().resolve(fileName);

            // resize image and store
            CompressImage.compress(file.getBytes(), target.toString());
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not store file " + fileName + ". Please try again!");
        }
    }

}