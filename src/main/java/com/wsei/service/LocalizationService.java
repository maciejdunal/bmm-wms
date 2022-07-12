package com.wsei.service;

import com.wsei.controller.model.NewLocalizationRequest;
import com.wsei.controller.model.PlaceUpdateRequest;
import com.wsei.controller.model.RowUpdateRequest;
import com.wsei.controller.model.WarehouseUpdateRequest;
import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.*;
import com.wsei.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalizationService {

    private final LocalizationRepository repository;


    public List<Localization> getLocalizations()
    {
        return repository.findAll();
    }
    public List<Localization> getLocalizationsByWarehouseId(@PathVariable Long warehouseId)
    {
        return repository.findAllByWarehouseId(warehouseId);
    }
    public Localization getLocalization(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Localization saveLocalization(NewLocalizationRequest newLocalizationRequest)
    {
        repository.findByName(newLocalizationRequest.getName())
                .ifPresent(existingLocalization -> {
                    throw new AlreadyExistException();
                });
        Localization localization = new Localization();
        localization.setName(newLocalizationRequest.getName());
        localization.setDisplayName(newLocalizationRequest.getDisplayName());
        localization.setCapacity(newLocalizationRequest.getCapacity());

        return repository.save(localization);
    }

    public Localization updateLocalization(NewLocalizationRequest newLocalization, Long id)
    {
        return repository.findById(id)
                .map (localization -> {
                    localization.setName(newLocalization.getName());
                    localization.setDisplayName(newLocalization.getDisplayName());
                    localization.setCapacity(newLocalization.getCapacity());
                    return repository.save(localization);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteLocalization(@PathVariable Long id)
    {
        repository.deleteById(id);
    }

    private final WarehouseRepository warehouseRepository;
    private final PlaceRepository placeRepository;

    public Localization assignWarehouse(WarehouseUpdateRequest request) {
        Localization localization = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new NotFoundException(null));
        localization.setWarehouseId(request.getWarehouseId());
/*        localization.setWarehouse(warehouse);*/
        return repository.save(localization);
    }

    public Localization assignPlace(PlaceUpdateRequest request) {
        Localization localization = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Place place = placeRepository.findById(request.getPlaceId())
                .orElseThrow(() -> new NotFoundException(null));
        localization.setPlace(place);
        return repository.save(localization);
    }

}
