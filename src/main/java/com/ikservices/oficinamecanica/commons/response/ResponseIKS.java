package com.ikservices.oficinamecanica.commons.response;

import com.ikservices.oficinamecanica.users.infra.controller.requests.LoginUserResponse;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ResponseIKS<T> {

    private T obj;
    private List<T> objList;
    private Map<MessageType, String> messages;

    private ResponseIKS() {
        this.messages = new HashMap<>();
    }

    public static <T> ResponseIKS<T> build() {
        return new ResponseIKS<T>();
    }

    public ResponseIKS<T> body(T obj) {
        this.obj = obj;
        return this;
    }

    public ResponseIKS<T> body(List<T> objList) {
        this.objList = objList;
        return this;
    }

    public ResponseIKS<T> addMessage(MessageType type, String message) {
        this.messages.put(type, message);
        return this;
    }
}