package com.wsei.service;


import com.wsei.controller.model.NewRowRequest;
import com.wsei.controller.model.WarehouseUpdateRequest;
import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.Article;
import com.wsei.model.Row;
import com.wsei.model.Warehouse;
import com.wsei.repository.RowRepository;
import com.wsei.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RowService {

    private final RowRepository repository;


    public List<Row> getRows()
    {
        return repository.findAll();
    }
    public Row getRow(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Row saveRow(NewRowRequest newRowRequest)
    {
/*        repository.findByName(newRowRequest.getName())
                .ifPresent(existingRow -> {
                    throw new AlreadyExistException();
                });*/

        Row row = new Row();
        row.setName(newRowRequest.getName());
        row.setCapacity(newRowRequest.getCapacity());
        return repository.save(row);
    }

    public Row updateRow(@RequestBody NewRowRequest newRow, @PathVariable Long id)
    {
        return repository.findById(id)
                .map (row -> {
                    row.setName(newRow.getName());
                    row.setCapacity(newRow.getCapacity());
                    return repository.save(row);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteRow(@PathVariable Long id)
    {
        repository.deleteById(id);
    }

    private final WarehouseRepository warehouseRepository;

    public Row assignWarehouse(WarehouseUpdateRequest request) {
        Row row = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new NotFoundException(null));
        row.setWarehouse(warehouse);
        return repository.save(row);
    }
}
