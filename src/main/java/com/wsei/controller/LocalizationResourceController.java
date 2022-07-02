package com.wsei.controller;

import com.wsei.controller.model.*;
import com.wsei.model.Article;
import com.wsei.model.Localization;
import com.wsei.model.LocalizationResource;
import com.wsei.model.Warehouse;
import com.wsei.service.LocalizationResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocalizationResourceController {

    private final LocalizationResourceService localizationResourceService;

    private LocalizationResourceResponse mapToResponse(LocalizationResource localizationResource) {

        Warehouse warehouse = localizationResource.getWarehouse();
        Localization localization = localizationResource.getLocalization();
        Article article = localizationResource.getArticle();
        return LocalizationResourceResponse.builder()
                .id(localizationResource.getId())
/*                .articleCode(localizationResource.getArticleCode())*/
                .warehouseId(Objects.nonNull(warehouse) ? warehouse.getId() : null)
                .quantity(localizationResource.getQuantity())
                .articleId(Objects.nonNull(article) ? article.getId() : null)
                .localizationId(Objects.nonNull(localization) ? localization.getId() : null)
                .weight(localizationResource.getWeight())
                .build();
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/localizationResources")
    public List<LocalizationResourceResponse> getLocalizationResources() {
        return localizationResourceService.getLocalizationResources()
                .stream()
                .map(this::mapToResponse)
//                .map(row -> mapToResponse(article))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/localizationResources/{id}")
    public LocalizationResourceResponse getLocalizationResource(@PathVariable Long id)
    {
        return mapToResponse(localizationResourceService.getLocalizationResource(id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/localizationResources")
    public LocalizationResourceResponse addLocalizationResource(@Valid @RequestBody NewLocalizationResourceRequest request)
    {
        LocalizationResource localizationResource = localizationResourceService.saveLocalizationResource(request);
        return mapToResponse(localizationResource);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/localizationResources/{id}")
    public LocalizationResourceResponse updateLocalizationResource(@RequestBody NewLocalizationResourceRequest newLocalizationResource, @PathVariable Long id)
    {
        return mapToResponse(localizationResourceService.updateLocalizationResource(newLocalizationResource, id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/localizationResources/{id}")
    void deleteLocalizationResource(@PathVariable Long id)
    {
        localizationResourceService.deleteLocalizationResource(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/localizationResources/add-warehouse-to-localizationResource")
    public LocalizationResourceResponse updateLocalizationResourceWarehouse(@RequestBody WarehouseUpdateRequest request){
        LocalizationResource localizationResource = localizationResourceService.assignWarehouse(request);

        return mapToResponse(localizationResource);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/localizationResources/add-localization-to-localizationResource")
    public LocalizationResourceResponse updateLocalizationResourceLocalization(@RequestBody LocalizationUpdateRequest request){
        LocalizationResource localizationResource = localizationResourceService.assignLocalization(request);

        return mapToResponse(localizationResource);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/localizationResources/add-article-to-localizationResource")
    public LocalizationResourceResponse updateLocalizationResourceArticle(@RequestBody ArticleUpdateRequest request){
        LocalizationResource localizationResource = localizationResourceService.assignArticle(request);

        return mapToResponse(localizationResource);
    }
}
