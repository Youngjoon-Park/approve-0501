// 📁 src/main/java/com/example/kiosk_backend/entity/OrderItem.java
package com.example.kiosk_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ 메뉴 수량
    private int quantity;

    // ✅ 주문과 연결 (다대일 관계)
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // ✅ 메뉴와 연결 (다대일 관계)
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private AdminMenu menu;
}
