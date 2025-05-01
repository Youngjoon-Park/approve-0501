package com.example.kiosk_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kiosk_backend.domain.Article;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
