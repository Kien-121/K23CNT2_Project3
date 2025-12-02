package com.giadungmart.service.impl;

import com.giadungmart.entity.Order;
import com.giadungmart.entity.OrderItem;
import com.giadungmart.repository.OrderRepository;
import com.giadungmart.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repo;

    public OrderServiceImpl(OrderRepository repo) {
        this.repo = repo;
    }

    @Override
    public Order createOrder(Order order) {

        double total = 0;

        for (OrderItem item : order.getItems()) {
            double itemTotal = item.getQuantity() * item.getPrice();
            item.setTotal(itemTotal);
            item.setOrder(order);
            total += itemTotal;
        }

        order.setTotal(total);
        return repo.save(order);
    }

    @Override
    public Order findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Order> findAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
