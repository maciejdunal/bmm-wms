package com.wsei.controller;

import com.wsei.controller.model.CustomerContactPersonUpdateRequest;
import com.wsei.model.Customer;
import com.wsei.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok().body(customerService.getCustomers());
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable Long id)
    {
        return customerService.getCustomer(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PostMapping("/customers")
    public Customer addCustomer(@Valid @RequestBody Customer customer)
    {
        return customerService.saveCustomer(customer);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@RequestBody Customer newCustomer, @PathVariable Long id)
    {
        return customerService.updateCustomer(newCustomer, id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @DeleteMapping("/customers/{id}")
    void deleteCustomer(@PathVariable Long id)
    {
        customerService.deleteCustomer(id);
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @PutMapping("/customers/add-customer-contact-person-to-customer")
    public Customer updateCustomerContactPerson(@RequestBody CustomerContactPersonUpdateRequest request){
        Customer customer = customerService.assignCustomerContantPerson(request);
        return  customerService.getCustomer(customer.getId());
    }

}
