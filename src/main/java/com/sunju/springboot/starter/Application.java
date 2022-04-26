package com.sunju.springboot.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @ SpringBootApplication
 * 스프링부트의 자동설정, 스프링 Bean 읽기, 생성 등 모두 자동으로 설정되는 어노테이션
 * SpringBootApplication 어노테이션 위치부터 설정을 읽어가기 떄문에
 * 항상 프로젝트 최상단에 위치해야함
 */
@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        /**
         * main 함수의 SpringApplication.run() 메소드로 인해
         * 내장 WAS가 돌아감
         * 별도의 톰캣 설치 x
         * 부트로 만들어진 jar 파일만 실행하면 됨
         */
        SpringApplication.run(Application.class, args);
    }
}
