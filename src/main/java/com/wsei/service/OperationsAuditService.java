package com.wsei.service;

import com.wsei.model.OperationsAudit;
import com.wsei.repository.OperationsAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
