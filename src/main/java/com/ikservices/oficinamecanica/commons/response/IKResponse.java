package com.ikservices.oficinamecanica.commons.response;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class IKResponse<T> implements Serializable {

    private T obj;
    private List<T> objList;
    private final Map<IKMessageType, String> messages;
    private String message;

    private IKResponse() {
        this.messages = new HashMap<>();
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

    public IKResponse<T> addMessage(IKMessageType type, String message) {
        this.messages.entrySet().removeIf(entry -> entry.getKey() == null);
        this.messages.put(type, message);
        return this;
    }

    public IKResponse<T> addMessage(String message) {
        this.message = message;
        return this;
    }
}