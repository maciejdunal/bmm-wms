package com.wsei.service;

import com.wsei.controller.model.NewWarehouseRequest;
import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.Warehouse;
import com.wsei.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository repository;


    public List<Warehouse> getWarehouses()
    {
        return repository.findAll();
    }
    public Warehouse getWarehouse(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Warehouse saveWarehouse(NewWarehouseRequest newWarehouseRequest)
    {
        repository.findByName(newWarehouseRequest.getName())
                .ifPresent(existingWarehouse -> {
                    throw new AlreadyExistException();
                });

        Warehouse warehouse = new Warehouse();

        warehouse.setName(newWarehouseRequest.getName());
        warehouse.setDisplayName(newWarehouseRequest.getDisplayName());
        warehouse.setCapacity(newWarehouseRequest.getCapacity());

        LocalDateTime now = LocalDateTime.now();
        warehouse.setCreationDate(now);
        warehouse.setModificationDate(now);

        return repository.save(warehouse);
    }

    public Warehouse updateWarehouse(@RequestBody Warehouse newWarehouse, @PathVariable Long id)
    {

        return repository.findById(id)
                .map (warehouse -> {
                    warehouse.setName(newWarehouse.getName());
                    warehouse.setDisplayName(newWarehouse.getDisplayName());
                    warehouse.setCapacity(newWarehouse.getCapacity());
                    warehouse.setModificationDate(LocalDateTime.now());
                    return repository.save(warehouse);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteWarehouse(@PathVariable Long id)
    {
        repository.deleteById(id);
    }
}
