package com.springboot.blog.controller;


import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto>createComment(@PathVariable(name="postId")Long postId,
                                                   @Valid @RequestBody CommentDto commentDto)
    {
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto>getCommentByPostId(@PathVariable(name="postId")Long postId){
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto>getCommentById(@PathVariable(name = "postId")Long postId,@PathVariable(name = "id")Long commentId)
    {
        CommentDto commentDto=commentService.getCommentById(postId,commentId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto>updateComment(@PathVariable(name="postId")Long postId,@PathVariable(name = "id")Long commentId,@Valid @RequestBody CommentDto commentDto)
    {
        return new ResponseEntity<>(commentService.updateComment(postId,commentId,commentDto),HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String>DeleteComment(@PathVariable(name="postId")Long postId,@PathVariable(name="id")Long commentId)
    {
        return new ResponseEntity<>(commentService.deleteComment(postId,commentId),HttpStatus.OK);
    }

}
