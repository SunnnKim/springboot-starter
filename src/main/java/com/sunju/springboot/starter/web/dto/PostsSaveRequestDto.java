package com.sunju.springboot.starter.web.dto;

import com.sunju.springboot.starter.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String title;
    private String content;
    private String author;

    // 빌더 메소드 :: 생성자에 어노테이션 붙이면 생성자에서 받는 필드만 빌더로 생성가능하지만
    // 클래스 레벨에서 어노테이션을 붙이면 클래스 내 모든 필드에서 빌더생성이 가능하다
    @Builder
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(this.title)
                .author(this.author)
                .content(this.content)
                .build();
    }

}
