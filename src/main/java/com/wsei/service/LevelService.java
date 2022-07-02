package com.wsei.service;

import com.wsei.controller.model.NewLevelRequest;
import com.wsei.controller.model.RackUpdateRequest;
import com.wsei.controller.model.RowUpdateRequest;
import com.wsei.controller.model.WarehouseUpdateRequest;
import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.Level;
import com.wsei.model.Rack;
import com.wsei.model.Row;
import com.wsei.model.Warehouse;
import com.wsei.repository.LevelRepository;
import com.wsei.repository.RackRepository;
import com.wsei.repository.RowRepository;
import com.wsei.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LevelService {

    private final LevelRepository repository;


    public List<Level> getLevels()
    {
        return repository.findAll();
    }
    public Level getLevel(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Level saveLevel(NewLevelRequest newLevelRequest)
    {
        repository.findByName(newLevelRequest.getName())
                .ifPresent(existingLevel -> {
                    throw new AlreadyExistException();
                });
        Level level = new Level();
        level.setName(newLevelRequest.getName());
        level.setCapacity(newLevelRequest.getCapacity());

        return repository.save(level);
    }

    public Level updateLevel(NewLevelRequest newLevel, Long id)
    {
        return repository.findById(id)
                .map (level -> {
                    level.setName(newLevel.getName());
                    level.setCapacity(newLevel.getCapacity());
                    return repository.save(level);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteLevel(@PathVariable Long id)
    {
        repository.deleteById(id);
    }

    private final WarehouseRepository warehouseRepository;
    private final RowRepository rowRepository;
    private final RackRepository rackRepository;

    public Level assignWarehouse(WarehouseUpdateRequest request) {
        Level level = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new NotFoundException(null));
        level.setWarehouse(warehouse);
        return repository.save(level);
    }

    public Level assignRow(RowUpdateRequest request) {
        Level level = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Row row = rowRepository.findById(request.getRowId())
                .orElseThrow(() -> new NotFoundException(null));
        level.setRow(row);
        return repository.save(level);
    }

    public Level assignRack(RackUpdateRequest request) {
        Level level = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Rack rack = rackRepository.findById(request.getRackId())
                .orElseThrow(() -> new NotFoundException(null));
        level.setRack(rack);
        return repository.save(level);
    }
}
