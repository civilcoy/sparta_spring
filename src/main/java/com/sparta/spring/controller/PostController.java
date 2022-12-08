package com.sparta.spring.controller;

import com.sparta.spring.dto.PostResponseDto;
import com.sparta.spring.dto.ResponseDto;
import com.sparta.spring.entity.Post;
import com.sparta.spring.dto.PostRequestDto;
import com.sparta.spring.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public ResponseDto createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.createPost(requestDto, request);
    }

    @GetMapping("/api/posts")  // 전체 게시글 조회
    public List<Post> getPost() {
        return postService.getPosts();
    }

    @GetMapping("/api/postview")  // 선택 게시물 조회
    public PostResponseDto postView(@RequestParam Long id){
        return postService.postView(id);
    }

    @PutMapping("/api/posts/{id}")  // 업데이트
    public PostResponseDto update(@PathVariable Long id, @RequestBody PostResponseDto requestDto, HttpServletRequest request) {
        return postService.update(id, requestDto, request);
    }

    @DeleteMapping("/api/posts/{id}/{pw}")  //삭제
    public Long deletePost(@PathVariable Long id, @PathVariable String pw, @RequestBody PostRequestDto requestDto) {
        return postService.deletePost(id,pw,requestDto);
    }

    // 구버전 선택 게시물 조회
//    @GetMapping("/api/postview/{id}")  // 선택 게시물 조회
//    public String postView(@PathVariable Long id, @RequestBody PostRequestDto requestDto){
//        return postService.postView(id, requestDto);
//    }

    // 구버전 업데이트
//    @PutMapping("/api/posts/{id}/{pw}")  // 업데이트
//    public Long updatePost(@PathVariable Long id, @PathVariable String pw, @RequestBody PostRequestDto requestDto) {
//        return postService.update(id,pw,requestDto);


}
