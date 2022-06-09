package com.wsei.service;

import com.wsei.model.Role;
import com.wsei.model.User;

import java.util.List;

public interface UserService {
/*    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);*/
    User getUser(String User);
    List<User> getUsers();
    List<Role> getRoles();
}
