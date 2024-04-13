package com.vndevteam.javawebspringboot3.domain.enums;

import lombok.Getter;

@Getter
public enum Message {
    MSG_1("msg-1"),
    MSG_2("msg-2"),
    ERR_1("err-1");

    public final String code;

    Message(String code) {
        this.code = code;
    }
}
