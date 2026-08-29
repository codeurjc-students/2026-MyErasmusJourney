package com.myerasmusjourney.backend.service;

import com.myerasmusjourney.backend.domain.Comment;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.CommentDTO;
import com.myerasmusjourney.backend.dto.CommentFormDTO;
import com.myerasmusjourney.backend.mapper.CommentMapper;
import com.myerasmusjourney.backend.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public CommentDTO postComment(Long experienceId, CommentFormDTO commentFormDTO) {
        Experience experience = experienceService.getExperience(experienceId);
        User user = userService.getLoggedUser();
        Comment comment = new Comment(commentFormDTO.description(), user, experience);
        Comment savedComment = commentRepository.save(comment);
        experienceService.addComment(savedComment, experience);
        userService.addComment(savedComment, user);
        return commentMapper.toDTO(savedComment);
    }

}
