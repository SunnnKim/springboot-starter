package com.sunju.springboot.starter.web;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void LombokAppTest(){
        // given
        String name = "test";
        int amount = 1000;

        // when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);

        /**
         * assertThat
         * - assertj 라는 테스트 검증 라이브러리의 검증메소드
         * - 메소드 체이닝을 지원해서 isEqualsTo 와 같이 메소드를 이어서 사용할 수 있음
         *
         * ** Junit의 assertThat / assertj 의 assertThat 차이점
         * - assertj : CoreMatchers와 달리 추가적으로 라이브러리가 필요하지 않다
         * - assertj : 자동완성 지원이 좀 더 확실함
         *
         */
    }
}
