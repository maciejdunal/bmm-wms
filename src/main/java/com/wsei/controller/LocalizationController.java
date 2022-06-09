package com.wsei.controller;

import com.wsei.model.Localization;
import com.wsei.service.LocalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocalizationController {

    private final LocalizationService localizationService;

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/localizations")
    public ResponseEntity<List<Localization>> getLocalizations() {
        return ResponseEntity.ok().body(localizationService.getLocalizations());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/localizations/{id}")
    public Localization getLocalization(@PathVariable Long id)
    {
        return localizationService.getLocalization(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/localizations")
    public Localization addLocalization(@Valid @RequestBody Localization localization)
    {
        return localizationService.saveLocalization(localization);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/localizations/{id}")
    public Localization updateLocalization(@RequestBody Localization newLocalization, @PathVariable Long id)
    {
        return localizationService.updateLocalization(newLocalization, id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/localizations/{id}")
    void deleteLocalization(@PathVariable Long id)
    {
        localizationService.deleteLocalization(id);
    }
}
