package com.wsei.controller;

import com.wsei.model.ReleaseElement;
import com.wsei.service.ReleaseElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReleaseElementController {


    private final ReleaseElementService releaseElementService;

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/releaseElements")
    public ResponseEntity<List<ReleaseElement>> getReleaseElements() {
        return ResponseEntity.ok().body(releaseElementService.getReleaseElements());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/releaseElements/{id}")
    public ReleaseElement getReleaseElement(@PathVariable Long id)
    {
        return releaseElementService.getReleaseElement(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/releaseElements")
    public ReleaseElement addReleaseElement(@Valid @RequestBody ReleaseElement releaseElement)
    {
        return releaseElementService.saveReleaseElement(releaseElement);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/releaseElements/{id}")
    public ReleaseElement updateReleaseElement(@RequestBody ReleaseElement newReleaseElement, @PathVariable Long id)
    {
        return releaseElementService.updateReleaseElement(newReleaseElement, id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/releaseElements/{id}")
    void deleteReleaseElements(@PathVariable Long id)
    {
        releaseElementService.deleteReleaseElement(id);
    }
}
