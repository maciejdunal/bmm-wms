package com.wsei.controller;

import com.wsei.model.MovementElement;
import com.wsei.service.MovementElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MovementElementController {
    private final MovementElementService movementElementService;

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/movementElements")
    public ResponseEntity<List<MovementElement>> getMovementElements() {
        return ResponseEntity.ok().body(movementElementService.getMovementElements());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/movementElements/{id}")
    public MovementElement getMovementElement(@PathVariable Long id)
    {
        return movementElementService.getMovementElement(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/movementElements")
    public MovementElement addMovementElement(@Valid @RequestBody MovementElement movementElement)
    {
        return movementElementService.saveMovementElement(movementElement);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/movementElements/{id}")
    public MovementElement updateMovementElement(@RequestBody MovementElement newMovementElement, @PathVariable Long id)
    {
        return movementElementService.updateMovementElement(newMovementElement, id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/movementElements/{id}")
    void deleteMovementElements(@PathVariable Long id)
    {
        movementElementService.deleteMovementElement(id);
    }
}

class MovementElementRequest{}