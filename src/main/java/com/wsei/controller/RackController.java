package com.wsei.controller;

import com.wsei.controller.model.NewRackRequest;
import com.wsei.controller.model.RackResponse;
import com.wsei.controller.model.RowUpdateRequest;
import com.wsei.controller.model.WarehouseUpdateRequest;
import com.wsei.model.Rack;
import com.wsei.model.Row;
import com.wsei.model.Warehouse;
import com.wsei.service.RackService;
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
public class RackController {

    private final RackService rackService;

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/racks")
    public List<RackResponse> getRacks() {
        return rackService.getRacks()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/racks/{id}")
    public RackResponse getRack(@PathVariable Long id)
    {
        return mapToResponse(rackService.getRack(id));
    }

    private RackResponse mapToResponse(Rack rack) {

        Warehouse warehouse = rack.getWarehouse();
        Row row = rack.getRow();

        return RackResponse.builder()
                .id(rack.getId())
                .rowId(Objects.nonNull(row) ? row.getId() : null)
                .name(rack.getName())
                .capacity(rack.getCapacity())
                .warehouseId(Objects.nonNull(warehouse) ? warehouse.getId() : null)
                .build();
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/racks")
    public RackResponse addRack(@Valid @RequestBody NewRackRequest request)
    {
        Rack rack = rackService.saveRack(request);
        return mapToResponse(rack);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/racks/{id}")
    public RackResponse updateRack(@RequestBody NewRackRequest newRack, @PathVariable Long id)
    {
        return mapToResponse(rackService.updateRack(newRack, id));
/*        return rackService.updateRack(newRack, id);*/
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/racks/{id}")
    void deleteRack(@PathVariable Long id)
    {
        rackService.deleteRack(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/rows/add-warehouse-to-rack")
    public RackResponse updateRowWarehouse(@RequestBody WarehouseUpdateRequest request){
        Rack rack = rackService.assignWarehouse(request);

        return mapToResponse(rack);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/rows/add-row-to-rack")
    public RackResponse updateRackRow(@RequestBody RowUpdateRequest request){
        Rack rack = rackService.assignRow(request);

        return mapToResponse(rack);
    }
}
