package com.wsei.controller;

import com.wsei.controller.model.CustomerUpdateRequest;
import com.wsei.controller.model.ReleaseResponse;
import com.wsei.controller.model.WarehouseUpdateRequest;
import com.wsei.model.Customer;
import com.wsei.model.Release;
import com.wsei.model.Warehouse;
import com.wsei.service.ReleaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReleaseController {


    private final ReleaseService releaseService;

    private ReleaseResponse mapToResponse(Release release) {

        Warehouse warehouse = release.getWarehouse();
        Customer customer = release.getCustomer();

        return ReleaseResponse.builder()
                .id(release.getId())
                .operationType(release.getOperationType())
                .documentNumber(release.getDocumentNumber())
                .creationDate(release.getCreationDate())
                .modificationDate(release.getModificationDate())
                .warehouseId(Objects.nonNull(warehouse) ? warehouse.getId() : null)
                .customerId(Objects.nonNull(customer) ? customer.getId() : null)
                .userId(release.getUser().getId())
                .description(release.getDescription())
                .build();
    }


    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/releases")
    public List<ReleaseResponse> getReleases(){
        return releaseService.getReleases()
                .stream()
                .map(this::mapToResponse)
//                .map(article -> mapToResponse(article))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/releases/{id}")
    public ReleaseResponse getRelease(@PathVariable Long id)
    {
        return mapToResponse(releaseService.getRelease(id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/releases")
    public ReleaseResponse addRelease(@Valid @RequestBody Release release){
        return mapToResponse(releaseService.saveRelease(release));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/releases/{id}")
    public ReleaseResponse updateRelease(@RequestBody Release newRelease, @PathVariable Long id){
        return mapToResponse(releaseService.updateRelease(newRelease, id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/releases/{id}")
    void deleteRelease(@PathVariable Long id){
        releaseService.deleteRelease(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/releases/add-warehouse-to-release")
    public ReleaseResponse updateReleaseWarehouse(@RequestBody WarehouseUpdateRequest request){
        Release release = releaseService.assignWarehouse(request);

        return mapToResponse(release);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/releases/add-customer-to-release")
    public ReleaseResponse updateReleaseCustomer(@RequestBody CustomerUpdateRequest request){
        Release release = releaseService.assignCustomer(request);

        return mapToResponse(release);
    }
}
