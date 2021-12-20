package com.leaning_machine.base.dto;


import java.io.Serializable;

public class TerminalAuthDto implements Serializable {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TerminalAuthDto{" +
                "token='" + token + '\'' +
                '}';
    }
}
