package com.rungroup.web.controller;

import com.rungroup.web.dto.PostDto;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.security.SecurityUtil;
import com.rungroup.web.service.SocialService;
import com.rungroup.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

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
