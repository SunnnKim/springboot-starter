package com.sunju.springboot.starter.web.domain.posts;


import com.sunju.springboot.starter.domain.posts.Posts;
import com.sunju.springboot.starter.domain.posts.PostsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        String content = "테스트본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("sunju@naver.com")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
        /**
         * @AfterEach :
         * - Junit 단위테스트가 수행되고 난 후 수행되는 메소드
         * - insert, update 등의 작업 후 데이터가 침범하지 않게 하기 위함
         * - 여러개의 단위테스트 진행 시 데이터가 그대로 남아 테스트가 수행되지 않는 것을 막기 위함
         *
         * .save() :
         * - 저장하는 메소드
         * - id가 있는 경우 update, 없는 경우 Insert
         *
         * .findAll() :
         * - 전체 컬럼을 불러옴
         *
         * */
    }

    // Auditing test
    @Test
    public void BaseTimeEntityTest () {
        //given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // when
        List<Posts> list = postsRepository.findAll();

        // then
        Posts posts = list.get(0);

        System.out.println(">>>>>>>>> createDate = " + posts.getCreatedDate() + ", modifiedDate = " + posts.getModifiedDate());
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
