package com.leaning_machine.base.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseDto<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Expose
    @SerializedName("code")
    private int businessCode;
    @Expose @SerializedName("message")
    private String message;
    @Expose @SerializedName("result")
    private T result;

    public int getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(int businessCode) {
        this.businessCode = businessCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseDto{" +
                "businessCode=" + businessCode +
                ", message='" + message + '\'' +
                '}';
    }
}

