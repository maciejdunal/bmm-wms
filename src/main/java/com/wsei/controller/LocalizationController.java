package com.wsei.controller;

import com.wsei.controller.model.*;
import com.wsei.model.*;
import com.wsei.service.LocalizationService;
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
public class LocalizationController {

    private final LocalizationService localizationService;

    private LocalizationResponse mapToResponse(Localization localization) {

        Warehouse warehouse = localization.getWarehouse();
        Place place = localization.getPlace();

        return LocalizationResponse.builder()
                .id(localization.getId())
                .name(localization.getName())
                .displayName(localization.getDisplayName())
                .capacity(localization.getCapacity())
                .placeId(Objects.nonNull(place) ? place.getId() : null)
                .warehouseId(Objects.nonNull(warehouse) ? warehouse.getId() : null)
                .build();
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/localizations")
    public List<LocalizationResponse> getLocalizations() {
        return localizationService.getLocalizations()
                .stream()
                .map(this::mapToResponse)
//                .map(row -> mapToResponse(article))
                .collect(Collectors.toList());

    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/localizations/{id}")
    public LocalizationResponse getLocalization(@PathVariable Long id)
    {
        return mapToResponse(localizationService.getLocalization(id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/localizations")
    public LocalizationResponse addLocalization(@Valid @RequestBody NewLocalizationRequest request)
    {
        Localization localization = localizationService.saveLocalization(request);
        return mapToResponse(localization);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/localizations/{id}")
    public LocalizationResponse updateLocalization(@RequestBody NewLocalizationRequest newLocalization, @PathVariable Long id)
    {
        return mapToResponse(localizationService.updateLocalization(newLocalization, id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/localizations/{id}")
    void deleteLocalization(@PathVariable Long id)
    {
        localizationService.deleteLocalization(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/localizations/add-warehouse-to-localization")
    public LocalizationResponse updateLocalizationWarehouse(@RequestBody WarehouseUpdateRequest request){
        Localization localization = localizationService.assignWarehouse(request);

        return mapToResponse(localization);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/localizations/add-place-to-localization")
    public LocalizationResponse updateLevelRow(@RequestBody PlaceUpdateRequest request){
        Localization localization = localizationService.assignPlace(request);

        return mapToResponse(localization);
    }
}
