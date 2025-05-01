// 📁 src/main/java/com/example/kiosk_backend/entity/AdminMenu.java
package com.example.kiosk_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AdminMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;
    private String description;

    private String imageFilename; // ✅ 파일명 저장 추가!

    public void update(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
