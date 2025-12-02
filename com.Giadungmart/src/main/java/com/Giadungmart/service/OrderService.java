package com.giadungmart.service;

import com.giadungmart.entity.Order;
import java.util.List;

public interface OrderService {

    Order createOrder(Order order);

    List<Order> findAll();

    Order findById(Integer id);

    void delete(Integer id);
}
