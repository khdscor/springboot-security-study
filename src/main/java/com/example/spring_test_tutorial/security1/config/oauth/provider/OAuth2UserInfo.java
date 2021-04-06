package com.example.spring_test_tutorial.security1.config.oauth.provider;

public interface OAuth2UserInfo {

    String getProviderId();
    String getProvider();
    String getName();
    String getEmail();
}
