package com.wsei.controller;

import com.wsei.model.OperationsType;
import com.wsei.service.OperationsTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OperationsTypeController {

    private final OperationsTypeService operationsTypeService;

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/operationsTypes")
    public ResponseEntity<List<OperationsType>> getOperationsTypes() {
        return ResponseEntity.ok().body(operationsTypeService.getOperationsTypes());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/operationsTypes/{id}")
    public OperationsType getOperationsType(@PathVariable Long id)
    {
        return operationsTypeService.getOperationsType(id);
    }
}
