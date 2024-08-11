package com.rungroup.web.controller;

import com.rungroup.web.dto.PostDto;
import com.rungroup.web.models.Post;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.security.SecurityUtil;
import com.rungroup.web.service.SocialService;
import com.rungroup.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SocialController {

    private final UserService userService;
    private final SocialService socialService;

    public SocialController(SocialService socialService, UserService userService) {
        this.socialService = socialService;
        this.userService = userService;
    }

    private void addUserToModel(Model model) {
        String username = SecurityUtil.getSessionUser();
        if (username == null) return;
        UserEntity user = userService.findByUsername(username);
        model.addAttribute("user", user);
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<PostDto> posts = socialService.findAllPosts();
        model.addAttribute("posts", posts);
        addUserToModel(model);
        return "social";
    }

    @GetMapping("posts")
    public String getPosts(Model model) {
        List<PostDto> posts = socialService.findAllPosts();
        model.addAttribute("posts", posts);
        return "fragments/social::posts";
    }

    @GetMapping("post/new")
    public String newPostForm(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "fragments/social::post-new";
    }

    @PostMapping("post/new")
    public String createPost(@ModelAttribute PostDto dto) {
        socialService.createPost(dto);
        return "redirect:/posts";
    }
    @GetMapping("/post/{postId}/status")
    public String getUserLikeStatus(@PathVariable("postId") long postId) {
        if (socialService.isPostLikedBySessionUser(postId))
            return "fragments/social::like-active";
        return "fragments/social::like-inactive";
    }

    @DeleteMapping("/post/{postId}/dislike")
    public String dislikePost(@PathVariable("postId") long postId) {
        socialService.dislikePost(postId);
        return "fragments/social::like-inactive";
    }


    @PutMapping(value = "/post/{postId}/like")
    public String toggleUserLikeStatus(@PathVariable("postId") long postId) {
        socialService.likePost(postId);
        return "fragments/social::like-active";
    }

}
