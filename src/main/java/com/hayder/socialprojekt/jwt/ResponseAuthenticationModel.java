package com.hayder.socialprojekt.jwt;

public class ResponseAuthenticationModel {
    final String token;

    public ResponseAuthenticationModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
