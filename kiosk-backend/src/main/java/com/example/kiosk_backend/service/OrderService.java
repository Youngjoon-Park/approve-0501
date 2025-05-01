// 📁 src/main/java/com/example/kiosk_backend/service/OrderService.java
package com.example.kiosk_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kiosk_backend.dto.OrderRequest;
import com.example.kiosk_backend.entity.AdminMenu;
import com.example.kiosk_backend.entity.Order;
import com.example.kiosk_backend.entity.OrderItem;
import com.example.kiosk_backend.repository.AdminMenuRepository;
import com.example.kiosk_backend.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AdminMenuRepository adminMenuRepository;

    // 📁 src/main/java/com/example/kiosk_backend/service/OrderService.java

    @Transactional
    public Long saveOrder(OrderRequest request) {

        System.out.println("🛠️ OrderRequest 전체: " + request);
        Order order = new Order();

        List<OrderItem> items = request.getItems().stream().map(i -> {
            AdminMenu menu = adminMenuRepository.findById(i.getMenuId())
                    .orElseThrow(() -> new IllegalArgumentException("없는 메뉴입니다."));

            System.out.println("💡 주문 요청 menuId = " + i.getMenuId());

            OrderItem item = new OrderItem();
            item.setMenu(menu);
            item.setQuantity(i.getQuantity());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setItems(items);

        // ✅ summary 생성
        String summary = items.stream()
                .map(i -> i.getMenu().getName())
                .limit(2) // 두 개까지만 요약
                .collect(Collectors.joining(", "));
        if (items.size() > 2)
            summary += " 외";

        order.setSummary(summary);

        orderRepository.save(order);

        return order.getId();
    }

}
