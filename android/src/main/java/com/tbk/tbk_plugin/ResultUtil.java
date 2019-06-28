package com.tbk.tbk_plugin;

import java.io.Serializable;

public class ResultUtil implements Serializable {

    private String keyPlatform = Constants.keyAndroid;
    private Boolean keyResult = true;
    private Object user;
    private Integer keyErrorCode;
    private String keyErrorMessage;

    public ResultUtil() {
    }

    public ResultUtil(Object user) {
        this.user = user;
    }

    public ResultUtil(Boolean keyResult) {
        this.keyResult = keyResult;
    }
    public ResultUtil(Boolean keyResult, Integer keyErrorCode, String keyErrorMessage) {
        this.keyResult = keyResult;
        this.keyErrorCode = keyErrorCode;
        this.keyErrorMessage = keyErrorMessage;
    }

    public String getKeyPlatform() {
        return keyPlatform;
    }

    public void setKeyPlatform(String keyPlatform) {
        this.keyPlatform = keyPlatform;
    }

    public Boolean getKeyResult() {
        return keyResult;
    }

    public void setKeyResult(Boolean keyResult) {
        this.keyResult = keyResult;
    }

    public Integer getKeyErrorCode() {
        return keyErrorCode;
    }

    public void setKeyErrorCode(Integer keyErrorCode) {
        this.keyErrorCode = keyErrorCode;
    }

    public String getKeyErrorMessage() {
        return keyErrorMessage;
    }

    public void setKeyErrorMessage(String keyErrorMessage) {
        this.keyErrorMessage = keyErrorMessage;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }
}
