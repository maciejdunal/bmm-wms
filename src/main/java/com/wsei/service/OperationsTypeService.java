package com.wsei.service;

import com.wsei.exception.NotFoundException;
import com.wsei.model.OperationsType;
import com.wsei.repository.OperationsTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationsTypeService {

    private final OperationsTypeRepository repository;


    public List<OperationsType> getOperationsTypes()
    {
        return repository.findAll();
    }
    public OperationsType getOperationsType(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }
}
