package com.example.demo.util.result;

import com.google.gson.Gson;

public class Response {

    private boolean success;
    private int error;
    private String message;
    private Object data;

    public Response() {
        this.setSuccess(false);
        this.setMessage("json格式不正确");
        this.setError(-1);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toJson() {
        return (new Gson()).toJson(this);
    }

    public void setResultSuccess() {
        this.setSuccess(true);
        this.setError(0);
        this.setMessage("success");
    }

    public void setError() {
        if (error < 1) {
            error = 1;
        } else {
            error++;
        }
    }
}
