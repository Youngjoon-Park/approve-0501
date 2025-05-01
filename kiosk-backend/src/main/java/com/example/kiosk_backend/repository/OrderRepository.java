package com.example.kiosk_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kiosk_backend.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
