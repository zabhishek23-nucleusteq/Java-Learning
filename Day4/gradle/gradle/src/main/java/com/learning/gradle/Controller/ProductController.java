package com.learning.gradle.Controller;

import com.learning.gradle.Entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final List<Product> products = new ArrayList<>(
            Arrays.asList(
                    new Product(1, "Laptop", 75000, "Electronics"),
                    new Product(2, "Phone", 50000, "Electronics"),
                    new Product(3, "Shoes", 3000, "Fashion"),
                    new Product(4, "Watch", 10000, "Accessories")
            )
    );

    private int currentId = 1;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}