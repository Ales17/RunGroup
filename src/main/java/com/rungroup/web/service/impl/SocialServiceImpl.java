package com.rungroup.web.service.impl;

import com.rungroup.web.dto.PostDto;
import com.rungroup.web.mapper.PostMapper;
import com.rungroup.web.models.Post;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.repository.PostRepository;
import com.rungroup.web.repository.UserRepository;
import com.rungroup.web.service.SocialService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.rungroup.web.mapper.PostMapper.mapDtoToPost;
import static com.rungroup.web.security.SecurityUtil.getSessionUser;

@Service
public class SocialServiceImpl implements SocialService {


    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public SocialServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createPost(PostDto postDto) {
        String username = getSessionUser();
        UserEntity sessionUser = userRepository.findFirstByUsername(username);
        Post post = mapDtoToPost(postDto);
        post.setCreatedBy(sessionUser);
        postRepository.save(post);
    }

    @Override
    public List<PostDto> findAllPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdOn")).stream().map(PostMapper::mapPostToDto).toList();
    }

    @Override
    public boolean isPostLikedBySessionUser(long postId) {
        String username = getSessionUser();
        UserEntity sessionUser = userRepository.findFirstByUsername(username);
        Post post = postRepository.findById(postId).get();
        return post.getLikedBy().contains(sessionUser);
    }

    @Override
    public void dislikePost(long postId) {
        String username = getSessionUser();
        UserEntity sessionUser = userRepository.findFirstByUsername(username);
        Post post = postRepository.findById(postId).get();
        post.getLikedBy().remove(sessionUser);
        postRepository.save(post);
    }

    @Override
    public void likePost(long postId) {
        String username = getSessionUser();
        UserEntity sessionUser = userRepository.findFirstByUsername(username);
        Post post = postRepository.findById(postId).get();
        Set<UserEntity> currentLikers = post.getLikedBy();
        currentLikers.add(sessionUser);
        post.setLikedBy(currentLikers);
        postRepository.save(post);
    }
}
