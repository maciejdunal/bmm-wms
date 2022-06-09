package com.wsei.controller;

import com.wsei.model.Customer;
import com.wsei.model.Release;
import com.wsei.service.CustomerService;
import com.wsei.service.ReleaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReleaseController {


    private final ReleaseService releaseService;

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/releases")
    public ResponseEntity<List<Release>> getCustomers() {
        return ResponseEntity.ok().body(releaseService.getReleases());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/releases/{id}")
    public Release getRelease(@PathVariable Long id)
    {
        return releaseService.getRelease(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/releases")
    public Release addRelease(@Valid @RequestBody Release release)
    {
        return releaseService.saveRelease(release);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/releases/{id}")
    public Release updateRelease(@RequestBody Release newRelease, @PathVariable Long id)
    {
        return releaseService.updateRelease(newRelease, id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/releases/{id}")
    void deleteRelease(@PathVariable Long id)
    {
        releaseService.deleteRelease(id);
    }
}
