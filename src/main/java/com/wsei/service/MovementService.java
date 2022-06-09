package com.wsei.service;

import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.Localization;
import com.wsei.model.Movement;
import com.wsei.repository.LocalizationRepository;
import com.wsei.repository.MovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovementService {

    private final MovementRepository repository;
    private final LocalizationRepository localizationRepository;


    public List<Movement> getMovements()
    {
        return repository.findAll();
    }
    public Movement getMovement(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Movement saveMovement(Movement movement)
    {
        repository.findByDocumentNumber(movement.getDocumentNumber())
                .ifPresent(existingMovement -> {
                    throw new AlreadyExistException();
                });

        return repository.save(movement);
    }

    public Movement updateMovement(Movement newMovement, Long id)
    {
        Optional<Localization> byId = localizationRepository.findById(1L);
        return repository.findById(id)

                .map (movement -> {
                    movement.setOperationsType(newMovement.getOperationsType());
                    movement.setCustomer(newMovement.getCustomer());
                    movement.setDescription(newMovement.getDescription());
                    movement.setUser(newMovement.getUser());
//dopisac dla source i target


                    return saveMovement(movement);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteMovement(@PathVariable Long id)
    {
        repository.deleteById(id);
    }
}
