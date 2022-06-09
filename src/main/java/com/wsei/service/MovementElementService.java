package com.wsei.service;

import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.MovementElement;
import com.wsei.repository.MovementElementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovementElementService {

    private final MovementElementRepository repository;


    public List<MovementElement> getMovementElements()
    {
        return repository.findAll();
    }
    public MovementElement getMovementElement(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public MovementElement saveMovementElement(MovementElement movementElement)
    {
        repository.findByArticle(movementElement.getArticle())
                .ifPresent(existingMovementElement -> {
                    throw new AlreadyExistException();
                });

        return repository.save(movementElement);
    }

    public MovementElement updateMovementElement(MovementElement newMovementElement, Long id)
    {
        return repository.findById(id)
                .map (movementElement -> {
                    movementElement.setReceipt(newMovementElement.getReceipt());
                    movementElement.setOperationsType(newMovementElement.getOperationsType());
                    movementElement.setArticle(newMovementElement.getArticle());
                    movementElement.setUserId(newMovementElement.getUserId());
                    movementElement.setQuantity(newMovementElement.getQuantity());
                    movementElement.setWeight(newMovementElement.getWeight());
//ddodac dla sourcei  target
                    return saveMovementElement(movementElement);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteMovementElement(@PathVariable Long id)
    {
        repository.deleteById(id);
    }
}
