package com.wsei.service;

import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.Level;
import com.wsei.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LevelService {

    private final LevelRepository repository;


    public List<Level> getLevels()
    {
        return repository.findAll();
    }
    public Level getLevel(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Level saveLevel(Level level)
    {
        repository.findByName(level.getName())
                .ifPresent(existingLevel -> {
                    throw new AlreadyExistException();
                });

        return repository.save(level);
    }

    public Level updateLevel(Level newLevel, Long id)
    {
        return repository.findById(id)
                .map (level -> {
                    level.setRow(newLevel.getRow());
                    level.setRack(newLevel.getRack());
                    level.setName(newLevel.getName());
                    level.setCapacity(newLevel.getCapacity());
                    level.setWarehouse(newLevel.getWarehouse());
                    return repository.save(level);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteLevel(@PathVariable Long id)
    {
        repository.deleteById(id);
    }
}
