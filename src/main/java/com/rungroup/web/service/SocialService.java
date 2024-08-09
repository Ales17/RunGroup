package com.rungroup.web.service;

import com.rungroup.web.dto.PostDto;

import java.util.List;

public interface SocialService {
    List<PostDto> findAllPosts();

    boolean isPostLikedBySessionUser(long postId);

    void dislikePost(long postId);

    void likePost(long postId);
}
