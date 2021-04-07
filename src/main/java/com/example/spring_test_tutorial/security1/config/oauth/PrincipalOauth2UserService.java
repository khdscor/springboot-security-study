package com.example.spring_test_tutorial.security1.config.oauth;

import com.example.spring_test_tutorial.security1.config.auth.PrincipalDetails;
import com.example.spring_test_tutorial.security1.config.oauth.provider.GoogleUserInfo;
import com.example.spring_test_tutorial.security1.config.oauth.provider.NaverUserInfo;
import com.example.spring_test_tutorial.security1.config.oauth.provider.OAuth2UserInfo;
import com.example.spring_test_tutorial.security1.model.User;
import com.example.spring_test_tutorial.security1.repository.UserRepository;
import java.util.Map;
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

        OAuth2UserInfo oauthUserInfo =null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oauthUserInfo= new GoogleUserInfo(oauth2User.getAttributes());
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oauthUserInfo= new NaverUserInfo((Map<String, Object>) oauth2User.getAttributes().get("response"));
        }  // response 안에 response 가 있기에 이런식으로 구성 id,email,name 이 들어있다.




        String provider = oauthUserInfo.getProvider();// google or naver
        String providerId = oauthUserInfo.getProviderId();// 개인 id
        String username = provider + "_" + providerId;   //
        String password =   "21312312";// 적당히 암호화해서 넣어도 되는데 의미는 없음
        String email = oauthUserInfo.getEmail();
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

        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
    }


}
