package com.mjc.school.service.exceptions;

public enum ServiceErrorCode {

    NEWS_ID_DOES_NOT_EXIST("000001", "News with id %d does not exist."),
    AUTHOR_ID_DOES_NOT_EXIST("000002", "Author with id %d does not exist."),
    TAG_ID_DOES_NOT_EXIST("000003", "Tag with id %d does not exist."),
    AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID("000004", "Author not found for news with id %d."),
    COMMENT_ID_DOES_NOT_EXIST("000005", "Comment with id %d does not exist."),
    VALIDATION("000013", "Validation failed: %s");

    private final String errorMessage;

    ServiceErrorCode(String errorCode, String message) {
        this.errorMessage = "ERROR_CODE: " + errorCode + " ERROR_MESSAGE: " + message;
    }

    public String getMessage() {
        return errorMessage;
    }
}
