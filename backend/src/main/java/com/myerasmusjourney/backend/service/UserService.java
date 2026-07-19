package com.myerasmusjourney.backend.service;

import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.UserFormDTO;
import com.myerasmusjourney.backend.mapper.UserMapper;
import com.myerasmusjourney.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public Long createUser(UserFormDTO newUser){
        if(!newUser.password().equals(newUser.passwordConfirmation())) return null;
        User user = userRepository.findByEmail(newUser.email());
        if (user != null) return -1L;
        user = new User(newUser.fullName(), newUser.displayName(), newUser.email(), newUser.password());
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }
}
