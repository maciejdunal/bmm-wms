package com.wsei.service;

import com.wsei.controller.model.NewRackRequest;
import com.wsei.controller.model.RowUpdateRequest;
import com.wsei.controller.model.WarehouseUpdateRequest;
import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.Rack;
import com.wsei.model.Row;
import com.wsei.model.Warehouse;
import com.wsei.repository.RackRepository;
import com.wsei.repository.RowRepository;
import com.wsei.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RackService {

    private final RackRepository repository;


    public List<Rack> getRacks()
    {
        return repository.findAll();
    }
    public Rack getRack(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Rack saveRack(NewRackRequest newRackRequest)
    {
        repository.findByName(newRackRequest.getName())
                .ifPresent(existingRack -> {
                    throw new AlreadyExistException();
                });

        Rack rack = new Rack();
        rack.setName(newRackRequest.getName());
        rack.setCapacity(newRackRequest.getCapacity());
        return repository.save(rack);
    }

    public Rack updateRack(@RequestBody NewRackRequest newRack, @PathVariable Long id)
    {
        return repository.findById(id)
                .map (rack -> {
                    rack.setName(newRack.getName());
                    rack.setCapacity(newRack.getCapacity());
                    return repository.save(rack);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteRack(@PathVariable Long id)
    {
        repository.deleteById(id);
    }

    private final WarehouseRepository warehouseRepository;
    private final RowRepository rowRepository;

    public Rack assignWarehouse(WarehouseUpdateRequest request) {
        Rack rack = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new NotFoundException(null));
        rack.setWarehouse(warehouse);
        return repository.save(rack);
    }

    public Rack assignRow(RowUpdateRequest request) {
        Rack rack = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Row row = rowRepository.findById(request.getRowId())
                .orElseThrow(() -> new NotFoundException(null));
        rack.setRow(row);
        return repository.save(rack);
    }

}
