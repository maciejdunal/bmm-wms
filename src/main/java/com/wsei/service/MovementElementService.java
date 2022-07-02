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
public class MovementElementService {

    private final MovementElementRepository repository;
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository;
    private final LocalizationRepository localizationRepository;
    private final ArticleRepository articleRepository;
    private final MovementRepository movementRepository;


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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object username = authentication.getPrincipal();
        User currentUser = userRepository.findByUsername((String) username);
        LocalDateTime now = LocalDateTime.now();

        movementElement.setUser(currentUser);
        movementElement.setCreationDate(now);

        return repository.save(movementElement);
    }

    public MovementElement updateMovementElement(MovementElement newMovementElement, Long id)
    {
        return repository.findById(id)
                .map (movementElement -> {
                    movementElement.setQuantity(newMovementElement.getQuantity());
                    movementElement.setWeight(newMovementElement.getWeight());
                    return repository.save(movementElement);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteMovementElement(@PathVariable Long id)
    {
        repository.deleteById(id);
    }

    public MovementElement assignOperation(OperationUpdateRequest request) {
        MovementElement movementElement = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Movement movement = movementRepository.findById(request.getOperationId())
                .orElseThrow(() -> new NotFoundException(null));

        movementElement.setMovement(movement);
        return repository.save(movementElement);
    }

    public MovementElement assignArticle(ArticleUpdateRequest request) {
        MovementElement movementElement = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Article article = articleRepository.findById(request.getArticleId())
                .orElseThrow(() -> new NotFoundException(null));

        movementElement.setArticle(article);
        return repository.save(movementElement);
    }

    public MovementElement assignWarehouse(WarehouseUpdateRequest request) {
        MovementElement movementElement = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new NotFoundException(null));

        movementElement.setSourceWarehouse(warehouse);
        movementElement.setTargetWarehouse(warehouse);
        return repository.save(movementElement);
    }

    public MovementElement assignTargetWarehouse(WarehouseUpdateRequest request) {
        MovementElement movementElement = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new NotFoundException(null));

        movementElement.setTargetWarehouse(warehouse);
        return repository.save(movementElement);
        }

        public MovementElement assignLocalization(LocalizationUpdateRequest request) {
            MovementElement movementElement = repository.findById(request.getResourceId())
                    .orElseThrow(() -> new NotFoundException(null));
            Localization localization = localizationRepository.findById(request.getLocalizationId())
                    .orElseThrow(() -> new NotFoundException(null));

            movementElement.setSourceLocalization(localization);
            movementElement.setTargetLocalization(localization);
            return repository.save(movementElement);
        }

    public MovementElement assignTargetLocalization(LocalizationUpdateRequest request) {
        MovementElement movementElement = repository.findById(request.getResourceId())
                .orElseThrow(() -> new NotFoundException(null));
        Localization localization = localizationRepository.findById(request.getLocalizationId())
                .orElseThrow(() -> new NotFoundException(null));

        movementElement.setTargetLocalization(localization);
        return repository.save(movementElement);
    }
}
