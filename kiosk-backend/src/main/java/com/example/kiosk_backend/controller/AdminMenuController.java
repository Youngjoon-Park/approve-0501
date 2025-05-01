// 📁 src/main/java/com/example/kiosk_backend/controller/AdminMenuController.java
package com.example.kiosk_backend.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.kiosk_backend.entity.AdminMenu;
import com.example.kiosk_backend.repository.AdminMenuRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/menus")
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://kiosktest.shop",
        "https://kiosktest.shop",
        "http://3.38.6.220:8081" // ✅ 추가!!
})

public class AdminMenuController {

    private final AdminMenuRepository adminMenuRepository;

    // ✅ 메뉴 목록 조회 (검색 기능 포함)
    @GetMapping
    public ResponseEntity<List<AdminMenu>> getAllMenus(@RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(adminMenuRepository.findByNameContaining(search));
        } else {
            return ResponseEntity.ok(adminMenuRepository.findAllByOrderByIdDesc());
        }
    }

    // ✅ 메뉴 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<AdminMenu> getMenuById(@PathVariable Long id) {
        AdminMenu menu = adminMenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ 메뉴를 찾을 수 없습니다."));
        return ResponseEntity.ok(menu);
    }

    // ✅ 이름으로 메뉴 하나 찾기 (QR 추가용)
    @GetMapping("/search")
    public ResponseEntity<AdminMenu> searchMenuByName(@RequestParam("name") String name) {
        AdminMenu menu = adminMenuRepository.findTopByNameContaining(name)
                .orElseThrow(() -> new RuntimeException("❌ 해당 메뉴를 찾을 수 없습니다."));
        return ResponseEntity.ok(menu);
    }

    // ✅ 메뉴 등록
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createMenu(
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        AdminMenu menu = new AdminMenu();
        menu.update(name, price, description);

        if (imageFile != null && !imageFile.isEmpty()) {
            saveImage(imageFile, menu);
        }

        adminMenuRepository.save(menu);
        return ResponseEntity.ok("✅ 메뉴 등록 완료");
    }

    // ✅ 메뉴 수정
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<String> updateMenu(
            @PathVariable Long id,
            @RequestPart("name") String name,
            @RequestPart("price") String price,
            @RequestPart("description") String description,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        AdminMenu menu = adminMenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ 메뉴를 찾을 수 없습니다."));

        menu.update(name, Integer.parseInt(price), description);

        if (imageFile != null && !imageFile.isEmpty()) {
            saveImage(imageFile, menu);
        }

        adminMenuRepository.save(menu);
        return ResponseEntity.ok("✅ 메뉴 수정 완료");
    }

    // ✅ 메뉴 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long id) {
        adminMenuRepository.deleteById(id);
        return ResponseEntity.ok("✅ 메뉴 삭제 완료");
    }

    // ✅ 공통: 이미지 저장 메서드
    private void saveImage(MultipartFile imageFile, AdminMenu menu) {
        try {
            // String uploadDir = new
            // File("src/main/resources/static/uploads").getAbsolutePath();
            String uploadDir = "/home/ubuntu/kiosk-system/uploads"; // ✅ 절대 경로로 수정

            File dir = new File(uploadDir);
            if (!dir.exists())
                dir.mkdirs();

            String fileName = imageFile.getOriginalFilename();
            File file = new File(uploadDir, fileName);
            imageFile.transferTo(file);

            menu.setImageFilename(fileName);
        } catch (IOException e) {
            throw new RuntimeException("❌ 이미지 저장 실패", e);
        }
    }
}
