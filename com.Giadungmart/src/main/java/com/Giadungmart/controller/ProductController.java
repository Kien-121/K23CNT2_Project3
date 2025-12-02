package com.giadungmart.controller;

import com.giadungmart.entity.Product;
import com.giadungmart.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // Get all
    @GetMapping
    public List<Product> getAll() {
        return service.findAll();
    }

    // Get by ID
    @GetMapping("/{id}")
    public Product getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    // Create or Update
    @PostMapping
    public Product save(@RequestBody Product product) {
        return service.save(product);
    }

    // Update (PUT)
    @PutMapping("/{id}")
    public Product update(@PathVariable Integer id, @RequestBody Product product) {
        product.setId(id);
        return service.save(product);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    // Get product by category
    @GetMapping("/category/{categoryId}")
    public List<Product> getByCategory(@PathVariable Integer categoryId) {
        return service.findByCategory(categoryId);
    }

    // Search by name
    @GetMapping("/search")
    public List<Product> search(@RequestParam String keyword) {
        return service.searchByName(keyword);
    }
}
