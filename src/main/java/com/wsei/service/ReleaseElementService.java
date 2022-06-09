package com.wsei.service;

import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.ReleaseElement;
import com.wsei.repository.ReleaseElementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReleaseElementService {


    private final ReleaseElementRepository repository;


    public List<ReleaseElement> getReleaseElements()
    {
        return repository.findAll();
    }
    public ReleaseElement getReleaseElement(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public ReleaseElement saveReleaseElement(ReleaseElement releaseElement)
    {
        repository.findByArticle(releaseElement.getArticle())
                .ifPresent(existingReleaseElement -> {
                    throw new AlreadyExistException();
                });

        return repository.save(releaseElement);
    }

    public ReleaseElement updateReleaseElement(ReleaseElement newReleaseElement, Long id)
    {
        return repository.findById(id)
                .map (releaseElement -> {
                    releaseElement.setRelease(newReleaseElement.getRelease());
                    releaseElement.setOperationsType(newReleaseElement.getOperationsType());
                    releaseElement.setArticle(newReleaseElement.getArticle());
                    releaseElement.setUserId(newReleaseElement.getUserId());
                    releaseElement.setQuantity(newReleaseElement.getQuantity());
                    releaseElement.setWeight(newReleaseElement.getWeight());
                    releaseElement.setLocalization(newReleaseElement.getLocalization());
                    releaseElement.setWarehouse(newReleaseElement.getWarehouse());

                    return repository.save(releaseElement);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteReleaseElement(@PathVariable Long id)
    {
        repository.deleteById(id);
    }
}
