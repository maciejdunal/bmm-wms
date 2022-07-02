package com.wsei.controller;

import com.wsei.controller.model.ArticleResponse;
import com.wsei.controller.model.NewWarehouseRequest;
import com.wsei.controller.model.WarehouseResponse;
import com.wsei.model.Article;
import com.wsei.model.Unit;
import com.wsei.model.Warehouse;
import com.wsei.service.WarehouseService;
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
public class WarehouseController {

    private final WarehouseService warehouseService;

    private WarehouseResponse mapToResponse(Warehouse warehouse) {

        return WarehouseResponse.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .displayName(warehouse.getDisplayName())
                .capacity(warehouse.getCapacity())
                .creationDate(warehouse.getCreationDate())
                .modificationDate(warehouse.getModificationDate())
                .build();
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/warehouses")
    public List<WarehouseResponse> getWarehouses() {

        return warehouseService.getWarehouses()
                .stream()
                .map(this::mapToResponse)
//                .map(warehouse -> mapToResponse(article))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/warehouses/{id}")
    public WarehouseResponse getWarehouse(@PathVariable Long id)
    {
        return mapToResponse(warehouseService.getWarehouse(id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/warehouses")
    public WarehouseResponse addWarehouse(@RequestBody NewWarehouseRequest request)
    {
        Warehouse warehouse = warehouseService.saveWarehouse(request);
        return mapToResponse(warehouse);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/warehouses/{id}")
    public WarehouseResponse updateWarehouse(@RequestBody NewWarehouseRequest newWarehouse, @PathVariable Long id)
    {
        return mapToResponse(warehouseService.updateWarehouse(newWarehouse, id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/warehouses/{id}")
    void deleteWarehouse(@PathVariable Long id)
    {
        warehouseService.deleteWarehouse(id);
    }
}
