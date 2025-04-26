package com.tauhoa.train.dtos.response;

public class BookingResponse {

    private String status;  // Trạng thái (ví dụ: "success" hoặc "error")
    private String message; // Thông báo lỗi hoặc thành công

    // Getter và Setter cho status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter và Setter cho message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}