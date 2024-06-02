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

    public ArticleResponse toArticleResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .author(article.getAuthor())
                .isbn(article.getIsbn())
                .description(article.getContent())
                .rate(article.getRate())
                .archived(article.isArchived())
                .published(article.isPublished())
                // TODO implement uploading images
                // .cover()
                .build();
    }
}
