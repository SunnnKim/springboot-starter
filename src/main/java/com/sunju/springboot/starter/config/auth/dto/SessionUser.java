package com.sunju.springboot.starter.config.auth.dto;

import com.sunju.springboot.starter.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    /**
     * 인증된 사용자정보만 저장
     * 1. User 클래스에 저장하지 않는 이유
     * -> User는 직렬화를 구현하지 않았기 떄문에 에러가 발생한다
     * 2. User 클래스를 직렬화하지 않는 이유?
     * -> User 클래스가 다른 엔티티와 어떤 관계를 형성할 지 모르기 때문에
     *    serialize 를 하게 되면 성능이슈, 부수효과가 발생할 수 있다.
     *    따라서 직렬화 기능을 가진 다른 세션 dto 를 만드는 것이 유지보수에 낫다
     */
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
