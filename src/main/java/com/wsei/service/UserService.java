package com.wsei.service;

import com.wsei.model.Role;
import com.wsei.model.User;

import java.util.List;

public interface UserService {
    User getUser(String User);

    List<User> getUsers();

    List<Role> getRoles();
}
