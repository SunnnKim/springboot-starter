package com.sunju.springboot.starter.service.posts;

import com.sunju.springboot.starter.domain.posts.Posts;
import com.sunju.springboot.starter.domain.posts.PostsRepository;
import com.sunju.springboot.starter.web.dto.PostsListResponseDto;
import com.sunju.springboot.starter.web.dto.PostsResponseDto;
import com.sunju.springboot.starter.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto){
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsSaveRequestDto postsSaveRequestDto){
        Posts posts = postsRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("해당게시글이 없습니다."));
        posts.update(postsSaveRequestDto.getTitle(), postsSaveRequestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("해당게시글이 없습니다."));
        return new PostsResponseDto(entity);
    }

    // readOnly = true ::: 트랜젝션의 범위는 유지하되, 조회기능만 남김. 삭제, 수정, 삽입등의 기능이 없어서 조회속도가 개선
   @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
   }

   @Transactional
    public void delete(Long id){
        // 존재하는 id인지 확인 후 삭제
        // 존재하지 않는 경우 Exception 던지기
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        // JPA 기본제공
        postsRepository.delete(posts);
   }

}
