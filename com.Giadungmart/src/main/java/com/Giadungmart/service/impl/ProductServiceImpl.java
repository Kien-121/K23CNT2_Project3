package com.giadungmart.service.impl;

import com.giadungmart.entity.Product;
import com.giadungmart.repository.ProductRepository;
import com.giadungmart.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Product> findAll() {
        return repo.findAll();
    }

    @Override
    public Product findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return repo.save(product);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public List<Product> findByCategory(Integer categoryId) {
        return repo.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> searchByName(String keyword) {
        return repo.searchByName(keyword);
    }
}
