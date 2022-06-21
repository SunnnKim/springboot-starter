package com.sunju.springboot.starter.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunju.springboot.starter.domain.posts.Posts;
import com.sunju.springboot.starter.domain.posts.PostsRepository;
import com.sunju.springboot.starter.service.posts.PostsService;
import com.sunju.springboot.starter.web.dto.PostsResponseDto;
import com.sunju.springboot.starter.web.dto.PostsSaveRequestDto;
import com.sunju.springboot.starter.web.dto.PostsUpdateRequestDto;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * WithMockUser : 모의 사용자 만들어서 사용, 이 어노테이션으로 인해 ROLE_USER 권한을 가진 사용자가
     * API를 요청하는 것과 동일한 효과를 가지게 됨
     */
    @Test
    @WithMockUser(roles="USER")
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

//        // when
//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, dto, Long.class);
//
//        // then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK); // 200
//        assertThat(responseEntity.getBody()).isGreaterThan(0L); // 응답바디의 값이 0보다 클때 (저장성공)
//
//        List<Posts> all = postsRepository.findAll();
//        assertThat(all.get(0).getAuthor()).isEqualTo(author);
//        assertThat(all.get(0).getContent()).isEqualTo(content);
//        assertThat(all.get(0).getTitle()).isEqualTo(title);
        // when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());
        // then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }

    /**
     * WithMockUser : 모의 사용자 만들어서 사용, 이 어노테이션으로 인해 ROLE_USER 권한을 가진 사용자가
     * API를 요청하는 것과 동일한 효과를 가지게 됨
     */
    @Test
    @WithMockUser(roles="USER")
    public void updateTest() throws Exception{
        // given
        String author = "author";
        String title = "title1";
        String content = "content1";

        // save
        Posts savePosts = postsRepository.save(Posts.builder()
                .author(author)
                .title(title)
                .content(content)
                .build());

        // id 조회
        Long updatedId = savePosts.getId();
        // update용
        String expectedTitle = "title2";
        String expectedContent = "content2";

        //  update REQ
        String url = "http://localhost:" + port + "/api/v1/posts/" + updatedId ;
        PostsUpdateRequestDto updateDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();
        HttpEntity<PostsUpdateRequestDto> entity = new HttpEntity<>(updateDto);

//        // when
//        ResponseEntity<Long> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Long.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isGreaterThan(0L);
//
//        List<Posts> postsLists = postsRepository.findAll();
//        assertThat(postsLists.get(0).getContent()).isEqualTo(expectedContent);
//        assertThat(postsLists.get(0).getTitle()).isEqualTo(expectedTitle);

        /**
         * ObjectMapper : 본문영역을 문자열로 표현하기 위해 JSON 으로 변환
         */
        // when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateDto)))
                .andExpect(status().isOk());

        // then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle())
                .isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }


}
