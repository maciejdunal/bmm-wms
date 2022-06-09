package com.wsei.controller;

import com.wsei.model.Place;
import com.wsei.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/places")
    public ResponseEntity<List<Place>> getPlaces() {
        return ResponseEntity.ok().body(placeService.getPlaces());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/places/{id}")
    public Place getPlace(@PathVariable Long id)
    {
        return placeService.getPlace(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/places")
    public Place addPlace(@Valid @RequestBody Place place)
    {
        return placeService.savePlace(place);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/places/{id}")
    public Place updatePlace(@RequestBody Place newPlace, @PathVariable Long id)
    {
        return placeService.updatePlace(newPlace, id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/places/{id}")
    void deletePlace(@PathVariable Long id)
    {
        placeService.deletePlace(id);
    }
}
