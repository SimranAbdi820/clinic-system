package com.clinic.clinic_backend.dto;

public class ApiResponse<T> {
    private boolean status;
    private T data;
    private String message;

    public ApiResponse(boolean status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public boolean isStatus() { return status; }
    public T getData() { return data; }
    public String getMessage() { return message; }
}