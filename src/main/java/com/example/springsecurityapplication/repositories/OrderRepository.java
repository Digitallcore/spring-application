package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Orders;
import com.example.springsecurityapplication.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByPerson(Person person);
}
