package com.wsei.service;

import com.wsei.controller.model.*;
import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.exception.ResourceNotFoundException;
import com.wsei.model.*;
import com.wsei.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalizationResourceService {

    private final LocalizationResourcesRepository repository;
    private final ArticleRepository articleRepository;


    public List<LocalizationResource> getLocalizationResources()
    {
        return repository.findAll();
    }
    public LocalizationResource getLocalizationResource(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public LocalizationResource saveLocalizationResource(NewLocalizationResourceRequest newLocalizationResourceRequest)
    {
/*        repository.findByArticleCode(newLocalizationResourceRequest.getArticleCode())
                .ifPresent(existingLocalizationResource -> {
                    throw new AlreadyExistException();
                });*/
/*        articleRepository.findByArticleCode(newLocalizationResourceRequest.getArticleCode())
                .orElseThrow(() -> new ResourceNotFoundException(newLocalizationResourceRequest.getArticleCode()));*/

        LocalizationResource localizationResource = new LocalizationResource();
        localizationResource.setQuantity(newLocalizationResourceRequest.getQuantity());
/*        localizationResource.setArticleCode(newLocalizationResourceRequest.getArticleCode());*/
        localizationResource.setWeight(newLocalizationResourceRequest.getWeight());

        return repository.save(localizationResource);
    }

    public LocalizationResource updateLocalizationResource(NewLocalizationResourceRequest newLocalizationResource, Long id)
    {
/*        repository.findByArticleCode(newLocalizationResource.getArticleCode())
                .ifPresent(existingLocalizationResource -> {
                    throw new AlreadyExistException();
                });*/
        return repository.findById(id)
                .map (localizationResource -> {
                    localizationResource.setQuantity(newLocalizationResource.getQuantity());
/*                    localizationResource.setArticleCode(newLocalizationResource.getArticleCode());*/
                    localizationResource.setWeight(newLocalizationResource.getWeight());
                    return repository.save(localizationResource);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteLocalizationResource(@PathVariable Long id)
    {
        repository.deleteById(id);
    }

    private final WarehouseRepository warehouseRepository;
    private final LocalizationRepository localizationRepository;
/*    private final ArticleRepository articleRepository;*/

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
