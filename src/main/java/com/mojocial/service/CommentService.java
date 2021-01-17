package com.mojocial.service;

import com.mojocial.model.Post;

public interface CommentService {

	public void saveComment(Post post, String username, String content);

}
