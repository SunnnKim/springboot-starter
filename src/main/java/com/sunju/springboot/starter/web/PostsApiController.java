package com.sunju.springboot.starter.web;

import com.sunju.springboot.starter.service.posts.PostsService;
import com.sunju.springboot.starter.web.dto.PostsResponseDto;
import com.sunju.springboot.starter.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto postsSaveRequestDto){

        return postsService.save(postsSaveRequestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id,
                       @RequestBody PostsSaveRequestDto postsSaveRequestDto){
        return postsService.update(id, postsSaveRequestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findByid(@PathVariable Long id){
        return postsService.findById(id);
    }


}
