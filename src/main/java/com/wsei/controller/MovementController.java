package com.wsei.controller;

import com.wsei.controller.model.CustomerUpdateRequest;
import com.wsei.controller.model.MovementResponse;
import com.wsei.controller.model.WarehouseUpdateRequest;
import com.wsei.model.Customer;
import com.wsei.model.Movement;
import com.wsei.model.Warehouse;
import com.wsei.service.MovementService;
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
public class MovementController {
    private final MovementService movementService;

    private MovementResponse mapToResponse(Movement movement) {

        Warehouse sourceWarehouse = movement.getSourceWarehouse();
        Warehouse targetWarehouse = movement.getTargetWarehouse();
        Customer customer = movement.getCustomer();

        return MovementResponse.builder()
                .id(movement.getId())
                .operationType(movement.getOperationType())
                .documentNumber(movement.getDocumentNumber())
                .creationDate(movement.getCreationDate())
                .modificationDate(movement.getModificationDate())
                .sourceWarehouseId(Objects.nonNull(sourceWarehouse) ? sourceWarehouse.getId() : null)
                .targetWarehouseId(Objects.nonNull(targetWarehouse) ? targetWarehouse.getId() : null)
                .customerId(Objects.nonNull(customer) ? customer.getId() : null)
                .userId(movement.getUser().getId())
                .description(movement.getDescription())
                .build();
    }


    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/movements")
    public List<MovementResponse> getMovements(){
        return movementService.getMovements()
                .stream()
                .map(this::mapToResponse)
//                .map(article -> mapToResponse(article))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/movements/{id}")
    public MovementResponse getMovement(@PathVariable Long id)
    {
        return mapToResponse(movementService.getMovement(id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/movements")
    public MovementResponse addMovement(@Valid @RequestBody Movement movement){
        return mapToResponse(movementService.saveMovement(movement));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/movements/{id}")
    public MovementResponse updateMovement(@RequestBody Movement newMovement, @PathVariable Long id){
        return mapToResponse(movementService.updateMovement(newMovement, id));
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/movements/{id}")
    void deleteMovement(@PathVariable Long id){
        movementService.deleteMovement(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/movements/add-warehouse-to-movement")
    public MovementResponse updateMovementWarehouse(@RequestBody WarehouseUpdateRequest request){
        Movement movement = movementService.assignWarehouse(request);

        return mapToResponse(movement);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/movements/add-targetWarehouse-to-movement")
    public MovementResponse updateMovementTargetWarehouse(@RequestBody WarehouseUpdateRequest request){
        Movement movement = movementService.assignTargetWarehouse(request);

        return mapToResponse(movement);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/movements/add-customer-to-movement")
    public MovementResponse updateMovementCustomer(@RequestBody CustomerUpdateRequest request){
        Movement movement = movementService.assignCustomer(request);

        return mapToResponse(movement);
    }


}
