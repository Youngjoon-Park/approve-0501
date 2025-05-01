package com.example.kiosk_backend.dto;

import com.example.kiosk_backend.domain.Article;

import lombok.Getter;

@Getter
public class ArticleResponse {
    private final Long id;
    private final String title;
    private final String content;

    public ArticleResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent(); // ✅ 이거 빠졌는지 확인!
    }
}
