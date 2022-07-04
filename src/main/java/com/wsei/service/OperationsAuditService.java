package com.wsei.service;

import com.wsei.exception.NotFoundException;
import com.wsei.model.MovementElement;
import com.wsei.model.OperationsAudit;
import com.wsei.repository.OperationsAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationsAuditService {

    private final OperationsAuditRepository repository;

    public List<OperationsAudit> getOperationsAudit()
    {
        return repository.findAll();
    }
}
