package com.sparta.spring.service;

import com.sparta.spring.entity.Post;
import com.sparta.spring.entity.PostRequestDto;
import com.sparta.spring.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post createPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        postRepository.save(post);
        return post;
    }

    @Transactional(readOnly = true)
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public String postView(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        String title = requestDto.getTitle();
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String content = requestDto.getContents();
        String result = "title : " + title + ", username : " + username + ", password : " + password + ", content : " + content;

        return result;
    }

    @Transactional
    public Long update(Long id, String pw, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        String password = requestDto.getPassword();
        if(password.equals(pw)){
            post.update(requestDto);
        }else{
            System.out.println("비밀번호가 일치하지 않습니다!");
        }
        return post.getId();
    }

    @Transactional
    public Long deletePost(Long id, String pw, PostRequestDto requestDto) {
        String password = requestDto.getPassword();
        String msg = "삭제 완료";
        if(password.equals(pw)){
            postRepository.deleteById(id);
        }else {
            System.out.println("비밀번호가 일치하지 않습니다!");
        }
        return id;
    }



}
