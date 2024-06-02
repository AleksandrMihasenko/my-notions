package com.aleksandrm.mynotions.article;

import org.springframework.stereotype.Service;

@Service
public class ArticleMapper {

    public Article toArticle(ArticleRequest request) {
        return Article.builder()
                .id(request.id())
                .title(request.title())
                .author(request.author())
                .content(request.content())
                .archived(false)
                .published(request.published())
                .build();
    }
}
