// 📁 src/main/java/com/example/kiosk_backend/service/BlogService.java
package com.example.kiosk_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.kiosk_backend.domain.Article;
import com.example.kiosk_backend.dto.AddArticleRequest;
import com.example.kiosk_backend.dto.UpdateArticleRequest;
import com.example.kiosk_backend.repository.BlogRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("❌ 해당 ID의 글이 존재하지 않습니다: " + id));
    }

    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = findById(id); // 중복 제거
        article.update(request.getTitle(), request.getContent());
        return article;
    }
}
