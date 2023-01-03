package com.wsei.controller;

import com.wsei.controller.model.*;
import com.wsei.model.*;
import com.wsei.service.PlaceService;
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
public class PlaceController {

    private final PlaceService placeService;

    private PlaceResponse mapToResponse(Place place) {

        Warehouse warehouse = place.getWarehouse();
        Row row = place.getRow();
        Rack rack = place.getRack();
        Level level = place.getLevel();

        return PlaceResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .capacity(place.getCapacity())
                .rowId(Objects.nonNull(row) ? row.getId() : null)
                .rackId(Objects.nonNull(rack) ? rack.getId() : null)
                .levelId(Objects.nonNull(level) ? level.getId() : null)
                .warehouseId(Objects.nonNull(warehouse) ? warehouse.getId() : null)
                .build();
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/places")
    public List<PlaceResponse> getPlaces() {
        return placeService.getPlaces()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/places/{id}")
    public PlaceResponse getPlace(@PathVariable Long id)
    {
        return mapToResponse(placeService.getPlace(id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/places")
    public PlaceResponse addPlace(@Valid @RequestBody NewPlaceRequest request)
    {
        Place place = placeService.savePlace(request);
        return mapToResponse(place);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/places/{id}")
    public PlaceResponse updatePlace(@RequestBody NewPlaceRequest newPlace, @PathVariable Long id)
    {
        return mapToResponse(placeService.updatePlace(newPlace, id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/places/{id}")
    void deletePlace(@PathVariable Long id)
    {
        placeService.deletePlace(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/places/add-warehouse-to-place")
    public PlaceResponse updatePlaceWarehouse(@RequestBody WarehouseUpdateRequest request){
        Place place = placeService.assignWarehouse(request);

        return mapToResponse(place);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/places/add-row-to-place")
    public PlaceResponse updatePlaceRow(@RequestBody RowUpdateRequest request){
        Place place = placeService.assignRow(request);

        return mapToResponse(place);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/places/add-rack-to-place")
    public PlaceResponse updatePlaceRack(@RequestBody RackUpdateRequest request){
        Place place = placeService.assignRack(request);

        return mapToResponse(place);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/places/add-level-to-place")
    public PlaceResponse updatePlaceLevel(@RequestBody LevelUpdateRequest request){
        Place place = placeService.assignLevel(request);

        return mapToResponse(place);
    }
}
