package com.wsei.controller;

import com.wsei.model.User;
import com.wsei.model.UserNotFoundException;
import com.wsei.model.UserRepository;
import java.util.List;

import com.wsei.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;
    private final UserService userService;

    @GetMapping("/users")
    public List<User> getUser(){
        return (List<User>) repository.findAll();
    }

    @GetMapping("/users/{id}")
    User getUser(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping("/users")
    public User saveUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setDisplayName(newUser.getDisplayName());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
