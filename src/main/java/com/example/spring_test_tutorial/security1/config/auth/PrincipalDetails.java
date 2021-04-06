package com.example.spring_test_tutorial.security1.config.auth;

//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
//로그인을 진행이 완료가 되면 시큐리티 session을 만들어 준다.(자신만의 session 공간,키값을 구분)(Security ContextHolder 에다 키값을 저장)
//세션에 들어갈 수 있는 오브젝트가 정해져 있다.
// 오브젝트는 Authentication 타입 객체
// Authentication 안에 User 정보가 있어야함.
// User 오브젝트 타입은 UserDetails 타입 객체
// Security Session => Authentication => UserDetails

// 아래에서 PrincipalDetails 은 UserDetails 타입이 됬다.

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import com.example.spring_test_tutorial.security1.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class  PrincipalDetails implements UserDetails, OAuth2User {

    private User user;

    private Map<String, Object> attributes;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    public PrincipalDetails(User user, Map<String, Object> attributes){
        this.user=user;
        this.attributes=attributes;
    }
    //해당 User의 권한을 리턴하는 곳!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    @Override
    public String getUsername() {

        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }  // 니 계정 만료됬니?

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }  // 니 계정 잠겼니??

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }  // 니 계정 비밀번호 기간만료됬니?

    @Override
    public boolean isEnabled() {
        return true;
    }  //니 계정 활성화되있니?, ex. 휴면계정, TimeStamp loginDate 로 가능, 현재시간- 로그인시간

    // ---------------------------------------------------------
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
        //return attributes.get("sub"); 는 구글 고유 PK
    }
}
