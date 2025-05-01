package com.example.kiosk_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kiosk_backend.entity.AdminMenu;

public interface AdminMenuRepository extends JpaRepository<AdminMenu, Long> {

    // ✅ 이름에 특정 문자열이 포함된 메뉴 검색 + 최신순
    List<AdminMenu> findByNameContainingOrderByIdDesc(String keyword); // 🔥 이거 추가

    // ✅ 전체 목록 최신순 정렬
    List<AdminMenu> findAllByOrderByIdDesc(); // 🔥 이거는 이미 있음 (잘했음)

    // ✅ 이거 추가!!!
    List<AdminMenu> findByNameContaining(String keyword);

    // ✅ 이름으로 첫 번째 메뉴 찾기 (Optional)
    // Optional<AdminMenu> findFirstByNameContaining(String keyword);
    Optional<AdminMenu> findTopByNameContaining(String keyword);

}
