package com.sunju.springboot.starter.web;

import com.sunju.springboot.starter.config.auth.LoginUser;
import com.sunju.springboot.starter.config.auth.dto.SessionUser;
import com.sunju.springboot.starter.service.posts.PostsService;
import com.sunju.springboot.starter.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){

        model.addAttribute("posts", postsService.findAllDesc());
        // CustomOAuth2UserService 에서 로그인 성공 시 세션에 SessionUser 저장하도록 구성 (어노테이션 방식으로 구현했기 떄문에 주석처리)
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user != null){
            // 세션에 값이 있는 경우만 userName 등록
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }
    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){

        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }

}
