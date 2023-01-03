package com.wsei.controller;

import com.wsei.controller.model.*;
import com.wsei.model.Level;
import com.wsei.model.Rack;
import com.wsei.model.Row;
import com.wsei.model.Warehouse;
import com.wsei.service.LevelService;
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
public class LevelController {

    private final LevelService levelService;

    private LevelResponse mapToResponse(Level level) {

        Warehouse warehouse = level.getWarehouse();
        Row row = level.getRow();
        Rack rack = level.getRack();

        return LevelResponse.builder()
                .id(level.getId())
                .name(level.getName())
                .capacity(level.getCapacity())
                .rowId(Objects.nonNull(row) ? row.getId() : null)
                .rackId(Objects.nonNull(rack) ? rack.getId() : null)
                .warehouseId(Objects.nonNull(warehouse) ? warehouse.getId() : null)
                .build();
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/levels")
    public List<LevelResponse> getLevels() {
        return levelService.getLevels()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/levels/{id}")
    public LevelResponse getLevel(@PathVariable Long id)
    {
        return mapToResponse(levelService.getLevel(id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/levels")
    public LevelResponse addLevel(@Valid @RequestBody NewLevelRequest request)
    {
        Level level = levelService.saveLevel(request);
        return mapToResponse(level);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/levels/{id}")
    public LevelResponse updateLevel(@RequestBody NewLevelRequest newLevel, @PathVariable Long id)
    {
        return mapToResponse(levelService.updateLevel(newLevel, id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/levels/{id}")
    void deleteLevel(@PathVariable Long id)
    {
        levelService.deleteLevel(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/levels/add-warehouse-to-level")
    public LevelResponse updateLevelWarehouse(@RequestBody WarehouseUpdateRequest request){
        Level level = levelService.assignWarehouse(request);

        return mapToResponse(level);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/levels/add-row-to-level")
    public LevelResponse updateLevelRow(@RequestBody RowUpdateRequest request){
        Level level = levelService.assignRow(request);

        return mapToResponse(level);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/levels/add-rack-to-level")
    public LevelResponse updateLevelRack(@RequestBody RackUpdateRequest request){
        Level level = levelService.assignRack(request);

        return mapToResponse(level);
    }
}
