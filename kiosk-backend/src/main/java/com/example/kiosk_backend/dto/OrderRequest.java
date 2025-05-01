// 📁 src/main/java/com/example/kiosk_backend/dto/OrderRequest.java
package com.example.kiosk_backend.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private List<Item> items;

    @Getter
    @Setter
    public static class Item {
        private Long menuId; // ✅ 반드시 menuId 라는 이름으로 유지
        private int quantity;
    }
}
