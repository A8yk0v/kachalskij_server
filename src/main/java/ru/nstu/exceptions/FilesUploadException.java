package ru.nstu.exceptions;

public class FilesUploadException extends Exception {
    private String status;

    public FilesUploadException(String status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }
}
