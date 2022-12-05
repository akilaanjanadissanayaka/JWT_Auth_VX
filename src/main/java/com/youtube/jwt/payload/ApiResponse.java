package com.youtube.jwt.payload;

import java.util.List;

public class ApiResponse {
    private Boolean success;
    private String message;
    private List<?> list;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(Boolean success, List<?> list) {
        this.success = success;
        this.list = list;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

}
