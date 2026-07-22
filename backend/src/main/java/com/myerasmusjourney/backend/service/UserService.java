package com.myerasmusjourney.backend.service;

import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.UserDTO;
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
    public UserDTO createUser(UserFormDTO newUserDTO){
        if(!newUserDTO.password().equals(newUserDTO.passwordConfirmation())) return null;

        User user = userRepository.findByEmail(newUserDTO.email());
        User newUser = new User(newUserDTO.fullName(), newUserDTO.displayName(), newUserDTO.email(), newUserDTO.password());
        if (user != null) return userMapper.toDTO(newUser);

        User savedUser = userRepository.save(newUser);
        return userMapper.toDTO(savedUser);
    }
}
