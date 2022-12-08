package com.sparta.spring.service;

import com.sparta.spring.config.ServiceConfig;
import com.sparta.spring.dto.PostResponseDto;
import com.sparta.spring.dto.ResponseDto;
import com.sparta.spring.entity.Post;
import com.sparta.spring.dto.PostRequestDto;
import com.sparta.spring.entity.User;
import com.sparta.spring.jwt.JwtUtil;
import com.sparta.spring.repository.PostRepository;
import com.sparta.spring.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ServiceConfig serviceConfig;

    @Transactional
    public ResponseDto createPost(PostRequestDto requestDto, HttpServletRequest request) {
        //Post post = new Post(requestDto);
        String token = jwtUtil.resolveToken(request);
        Claims claims;
//        postRepository.save(post);

        // 토큰이 있는 경우에만 작성 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Post post = postRepository.saveAndFlush(new Post(requestDto, user.getUsername()));
            postRepository.save(post);
            return new ResponseDto("글이 작성되었습니다.", HttpStatus.OK.value());
        } else {
            return new ResponseDto("로그인하지 않은 사용자입니다.", HttpStatus.OK.value());
        }
        //return post;
    }

    @Transactional(readOnly = true)
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional(readOnly = true)
    public PostResponseDto postView(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto update(Long id, PostResponseDto requestDto, HttpServletRequest request) {
        User userFromToken = serviceConfig.findUserFromToken(request);
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("작성된 게시물이 없습니다.")
        );
        if (post.getUsername().equals(userFromToken.getUsername())){
//            post.update(requestDto);
        }else{
            throw new IllegalArgumentException("등록한 게시물만 수정할 수 있습니다.");
        }
        return new PostResponseDto(post);
    }
//    @Transactional
//    public ArticleResponseDto updateArticle(Long id, ArticleResponseDto requestDto, HttpServletRequest request) {
//        User userFromToken = serviceConfig.findUserFromToken(request);
//
//        Article article = articleRepository.findById(id).orElseThrow(
//                ()-> new RuntimeException("글이 없다")
//        );
//
//        if (article.getUsername().equals(userFromToken.getUsername())){
//            article.update(requestDto);
//        }else {
//            throw new IllegalArgumentException("자신의 글만 수정할 수 있습니다.");
//        }
//
//        return new ArticleResponseDto(article);
//    }

    @Transactional
    public Long deletePost(Long id, String pw, PostRequestDto requestDto) {
        String password = requestDto.getPassword();
        if(password.equals(pw)){
            postRepository.deleteById(id);
        }else {
            System.out.println("비밀번호가 일치하지 않습니다!");
        }
        return id;
    }
// 구버전 선택 게시물 조회
//    @Transactional
//    public String postView(Long id, PostRequestDto requestDto) {
//        Post post = postRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        );
//        String title = requestDto.getTitle();
//        String name = requestDto.getName();
//        String password = requestDto.getPassword();
//        String content = requestDto.getContents();
//        String result = "title : " + title + ", username : " + name + ", password : " + password + ", content : " + content;
//
//        return result;
//    }

    // 구버전 업데이트
//    @Transactional
//    public Long update(Long id, String pw, PostRequestDto requestDto) {
//        Post post = postRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        );
//        String password = requestDto.getPassword();
//        if(password.equals(pw)){
//            post.update(requestDto);
//        }else{
//            System.out.println("비밀번호가 일치하지 않습니다!");
//        }
//        return post.getId();
//    }

}
