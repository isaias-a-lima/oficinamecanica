package com.ikservices.oficinamecanica.commons.response;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class IKResponse<T> implements Serializable {

    private T obj;
    private List<T> objList;
    private final List<IKMessage> messages;

    private IKResponse() {
        this.messages = new ArrayList<>();
    }

    public static <T> IKResponse<T> build() {
        return new IKResponse<T>();
    }

    public IKResponse<T> body(T obj) {
        this.obj = obj;
        return this;
    }

    public IKResponse<T> body(List<T> objList) {
        this.objList = objList;
        return this;
    }

    public IKResponse<T> addMessage(String code, IKMessageType type, String message) {
        this.messages.add(new IKMessage(code, type.getCode(), message));
        return this;
    }
}