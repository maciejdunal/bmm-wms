package com.wsei.service;

import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.LocalizationResource;
import com.wsei.repository.LocalizationResourcesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalizationResourceService {

    private final LocalizationResourcesRepository repository;


    public List<LocalizationResource> getLocalizationResources()
    {
        return repository.findAll();
    }
    public LocalizationResource getLocalizationResource(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public LocalizationResource saveLocalizationResource(LocalizationResource localizationResource)
    {
        repository.findByArticle(localizationResource.getArticle())
                .ifPresent(existingLocalizationResource -> {
                    throw new AlreadyExistException();
                });

        return repository.save(localizationResource);
    }

    public LocalizationResource updateLocalizationResource(LocalizationResource newLocalizationResource, Long id)
    {
        return repository.findById(id)
                .map (localizationResource -> {
                    localizationResource.setWarehouse(newLocalizationResource.getWarehouse());
                    localizationResource.setQuantity(newLocalizationResource.getQuantity());
                    localizationResource.setPlace(newLocalizationResource.getPlace());
                    localizationResource.setWeight(newLocalizationResource.getWeight());
                    return saveLocalizationResource(localizationResource);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteLocalizationResource(@PathVariable Long id)
    {
        repository.deleteById(id);
    }
}
