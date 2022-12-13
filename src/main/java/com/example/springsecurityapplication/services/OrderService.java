package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.models.Orders;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Orders getOrderId(int id){
        Optional<Orders> optionalProduct = orderRepository.findById(id);
        return optionalProduct.orElse(null);
    }
}
