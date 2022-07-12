package com.wsei.service;

import com.wsei.controller.model.ArticleUpdateRequest;
import com.wsei.controller.model.LocalizationUpdateRequest;
import com.wsei.controller.model.OperationUpdateRequest;
import com.wsei.controller.model.WarehouseUpdateRequest;
import com.wsei.exception.NotFoundException;
import com.wsei.model.*;
import com.wsei.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReleaseElementService {


    private final ReleaseElementRepository repository;
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository;
    private final LocalizationRepository localizationRepository;
    private final ArticleRepository articleRepository;
    private final ReleaseRepository movementRepository;


    public List<ReleaseElement> getReleaseElements()
    {
        return repository.findAll();
    }

    public List<ReleaseElement> getReleaseElementsByOperationId(@PathVariable Long operationId)
    {
        return repository.findAllByOperationId(operationId);
    }

    public ReleaseElement getReleaseElement(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public ReleaseElement saveReleaseElement(ReleaseElement releaseElement)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object username = authentication.getPrincipal();
        User currentUser = userRepository.findByUsername((String) username);
        LocalDateTime now = LocalDateTime.now();

        releaseElement.setUser(currentUser);
        releaseElement.setCreationDate(now);

        return repository.save(releaseElement);
    }

    public ReleaseElement updateReleaseElement(ReleaseElement newReleaseElement, Long id)
    {
        return repository.findById(id)
                .map (releaseElement -> {
                    releaseElement.setQuantity(newReleaseElement.getQuantity());
                    releaseElement.setWeight(newReleaseElement.getWeight());;
                    return repository.save(releaseElement);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteReleaseElement(@PathVariable Long id)
    {
        repository.deleteById(id);
    }

    public ReleaseElement assignOperation(OperationUpdateRequest request) {
        ReleaseElement releaseElement = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Release release = movementRepository.findById(request.getOperationId())
                .orElseThrow(() -> new NotFoundException(null));

        releaseElement.setOperationId(request.getOperationId());
  /*      releaseElement.setRelease(release);*/
        return repository.save(releaseElement);
    }

    public ReleaseElement assignArticle(ArticleUpdateRequest request) {
        ReleaseElement releaseElement = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Article article = articleRepository.findById(request.getArticleId())
                .orElseThrow(() -> new NotFoundException(null));

        releaseElement.setArticle(article);
        return repository.save(releaseElement);
    }

    public ReleaseElement assignWarehouse(WarehouseUpdateRequest request) {
        ReleaseElement releaseElement = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new NotFoundException(null));

        releaseElement.setWarehouse(warehouse);

        return repository.save(releaseElement);
    }

    public ReleaseElement assignLocalization(LocalizationUpdateRequest request) {
        ReleaseElement releaseElement = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Localization localization = localizationRepository.findById(request.getLocalizationId())
                .orElseThrow(() -> new NotFoundException(null));

        releaseElement.setLocalization(localization);
        return repository.save(releaseElement);
    }
}
