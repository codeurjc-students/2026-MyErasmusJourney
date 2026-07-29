package com.myerasmusjourney.backend.service;

import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.UserDTO;
import com.myerasmusjourney.backend.dto.UserFormDTO;
import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import com.myerasmusjourney.backend.mapper.UserMapper;
import com.myerasmusjourney.backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init(){
        User admin = new User("test", "testUser", "testadmin@email.com", passwordEncoder.encode("password"), List.of("USER", "ADMIN"));
        User user = new User("test", "testUser", "test@email.com", passwordEncoder.encode("password"));
        userRepository.save(admin);
        userRepository.save(user);
    }

    private User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            return userRepository.findByEmail(auth.getName());
        }
        return null;
    }

    private boolean isActionAllowed(User user, long id){
        return user.getRoles().contains("ADMIN") || user.getId().equals(id);
    }

    @Transactional
    public UserSimpleDTO createUser(UserFormDTO newUserDTO){
        if(!newUserDTO.password().equals(newUserDTO.passwordConfirmation())) return null;

        User user = userRepository.findByEmail(newUserDTO.email());
        User newUser = new User(newUserDTO.fullName(), newUserDTO.displayName(), newUserDTO.email(), passwordEncoder.encode(newUserDTO.password()));
        if (user != null) return userMapper.toSimpleDTO(newUser);

        User savedUser = userRepository.save(newUser);
        return userMapper.toSimpleDTO(savedUser);
    }

    public UserSimpleDTO getUserInfo() {
        return userMapper.toSimpleDTO(this.getLoggedUser());
    }

    public UserDTO getUserById(Long id) {
        User user = getLoggedUser();
        if (user == null) return null;
        if (!isActionAllowed(user, id)) return null;
        User savedUser = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));;
        return userMapper.toDTO(savedUser);
    }
}
