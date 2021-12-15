package com.leaning_machine.base.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.leaning_machine.base.dto.BaseDto;

public class TerminalAuthDto extends BaseDto {
    @Expose
    @SerializedName("result")
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
