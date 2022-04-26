package com.sunju.springboot.starter.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
    /**
     * BaseTimeEntity :
     * - 모든 Entity 클래스의 상위클래스가 되어 LocalDateTime을 자동으로 관리함
     *
     * @MappedSuperclass : 엔티티 클래스들이 BaseTimeEntity를 상속할 경우,
     * BaseTimeEntity의 필드들도 자동적으로 컬럼으로 인식하도록 함
     *
     * @EntityListeners : Auditing 기능 포함시킴
     *
     * @CreatedDate : 엔티티가 생성되어 저장될 때 시간자동저장
     *
     * @LastModifiedDate : 조회한 엔티티의 값을 변경할 때 시간자동저장
     *
     */
}
