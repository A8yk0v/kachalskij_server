package ru.nstu.response;

public class CalculateResponse {
    private String status;

    public CalculateResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
