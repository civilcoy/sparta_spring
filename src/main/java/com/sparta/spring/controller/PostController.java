package com.sparta.spring.controller;

import com.sparta.spring.entity.Post;
import com.sparta.spring.entity.PostRequestDto;
import com.sparta.spring.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @PostMapping("/api/post")  // 게시글 작성
    public Post createPost(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    @GetMapping("/api/posts")  // 전체 게시글 조회
    public List<Post> getPost() {
        return postService.getPosts();
    }

    @GetMapping("/api/postview/{id}")  // 선택 게시물 조회
    public String postView(@PathVariable Long id, @RequestBody PostRequestDto requestDto){
        return postService.postView(id, requestDto);
    }

    @PutMapping("/api/posts/{id}/{pw}")  // 업데이트
    public Long updatePost(@PathVariable Long id, @PathVariable String pw, @RequestBody PostRequestDto requestDto) {
        return postService.update(id,pw,requestDto);
    }

    @DeleteMapping("/api/posts/{id}/{pw}")  //삭제
    public Long deletePost(@PathVariable Long id, @PathVariable String pw, @RequestBody PostRequestDto requestDto) {
        return postService.deletePost(id,pw,requestDto);
    }



}
