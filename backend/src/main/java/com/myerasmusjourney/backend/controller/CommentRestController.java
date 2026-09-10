package com.myerasmusjourney.backend.controller;

import com.myerasmusjourney.backend.dto.CommentDTO;
import com.myerasmusjourney.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentRestController {

    @Autowired
    private CommentService commentService;
    
    @DeleteMapping("/{id}")
    public ResponseEntity<CommentDTO> deleteCommentById(@PathVariable Long id){
        CommentDTO commentDTO = commentService.deleteCommentById(id);
        if (commentDTO == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(commentDTO);
    }
}
