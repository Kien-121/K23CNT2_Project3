package com.giadungmart.service;

import com.giadungmart.entity.Product;
import java.util.List;

public interface ProductService extends BaseService<Product> {

    List<Product> findByCategory(Integer categoryId);

    List<Product> searchByName(String keyword);
}
