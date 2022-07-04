package com.wsei.service;

import com.wsei.controller.model.*;
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
public class PlaceService {

    private final PlaceRepository repository;


    public List<Place> getPlaces()
    {
        return repository.findAll();
    }
    public Place getPlace(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Place savePlace(NewPlaceRequest newPlaceRequest)
    {
/*        repository.findByName(newPlaceRequest.getName())
                .ifPresent(existingPlace -> {
                    throw new AlreadyExistException();
                });*/
        Place place = new Place();
        place.setName(newPlaceRequest.getName());
        place.setCapacity(newPlaceRequest.getCapacity());

        return repository.save(place);
    }

    public Place updatePlace(NewPlaceRequest newPlace, Long id)
    {
        return repository.findById(id)
                .map (place -> {
                    place.setName(newPlace.getName());
                    place.setCapacity(newPlace.getCapacity());
                    return repository.save(place);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deletePlace(@PathVariable Long id)
    {
        repository.deleteById(id);
    }

    private final WarehouseRepository warehouseRepository;
    private final RowRepository rowRepository;
    private final RackRepository rackRepository;
    private final LevelRepository levelRepository;

    public Place assignWarehouse(WarehouseUpdateRequest request) {
        Place place = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new NotFoundException(null));
        place.setWarehouse(warehouse);
        return repository.save(place);
    }

    public Place assignRow(RowUpdateRequest request) {
        Place place = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Row row = rowRepository.findById(request.getRowId())
                .orElseThrow(() -> new NotFoundException(null));
        place.setRow(row);
        return repository.save(place);
    }

    public Place assignRack(RackUpdateRequest request) {
        Place place = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Rack rack = rackRepository.findById(request.getRackId())
                .orElseThrow(() -> new NotFoundException(null));
        place.setRack(rack);
        return repository.save(place);
    }

    public Place assignLevel(LevelUpdateRequest request) {
        Place place = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Level level = levelRepository.findById(request.getLevelId())
                .orElseThrow(() -> new NotFoundException(null));
        place.setLevel(level);
        return repository.save(place);
    }
}
