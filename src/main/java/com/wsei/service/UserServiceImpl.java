package com.wsei.service;

import com.wsei.controller.model.NewUserRequest;
import com.wsei.controller.model.RoleUpdateRequest;
import com.wsei.exception.NotFoundException;
import com.wsei.model.Role;
import com.wsei.model.User;
import com.wsei.repository.RoleRepository;
import com.wsei.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public User saveUser(NewUserRequest newUserRequest) {
        log.info("Saving new user {} to the database", newUserRequest.getName());
        userRepository.findByUsername(newUserRequest.getUsername());
        User user = new User();

        user.setUsername(newUserRequest.getUsername());
        user.setPassword(passwordEncoder.encode((newUserRequest.getPassword())));
        user.setName(newUserRequest.getName());
        user.setSurname(newUserRequest.getSurname());
        Role defaultRole = roleRepository.getById(3L);
        user.setRole(defaultRole);
        return userRepository.save(user);
    }

    public User updateUser(NewUserRequest newUser, Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(passwordEncoder.encode((newUser.getPassword())));
                    user.setName(newUser.getName());
                    user.setSurname(newUser.getSurname());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    public User assignRole(RoleUpdateRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        Role role = roleRepository.findByName(request.getRoleName());
        user.setRole(role);
        userRepository.save(user);
        return userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public List<Role> getRoles() {
        log.info("Fetching all roles");
        return roleRepository.findAll();
    }
}