package com.wsei.service;

import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.CustomerContactPerson;
import com.wsei.repository.CustomerContactPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerContactPersonService {
    private final CustomerContactPersonRepository repository;

    public List<CustomerContactPerson> getCustomerContactPersons(){
        return repository.findAll();
    }

    public CustomerContactPerson getCustomerContantPerson(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public CustomerContactPerson saveCustomerContactPerson(CustomerContactPerson customerContactPerson){
        repository.findByName(customerContactPerson.getName())
                .ifPresent(existingCustomerContactPerson -> {
                    throw new AlreadyExistException();
                });

        return repository.save(customerContactPerson);
    }

    public CustomerContactPerson updateCustomerContactPerson(CustomerContactPerson newCustomerContactPerson, Long id)
    {
        return repository.findById(id)
                .map (customerContactPerson -> {
                    customerContactPerson.setName(newCustomerContactPerson.getName());
                    customerContactPerson.setSurname(newCustomerContactPerson.getSurname());
                    customerContactPerson.setEmail(newCustomerContactPerson.getEmail());
                    customerContactPerson.setPhoneNumber(newCustomerContactPerson.getPhoneNumber());
                    return repository.save(customerContactPerson);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteCustomerContactPerson(@PathVariable Long id)
    {
        repository.deleteById(id);
    }
}
