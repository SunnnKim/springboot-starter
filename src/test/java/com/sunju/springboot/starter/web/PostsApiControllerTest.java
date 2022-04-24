package com.sunju.springboot.starter.web;

import com.sunju.springboot.starter.domain.posts.Posts;
import com.sunju.springboot.starter.domain.posts.PostsRepository;
import com.sunju.springboot.starter.service.posts.PostsService;
import com.sunju.springboot.starter.web.dto.PostsSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void clear() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void testSave() throws Exception {
        // given
        String title = "title";
        String content = "content";
        String author = "author";

        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, dto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK); // 200
        assertThat(responseEntity.getBody()).isGreaterThan(0L); // 응답바디의 값이 0보다 클때 (저장성공)

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getAuthor()).isEqualTo(author);
        assertThat(all.get(0).getContent()).isEqualTo(content);
        assertThat(all.get(0).getTitle()).isEqualTo(title);


    }
}
