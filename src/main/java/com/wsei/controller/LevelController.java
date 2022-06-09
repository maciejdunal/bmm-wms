package com.wsei.controller;

import com.wsei.model.Level;
import com.wsei.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LevelController {

    private final LevelService levelService;

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/levels")
    public ResponseEntity<List<Level>> getLevels() {
        return ResponseEntity.ok().body(levelService.getLevels());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/levels/{id}")
    public Level getLevel(@PathVariable Long id)
    {
        return levelService.getLevel(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/levels")
    public Level addLevel(@Valid @RequestBody Level level)
    {
        return levelService.saveLevel(level);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/levels/{id}")
    public Level updateLevel(@RequestBody Level newLevel, @PathVariable Long id)
    {
        return levelService.updateLevel(newLevel, id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/levels/{id}")
    void deleteLevel(@PathVariable Long id)
    {
        levelService.deleteLevel(id);
    }
}
