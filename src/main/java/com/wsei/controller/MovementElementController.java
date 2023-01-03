package com.wsei.controller;

import com.wsei.controller.model.*;
import com.wsei.model.*;
import com.wsei.service.MovementElementService;
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
public class MovementElementController {

    private final MovementElementService movementElementService;

    private MovementElementResponse mapToResponse(MovementElement movementElement) {

        Warehouse sourceWarehouse = movementElement.getSourceWarehouse();
        Warehouse targetWarehouse = movementElement.getTargetWarehouse();
        Article article= movementElement.getArticle();
        Localization sourceLocalization = movementElement.getSourceLocalization();
        Localization targetLocalization = movementElement.getTargetLocalization();
        return MovementElementResponse.builder()
                .id(movementElement.getId())
/*                .operationId(Objects.nonNull(operation) ? operation.getId() : null)*/
                .operationId(movementElement.getOperationId())
                .operationType(movementElement.getOperationType())
                .articleId(Objects.nonNull(article) ? article.getId() : null)
                .userId(movementElement.getUser().getId())
                .quantity(movementElement.getQuantity())
                .weight(movementElement.getWeight())
                .sourceLocalizationId(Objects.nonNull(sourceLocalization) ? sourceLocalization.getId() : null)
                .targetLocalizationId(Objects.nonNull(targetLocalization) ? targetLocalization.getId() : null)
                .creationDate(movementElement.getCreationDate())
                .sourceWarehouseId(Objects.nonNull(sourceWarehouse) ? sourceWarehouse.getId() : null)
                .targetWarehouseId(Objects.nonNull(targetWarehouse) ? targetWarehouse.getId() : null)

                .build();
    }


    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/movementElements")
    public List<MovementElementResponse> getMovementElements(){
        return movementElementService.getMovementElements()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/movementElements/operationId/{operationId}")
    public List<MovementElementResponse> getMovementElementsByOperationId(@PathVariable Long operationId){
        return movementElementService.getMovementElementsByOperationId(operationId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/movementElements/{id}")
    public MovementElementResponse getMovementElement(@PathVariable Long id)
    {
        return mapToResponse(movementElementService.getMovementElement(id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/movementElements")
    public MovementElementResponse addMovementElement(@Valid @RequestBody MovementElement movementElement){
        return mapToResponse(movementElementService.saveMovementElement(movementElement));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/movementElements/{id}")
    public MovementElementResponse updateMovementElement(@RequestBody MovementElement newMovement, @PathVariable Long id){
        return mapToResponse(movementElementService.updateMovementElement(newMovement, id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/movementElements/{id}")
    void deleteMovementElement(@PathVariable Long id){
        movementElementService.deleteMovementElement(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/movementElements/add-operation-to-movementElement")
    public MovementElementResponse updateMovementElementOperation(@RequestBody OperationUpdateRequest request){
        MovementElement movementElement = movementElementService.assignOperation(request);

        return mapToResponse(movementElement);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/movementElements/add-article-to-movementElement")
    public MovementElementResponse updateMovementElementArticle(@RequestBody ArticleUpdateRequest request){
        MovementElement movementElement = movementElementService.assignArticle(request);

        return mapToResponse(movementElement);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/movementElements/add-warehouse-to-movementElement")
    public MovementElementResponse updateMovementElementWarehouse(@RequestBody WarehouseUpdateRequest request){
        MovementElement movementElement = movementElementService.assignWarehouse(request);

        return mapToResponse(movementElement);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/movementElements/add-targetWarehouse-to-movementElement")
    public MovementElementResponse updateMovementElementTargetWarehouse(@RequestBody WarehouseUpdateRequest request){
        MovementElement movementElement = movementElementService.assignTargetWarehouse(request);

        return mapToResponse(movementElement);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/movementElements/add-localization-to-movementElement")
    public MovementElementResponse updateMovementElementLocalization(@RequestBody LocalizationUpdateRequest request){
        MovementElement movementElement = movementElementService.assignLocalization(request);

        return mapToResponse(movementElement);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/movementElements/add-targetLocalization-to-movementElement")
    public MovementElementResponse updateMovementElementTargetWarehouse(@RequestBody LocalizationUpdateRequest request){
        MovementElement movementElement = movementElementService.assignTargetLocalization(request);

        return mapToResponse(movementElement);
    }

}