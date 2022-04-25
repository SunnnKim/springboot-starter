package com.sunju.springboot.starter.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // ****Entity 클래스엔 Setter 생성하지 않느다
@NoArgsConstructor
@Entity // JPA Annotation
public class Posts { // Entity Class

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    /***
     * @Entity : 테이블에 링크될 클래스임을 명시
     * - 컬럼 -> 필드 = (_) -> 카멜케이스
     * - ex) sale_manager (TABLE) -> SaleManager.java
     * - Setter 메소드가 없다. 해당 필드값 변경이 필요하면 목적과 의도가 분명한 메소드를 하나 추가하도록 한다.
     * - 해당 클래스의 인스턴스 값이 언제 어디서 변해야하는지 명확히 구분하기 힘들기 때문에 Setter를 생성하지 않음
     *
     *  @ID : PK 지정
     * - PK는 Long타입의 Auto_increment 지정하는 것을 권장 (Long -> BigInt로 변환)
     *
     * @GeneratedValue : PK 생성규칙지정
     * - GenerationType.IDENTITY 옵션 추가하면 auto_increment
     *
     * @Column :
     * - 테이블의 칼럼용 변수, 굳이 지정하지 않아도 해당 클래스의 필드는 모두 칼럼이 됨
     * - 기본값 이외에 추가로 변경옵션을 지정하는 경우 사용함
     * ex1) VARCHAR 기본값이 255 -> 500 으로 사이즈를 늘리고 싶은 경우 (title)
     * ex2) 타입을 TEXT로 변경하고 싶은 경우 (content)
     *
     * @Builder :
     * - 해당클래스의 빌더 패턴 클래스를 생성함
     * - 생성자 상단에 선언시 생성자에 포함된 필드만 빌더에 포함
     *
     *
     * Q : Setter가 없는 Entity에서 어떻게 값을 채워 DB에 삽입하는지?
     * - 기본적인 구조는 생성자를 통해 최종 값을 채운 뒤 DB에 삽입하는 것이다.
     * - 값 변경이 필요한 경우는 반드시 의도와 목적이 분명한 *메소드*를 호출하여 변경한다.
     *
     * Q : 생성자와 빌더를 통해 필드값을 채우는 것의 차이점은?
     * - 둘 다 생성시점에 Entity 필드값을 채워주는 역할을 수행한다.
     * - 생성자의 경우, 지금 채워야 할 필드값을 명확히 지정할 수 없다.
     * - 빌더의 경우, 어느 필드에 어떤 값을 채워야하는지 명확하게 인지가능하다.
     * - 따라서 빌더패던을 사용하는 것이 오류시점의 필드값이 어떤 것이 들어갔는지 명확하게 알 수 있다.
     *
     */

    // 업데이트 메소드
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}
