package com.wsei.controller;


import com.wsei.controller.model.ArticleResponse;
import com.wsei.controller.model.NewRowRequest;
import com.wsei.controller.model.RowResponse;
import com.wsei.controller.model.WarehouseUpdateRequest;
import com.wsei.model.Row;
import com.wsei.model.Warehouse;
import com.wsei.service.RowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RowController {

    private final RowService rowService;

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/rows")
    public List<RowResponse> getRows() {

        return rowService.getRows()
                .stream()
                .map(this::maptoResponse)
//                .map(row -> maptoResponse(article))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/rows/{id}")
    public RowResponse getRow(@PathVariable Long id)
    {
        return maptoResponse(rowService.getRow(id));
    }

    private RowResponse maptoResponse(Row row) {

        Warehouse warehouse = row.getWarehouse();

        return RowResponse.builder()
                .id(row.getId())
                .name(row.getName())
                .capacity(row.getCapacity())
                .warehouseId(Objects.nonNull(warehouse) ? warehouse.getId() : null)
                .build();
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/rows")
    public RowResponse addRow(@RequestBody NewRowRequest request)
    {
        Row row = rowService.saveRow(request);
        return maptoResponse(row);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/rows/{id}")
    public Row updateRow(@RequestBody Row newRow, @PathVariable Long id)
    {
        return rowService.updateRow(newRow, id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/rows/{id}")
    void deleteRow(@PathVariable Long id)
    {
        rowService.deleteRow(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/rows/add-warehouse-to-row")
    public RowResponse updateRowWarehouse(@RequestBody WarehouseUpdateRequest request){
        Row row = rowService.assignWarehouse(request);
        return maptoResponse(row);
    }



}
