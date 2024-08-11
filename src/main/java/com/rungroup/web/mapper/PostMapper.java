package com.rungroup.web.mapper;

import com.rungroup.web.dto.PostDto;
import com.rungroup.web.models.Post;

public class PostMapper {
    public static PostDto mapPostToDto(Post p) {
        return PostDto.builder()
                .id(p.getId())
                .createdOn(p.getCreatedOn())
                .updatedOn(p.getUpdatedOn())
                .createdBy(p.getCreatedBy())
                .content(p.getContent())
                .likedBy(p.getLikedBy())
                .build();
    }

    public static Post mapDtoToPost(PostDto dto) {
        return Post.builder()
                .id(dto.getId())
                .createdOn(dto.getCreatedOn())
                .updatedOn(dto.getUpdatedOn())
                .createdBy(dto.getCreatedBy())
                .content(dto.getContent())
                .likedBy(dto.getLikedBy())
                .build();
    }
}
