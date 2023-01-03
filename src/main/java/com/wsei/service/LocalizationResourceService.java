package com.wsei.service;

import com.wsei.controller.model.ArticleUpdateRequest;
import com.wsei.controller.model.LocalizationUpdateRequest;
import com.wsei.controller.model.NewLocalizationResourceRequest;
import com.wsei.controller.model.WarehouseUpdateRequest;
import com.wsei.exception.NotFoundException;
import com.wsei.model.Article;
import com.wsei.model.Localization;
import com.wsei.model.LocalizationResource;
import com.wsei.model.Warehouse;
import com.wsei.repository.ArticleRepository;
import com.wsei.repository.LocalizationRepository;
import com.wsei.repository.LocalizationResourcesRepository;
import com.wsei.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalizationResourceService {

    private final LocalizationResourcesRepository repository;
    private final ArticleRepository articleRepository;


    public List<LocalizationResource> getLocalizationResources() {
        return repository.findAll();
    }

    public LocalizationResource getLocalizationResource(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public LocalizationResource saveLocalizationResource(NewLocalizationResourceRequest newLocalizationResourceRequest) {
        LocalizationResource localizationResource = new LocalizationResource();
        localizationResource.setQuantity(newLocalizationResourceRequest.getQuantity());
        localizationResource.setWeight(newLocalizationResourceRequest.getWeight());

        return repository.save(localizationResource);
    }

    public LocalizationResource updateLocalizationResource(NewLocalizationResourceRequest newLocalizationResource, Long id) {
        return repository.findById(id)
                .map(localizationResource -> {
                    localizationResource.setQuantity(newLocalizationResource.getQuantity());
                    localizationResource.setWeight(newLocalizationResource.getWeight());
                    return repository.save(localizationResource);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteLocalizationResource(@PathVariable Long id) {
        repository.deleteById(id);
    }

    private final WarehouseRepository warehouseRepository;
    private final LocalizationRepository localizationRepository;

    public LocalizationResource assignWarehouse(WarehouseUpdateRequest request) {
        LocalizationResource localizationResource = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new NotFoundException(null));
        localizationResource.setWarehouse(warehouse);
        return repository.save(localizationResource);
    }

    public LocalizationResource assignLocalization(LocalizationUpdateRequest request) {
        LocalizationResource localizationResource = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Localization localization = localizationRepository.findById(request.getLocalizationId())
                .orElseThrow(() -> new NotFoundException(null));
        localizationResource.setLocalization(localization);
        return repository.save(localizationResource);
    }

    public LocalizationResource assignArticle(ArticleUpdateRequest request) {
        LocalizationResource localizationResource = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Article article = articleRepository.findById(request.getArticleId())
                .orElseThrow(() -> new NotFoundException(null));

        localizationResource.setArticle(article);
        return repository.save(localizationResource);
    }
}
