package com.sunju.springboot.starter.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter // getter 자동생성
@RequiredArgsConstructor // 생성자 자동생성
public class HelloResponseDto {

    private final String name;
    private final int amount;

}
