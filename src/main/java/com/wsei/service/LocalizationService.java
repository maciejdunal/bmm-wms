package com.wsei.service;

import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.Localization;
import com.wsei.repository.LocalizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalizationService {

    private final LocalizationRepository repository;


    public List<Localization> getLocalizations()
    {
        return repository.findAll();
    }
    public Localization getLocalization(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Localization saveLocalization(Localization localization)
    {
        repository.findByName(localization.getName())
                .ifPresent(existingLocalization -> {
                    throw new AlreadyExistException();
                });

        return repository.save(localization);
    }

    public Localization updateLocalization(Localization newLocalization, Long id)
    {
        return repository.findById(id)
                .map (localization -> {
                    localization.setName(newLocalization.getName());
                    localization.setDisplayName(newLocalization.getDisplayName());
                    localization.setWarehouse(newLocalization.getWarehouse());
                    localization.setCapacity(newLocalization.getCapacity());
                    localization.setPlaceId(newLocalization.getPlaceId());
                    return repository.save(localization);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteLocalization(@PathVariable Long id)
    {
        repository.deleteById(id);
    }
}
