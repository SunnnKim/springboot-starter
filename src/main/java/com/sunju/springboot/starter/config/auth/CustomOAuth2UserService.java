package com.sunju.springboot.starter.config.auth;

import com.sunju.springboot.starter.config.auth.dto.OAuthAttributes;
import com.sunju.springboot.starter.config.auth.dto.SessionUser;
import com.sunju.springboot.starter.domain.user.User;
import com.sunju.springboot.starter.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /**
        registrationId :
            - 현재 로그인 진행 중인 서비스를 구분하는 코드
            - 구글만 사용하는 블필요한 값이지만, 이후 네이버 로그인 연동시에 네이버로그인인지, 구글로그인인지 구분하기위해 사용
         userNameAttributeName
            - OAuth2 로그인 진행 시 키가 되는 필드값. primary key 와 같은 의미임
            - 구글은 기본적으로 코드를 지원, 네이버/카카오 미지원 (구글 기본코드 = sub)
            - 네이버 로그인과 구글 로그인을 동시 지원할 때 사용
         OAuthAttributes
            - OAuth2UserService 를 통해 가져온 oAuth2User의 attribute 를 담을 클래스
            - 네이버 등 다른 소셜 로그인도 해당 클래스 이용
         SessionUser
            - 세션에 사용자 정보를 저장하기 위한 Dto 클래스
         */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                                        attributes.getAttributes(),
                                        attributes.getNameAttributeKey()
        );

    }

    /**
     * 구글 사용자정보가 업데이트 되었을 때
     * 사용자이름, 프로필사진이 변경되면 User엔티티에도 반영됨
     * @param attributes
     * @return
     */
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }

}
