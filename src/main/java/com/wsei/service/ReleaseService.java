package com.wsei.service;

import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.Release;
import com.wsei.repository.ReleaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReleaseService {
    private final ReleaseRepository repository;


    public List<Release> getReleases()
    {
        return repository.findAll();
    }
    public Release getRelease(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Release saveRelease(Release release)
    {
        repository.findByDocumentNumber(release.getDocumentNumber())
                .ifPresent(existingRelease-> {
                    throw new AlreadyExistException();
                });

        return repository.save(release);
    }

    public Release updateRelease(Release newRelease, Long id)
    {
        return repository.findById(id)
                .map (release -> {
                    release.setOperationsType(newRelease.getOperationsType());
                    release.setWarehouse(newRelease.getWarehouse());
                    release.setCustomer(newRelease.getCustomer());
                    release.setDescription(newRelease.getDescription());

                    return repository.save(release);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteRelease(@PathVariable Long id)
    {
        repository.deleteById(id);
    }
}
