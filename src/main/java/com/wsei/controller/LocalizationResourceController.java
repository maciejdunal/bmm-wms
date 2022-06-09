package com.wsei.controller;

import com.wsei.model.Customer;
import com.wsei.model.LocalizationResource;
import com.wsei.service.CustomerService;
import com.wsei.service.LocalizationResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocalizationResourceController {

    private final LocalizationResourceService localizationResourceService;

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/localizationResources")
    public ResponseEntity<List<LocalizationResource>> getLocalizationResources() {
        return ResponseEntity.ok().body(localizationResourceService.getLocalizationResources());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/localizationResources/{id}")
    public LocalizationResource getLocalizationResource(@PathVariable Long id)
    {
        return localizationResourceService.getLocalizationResource(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/localizationResources")
    public LocalizationResource addLocalizationResource(@Valid @RequestBody LocalizationResource localizationResource)
    {
        return localizationResourceService.saveLocalizationResource(localizationResource);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/localizationResources/{id}")
    public LocalizationResource updateLocalizationResource(@RequestBody LocalizationResource newLocalizationResource, @PathVariable Long id)
    {
        return localizationResourceService.updateLocalizationResource(newLocalizationResource, id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/localizationResources/{id}")
    void deleteLocalizationResource(@PathVariable Long id)
    {
        localizationResourceService.deleteLocalizationResource(id);
    }
}
