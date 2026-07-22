package com.myerasmusjourney.backend.repository;

import com.myerasmusjourney.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
