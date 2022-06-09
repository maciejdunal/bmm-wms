package com.wsei.controller;

import com.wsei.model.Movement;
import com.wsei.service.MovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/movements")
    public ResponseEntity<List<Movement>> getMovements() {
        return ResponseEntity.ok().body(movementService.getMovements());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/movements/{id}")
    public Movement getMovements(@PathVariable Long id)
    {
        return movementService.getMovement(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/movements")
    public Movement addMovement(@Valid @RequestBody Movement movement)
    {
        return movementService.saveMovement(movement);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/movements/{id}")
    public Movement updateMovement(@RequestBody Movement newMovement, @PathVariable Long id)
    {
        return movementService.updateMovement(newMovement, id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/movements/{id}")
    void deleteMovements(@PathVariable Long id)
    {
        movementService.deleteMovement(id);
    }
}
