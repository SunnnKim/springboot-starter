package com.sunju.springboot.starter.web.domain.posts;


import com.sunju.springboot.starter.domain.posts.PostsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach // After -> AfterEach
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void inquireAll(){
        // given
        String title = "테스트 게시글";

    }

}
