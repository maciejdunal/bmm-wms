package com.wsei.controller;

import com.wsei.controller.model.ArticleResponse;
import com.wsei.controller.model.ArticleUnitUpdateRequest;
import com.wsei.controller.model.NewArticleRequest;
import com.wsei.model.Article;

import com.wsei.model.Unit;
import com.wsei.service.ArticleService;
import com.wsei.service.UnitService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    private ArticleResponse maptoResponse(Article article) {

        Unit unit = article.getUnit();

        return ArticleResponse.builder()
                .id(article.getId())
                .name(article.getName())
                .articleCode(article.getArticleCode())
                .weight(article.getWeight())
                .creationDate(article.getCreationDate())
                .modificationDate(article.getModificationDate())
                .userId(article.getUser().getId())
                .unitName(Objects.nonNull(unit) ? unit.getName() : null)
                .build();
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/articles")
    public List<ArticleResponse> getArticles() {

        return articleService.getArticles()
                .stream()
                .map(this::maptoResponse)
//                .map(article -> maptoResponse(article))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/articles/{id}")
    public ArticleResponse getItem(@PathVariable Long id){
        return maptoResponse(articleService.getArticle(id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/articles")
    public ArticleResponse saveItem(@RequestBody NewArticleRequest request){
        Article article = articleService.saveArticle(request);
        return maptoResponse(article);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/articles/{id}")
    Article replaceItem(@RequestBody Article newArticle, @PathVariable Long id) {
        return articleService.updateArticle(newArticle, id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/articles/{id}")
    void deleteItem(@PathVariable Long id) {
        articleService.deleteArticle(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/articles/add-unit-to-article")
    public ArticleResponse updateArticleUnit(@RequestBody ArticleUnitUpdateRequest request){

        Article article = articleService.assignUnit(request);

        return maptoResponse(article);
    }

}
