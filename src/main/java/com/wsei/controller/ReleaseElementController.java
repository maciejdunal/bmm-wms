package com.wsei.controller;

import com.wsei.controller.model.*;
import com.wsei.model.*;
import com.wsei.service.ReleaseElementService;
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
public class ReleaseElementController {


    private final ReleaseElementService releaseElementService;

    private ReleaseElementResponse mapToResponse(ReleaseElement releaseElement) {

        Release operation = releaseElement.getRelease();
        Warehouse warehouse = releaseElement.getWarehouse();
        Article article= releaseElement.getArticle();
        Localization localization = releaseElement.getLocalization();
        return ReleaseElementResponse.builder()
                .id(releaseElement.getId())
                .operationId(Objects.nonNull(operation) ? operation.getId() : null)
                .operationType(releaseElement.getOperationType())
                .articleId(Objects.nonNull(article) ? article.getId() : null)
                .userId(releaseElement.getUser().getId())
                .quantity(releaseElement.getQuantity())
                .weight(releaseElement.getWeight())
                .localizationId(Objects.nonNull(localization) ? localization.getId() : null)
                .creationDate(releaseElement.getCreationDate())
                .warehouseId(Objects.nonNull(warehouse) ? warehouse.getId() : null)

                .build();
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/releaseElements")
    public List<ReleaseElementResponse> getReleaseElements() {
        return releaseElementService.getReleaseElements()
                .stream()
                .map(this::mapToResponse)
//                .map(article -> mapToResponse(article))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/releaseElements/{id}")
    public ReleaseElementResponse getReleaseElement(@PathVariable Long id)
    {
        return mapToResponse(releaseElementService.getReleaseElement(id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/releaseElements")
    public ReleaseElementResponse addReleaseElement(@Valid @RequestBody ReleaseElement releaseElement)
    {
        return mapToResponse(releaseElementService.saveReleaseElement(releaseElement));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/releaseElements/{id}")
    public ReleaseElementResponse updateReleaseElement(@RequestBody ReleaseElement newReleaseElement, @PathVariable Long id)
    {
        return mapToResponse(releaseElementService.updateReleaseElement(newReleaseElement, id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/releaseElements/{id}")
    void deleteReleaseElements(@PathVariable Long id)
    {
        releaseElementService.deleteReleaseElement(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/releaseElements/add-operation-to-releaseElement")
    public ReleaseElementResponse updateReleaseElementOperation(@RequestBody OperationUpdateRequest request){
        ReleaseElement releaseElement = releaseElementService.assignOperation(request);

        return mapToResponse(releaseElement);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/releaseElements/add-article-to-releaseElement")
    public ReleaseElementResponse updateReleaseElementArticle(@RequestBody ArticleUpdateRequest request){
        ReleaseElement releaseElement = releaseElementService.assignArticle(request);

        return mapToResponse(releaseElement);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/releaseElements/add-warehouse-to-releaseElement")
    public ReleaseElementResponse updateReleaseElementWarehouse(@RequestBody WarehouseUpdateRequest request){
        ReleaseElement releaseElement = releaseElementService.assignWarehouse(request);

        return mapToResponse(releaseElement);
    }
    
    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/releaseElements/add-localization-to-releaseElement")
    public ReleaseElementResponse updateReleaseElementLocalization(@RequestBody LocalizationUpdateRequest request){
        ReleaseElement releaseElement = releaseElementService.assignLocalization(request);

        return mapToResponse(releaseElement);
    }
}
