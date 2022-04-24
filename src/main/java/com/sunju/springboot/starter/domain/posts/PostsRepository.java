package com.sunju.springboot.starter.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    /**
     * JpaRepository :
     * - ibatis 등에서 Dao(DB Layer 접근자)와 동일
     * - 인터페이스로 생성
     * - JpaRepository<Entity 클래스, PK타입>
     * - @Repository 추가할 필요없음
     * - **** Entity 클래스와 같은 레벨에 위치해야 함! ****
     * -> 같은 도메인 패키지에서 관리하는 것이 중요함
     */


}
