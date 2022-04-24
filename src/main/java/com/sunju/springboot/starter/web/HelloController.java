package com.sunju.springboot.starter.web;

import com.sunju.springboot.starter.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount){

        return new HelloResponseDto(name, amount);

    }
}

/**
 * @RestController
 * - 리턴값을 JSON 객체로 반환하는 컨트롤러
 * @GetMapping
 * - HTTP GET 요청을 받을 수 있도록 설정하는 어노테이션
 * - @RequestMapping(method=RequestMethod.GET)
 */
