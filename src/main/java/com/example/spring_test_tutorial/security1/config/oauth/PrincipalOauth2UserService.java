package com.example.spring_test_tutorial.security1.config.oauth;

import com.example.spring_test_tutorial.security1.config.auth.PrincipalDetails;
import com.example.spring_test_tutorial.security1.model.User;
import com.example.spring_test_tutorial.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    UserRepository userRepository;

    //구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{


        OAuth2User oauth2User =super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getClientId();// google
        String providerId = oauth2User.getAttribute("sub");// 구글의 개인 id
        String username = provider + "_" + providerId;   //google_sub
        String password =   "21312312";// 적당히 암호화해서 넣어도 되는데 의미는 없음
        String email = oauth2User.getAttribute("email");
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);

        if(userEntity==null){
            userEntity=User.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .provider(provider)
                .providerId(providerId)
                .build();
            userRepository.save(userEntity);
        }
        else{

        }

        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
    }


}