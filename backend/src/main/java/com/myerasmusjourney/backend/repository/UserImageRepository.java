package com.myerasmusjourney.backend.repository;

import com.myerasmusjourney.backend.domain.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
}
