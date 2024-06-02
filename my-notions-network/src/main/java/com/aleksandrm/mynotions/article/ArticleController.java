package com.aleksandrm.mynotions.article;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("articles")
@RequiredArgsConstructor
@Tag(name = "Article")
public class ArticleController {

    private final ArticleService service;

    @PostMapping
    public ResponseEntity<Integer> saveArticle(
            @Valid @RequestBody ArticleRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.save(request, connectedUser));
    }

    @GetMapping("{article-id}")
    public ResponseEntity<ArticleResponse> findArticleById(
            @PathVariable("article-id") Integer articleId
    ) {
        return ResponseEntity.ok(service.findById(articleId));
    }
}
