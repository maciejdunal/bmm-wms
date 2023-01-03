package com.wsei.service;

import com.wsei.exception.NotFoundException;
import com.wsei.model.Unit;
import com.wsei.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository repository;

    public List<Unit> getUnits() {
        return repository.findAll();
    }

    public Unit getUnit(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

}
