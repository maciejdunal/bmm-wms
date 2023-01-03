package com.wsei.service;


import com.wsei.controller.model.CustomerContactPersonUpdateRequest;
import com.wsei.exception.AlreadyExistException;
import com.wsei.exception.NotFoundException;
import com.wsei.model.Customer;
import com.wsei.model.CustomerContactPerson;
import com.wsei.repository.CustomerContactPersonRepository;
import com.wsei.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerContactPersonRepository customerContactPersonRepository;


    public List<Customer> getCustomers() {
        return repository.findAll();
    }

    public Customer getCustomer(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Customer saveCustomer(Customer customer) {
        repository.findByName(customer.getName())
                .ifPresent(existingCustomer -> {
                    throw new AlreadyExistException();
                });

        return repository.save(customer);
    }

    public Customer updateCustomer(Customer newCustomer, Long id) {
        return repository.findById(id)
                .map(customer -> {
                    customer.setName(newCustomer.getName());
                    customer.setStreet(newCustomer.getStreet());
                    customer.setCity(newCustomer.getCity());
                    customer.setPostalCode(newCustomer.getPostalCode());
                    customer.setCountry(newCustomer.getCountry());
                    customer.setEmail(newCustomer.getEmail());
                    customer.setPhoneNumber(newCustomer.getPhoneNumber());
                    customer.setCustomerContactPerson(newCustomer.getCustomerContactPerson());
                    return repository.save(customer);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteCustomer(@PathVariable Long id) {
        repository.deleteById(id);
    }

    public Customer assignCustomerContantPerson(CustomerContactPersonUpdateRequest request) {
        Customer customer = repository.findById(request.getCustomerId())
                .orElseThrow(() -> new NotFoundException(null));
        CustomerContactPerson customerContactPerson = customerContactPersonRepository.findById(request.getCustomerContactPersonId())
                .orElseThrow(() -> new NotFoundException(null));
        customer.setCustomerContactPerson(customerContactPerson);
        return repository.save(customer);

    }
}
