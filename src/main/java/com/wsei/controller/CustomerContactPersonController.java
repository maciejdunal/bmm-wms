package com.wsei.controller;

import com.wsei.model.CustomerContactPerson;
import com.wsei.service.CustomerContactPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerContactPersonController {

    private final CustomerContactPersonService customerContactPersonService;

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/customerContactPersons")
    public ResponseEntity<List<CustomerContactPerson>> getCustomerContactPersons() {
        return ResponseEntity.ok().body(customerContactPersonService.getCustomerContactPersons());
    }


    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/customerContactPersons/{id}")
    public CustomerContactPerson getCustomerContactPerson(@PathVariable Long id)
    {
        return customerContactPersonService.getCustomerContantPerson(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/customerContactPersons")
    public CustomerContactPerson addCustomerContactPerson(@Valid @RequestBody CustomerContactPerson customerContactPerson)
    {
        return customerContactPersonService.saveCustomerContactPerson(customerContactPerson);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/customerContactPersons/{id}")
    public CustomerContactPerson updateCustomerContactPerson(@RequestBody CustomerContactPerson newCustomerContactPerson, @PathVariable Long id)
    {
        return customerContactPersonService.updateCustomerContactPerson(newCustomerContactPerson, id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/customerContactPersons/{id}")
    void deleteCustomerContactPerson(@PathVariable Long id)
    {
        customerContactPersonService.deleteCustomerContactPerson(id);
    }
}
