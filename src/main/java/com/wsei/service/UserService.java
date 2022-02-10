package com.wsei.service;

import com.wsei.model.User;
import com.wsei.model.UserAlreadyExistException;
import com.wsei.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    public User createUser(User user) {
        Optional<User> optionalUser = userRepository.findByName(user.getName());
        if(optionalUser.isPresent()){
            throw new UserAlreadyExistException();
        }

        return userRepository.save(user);
    }


}
