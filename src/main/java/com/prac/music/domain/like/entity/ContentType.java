package com.prac.music.domain.like.entity;

public enum ContentType {
    BOARD(Type.BOARD),
    COMMENT(Type.COMMENT);

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }

    public static class Type {
        public static final String BOARD = "BOARD";
        public static final String COMMENT = "COMMENT";
    }
}
