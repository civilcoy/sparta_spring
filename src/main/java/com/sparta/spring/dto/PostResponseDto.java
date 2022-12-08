package com.sparta.spring.dto;

import com.sparta.spring.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String name;

    private String password;
    private String contents;


    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.name = post.getName();
        this.password = post.getPassword();
        this.contents = post.getContents();
    }
}
