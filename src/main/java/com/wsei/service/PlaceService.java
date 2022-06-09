package com.wsei.service;

import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.Place;
import com.wsei.repository.PlaceRepository;
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

    public Place savePlace(Place place)
    {
        repository.findByName(place.getName())
                .ifPresent(existingPlace -> {
                    throw new AlreadyExistException();
                });

        return repository.save(place);
    }

    public Place updatePlace(Place newPlace, Long id)
    {
        return repository.findById(id)
                .map (place -> {
                    place.setRow(newPlace.getRow());
                    place.setRack(newPlace.getRack());
                    place.setLevel(newPlace.getLevel());
                    place.setName(newPlace.getName());
                    place.setCapacity(newPlace.getCapacity());
                    place.setWarehouse(newPlace.getWarehouse());
                    return repository.save(place);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deletePlace(@PathVariable Long id)
    {
        repository.deleteById(id);
    }
}
