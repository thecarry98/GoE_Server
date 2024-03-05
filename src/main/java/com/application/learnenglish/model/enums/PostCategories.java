package com.application.learnenglish.model.enums;

public enum PostCategories {
    IELTS ("Ielts"),
    TOEIC("Toeic"),
    THPTQG("THPTQG");

    private final String value;

    PostCategories(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
