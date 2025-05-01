// 📁 src/main/java/com/example/kiosk_backend/controller/BlogApiController.java
package com.example.kiosk_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kiosk_backend.domain.Article;
import com.example.kiosk_backend.dto.AddArticleRequest;
import com.example.kiosk_backend.dto.ArticleResponse;
import com.example.kiosk_backend.dto.UpdateArticleRequest;
import com.example.kiosk_backend.service.BlogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles") // ✅ 공통 prefix 정리
public class BlogApiController {

        private final BlogService blogService;

        @PostMapping
        public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
                Article savedArticle = blogService.save(request);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
        }

        @GetMapping
        public ResponseEntity<List<ArticleResponse>> findAllArticles() {
                List<ArticleResponse> articles = blogService.findAll()
                                .stream()
                                .map(ArticleResponse::new)
                                .toList();
                return ResponseEntity.ok(articles);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ArticleResponse> findArticle(@PathVariable("id") long id) {
                Article article = blogService.findById(id);
                return ResponseEntity.ok(new ArticleResponse(article));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteArticle(@PathVariable("id") long id) {
                blogService.delete(id);
                return ResponseEntity.ok().build();
        }

        @PutMapping("/{id}")
        public ResponseEntity<Article> updateArticle(@PathVariable("id") long id,
                        @RequestBody UpdateArticleRequest request) {
                Article updatedArticle = blogService.update(id, request);
                return ResponseEntity.ok(updatedArticle);
        }
}
