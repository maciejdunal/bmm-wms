package com.wsei.service;

import com.wsei.controller.model.ArticleUnitUpdateRequest;
import com.wsei.controller.model.NewArticleRequest;
import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.Article;
import com.wsei.model.Unit;
import com.wsei.model.User;
import com.wsei.repository.ArticleRepository;
import com.wsei.repository.UnitRepository;
import com.wsei.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository repository;
    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    
    public List<Article> getArticles()
    {
        return repository.findAll();
    }
    
    public Article getArticle(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }
    
    public Article saveArticle(NewArticleRequest newArticleRequest){
        repository.findByName(newArticleRequest.getArticleCode())
                .ifPresent(existingArticle -> {
                    throw new AlreadyExistException();
                });

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object username = authentication.getPrincipal();
        User currentUser = userRepository.findByUsername((String) username);


        Article article = new Article();
        article.setArticleCode(newArticleRequest.getArticleCode());
        article.setName(newArticleRequest.getName());
        article.setWeight(newArticleRequest.getWeight());

        article.setUser(currentUser);
        LocalDateTime now = LocalDateTime.now();
        article.setCreationDate(now);
        article.setModificationDate(now);

        return repository.save(article);
    }
    
    public Article updateArticle(@RequestBody NewArticleRequest newArticle, @PathVariable Long id) {
        return repository.findById(id)
                .map(article -> {
                    article.setName(newArticle.getName());
                    article.setWeight(newArticle.getWeight());
                    article.setArticleCode(newArticle.getArticleCode());
                    article.setModificationDate(LocalDateTime.now());
                    return repository.save(article);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }
    
    public void deleteArticle(@PathVariable Long id) {
        repository.deleteById(id);
    }

    public Article assignUnit(ArticleUnitUpdateRequest request) {
        Article article = repository.findByArticleCode(request.getArticleCode())
                .orElseThrow(() -> new NotFoundException(null));
        Unit unit = unitRepository.findByName(request.getUnitName())
                .orElseThrow(() -> new NotFoundException(null));
        article.setModificationDate(LocalDateTime.now());
        article.setUnit(unit);
        return repository.save(article);

    }
}
