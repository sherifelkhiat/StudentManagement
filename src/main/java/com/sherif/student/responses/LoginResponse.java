package com.sherif.student.responses;

public class LoginResponse {
    private String token;

    private long expiresIn;

    private String refreshToken;

    private long refreshExpireIn;

    public String getToken() {
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public LoginResponse setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public long getRefreshExpireIn() {
        return refreshExpireIn;
    }

    public LoginResponse setRefreshExpireIn(long refreshExpireIn) {
        this.refreshExpireIn = refreshExpireIn;
        return this;
    }
}