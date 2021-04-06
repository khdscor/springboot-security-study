package com.example.spring_test_tutorial.security1.config.oauth.provider;


import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo{

    private Map<String,Object> attributes; // oauth2User.getAttributes()

    public NaverUserInfo(Map<String,Object> attributes){
        this.attributes=attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
