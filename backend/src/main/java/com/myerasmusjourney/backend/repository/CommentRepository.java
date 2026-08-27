package com.myerasmusjourney.backend.repository;

import com.myerasmusjourney.backend.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
