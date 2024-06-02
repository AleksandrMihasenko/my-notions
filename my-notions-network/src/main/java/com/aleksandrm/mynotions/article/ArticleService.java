package com.aleksandrm.mynotions.article;

import com.aleksandrm.mynotions.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleMapper articleMapper;
    private final ArticleRepository articleRepository;

    public Integer save(ArticleRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Article article = articleMapper.toArticle(request);

        article.setOwner(user);
        return articleRepository.save(article).getId();
    }

    public ArticleResponse findById(Integer articleId) {
        return articleRepository.findById(articleId)
                .map(articleMapper::toArticleResponse)
                .orElseThrow(() -> new EntityNotFoundException("No article found with the ID: " + articleId));
    }
}
