package ru.nstu.response;

public class CalculateResponse {
    private String status;
    private StringBuilder content;

    public CalculateResponse(String status) {
        this.status = status;
        content = new StringBuilder();
    }

    public void addStrLIne(String str) {
        content.append(str);
        content.append('\n');
    }

    public StringBuilder getContent() {
        return content;
    }

    public void setContent(StringBuilder content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{\"status\":\"" + status + "\"" +
                ", \"content\":\"" + content.toString() + "\"" +
                "}";
    }
}
