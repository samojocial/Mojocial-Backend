package com.mojocial.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mojocial.model.Comment;


public interface CommentRepo extends JpaRepository<Comment, Long> {

}
