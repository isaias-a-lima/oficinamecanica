package com.ikservices.oficinamecanica.commons.response;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ResponseIKS<T> {

    private Integer httpCode;
    private String httpMessage;
    private T obj;
    private List<T> objList;
    private Map<MessageType, String> messages;

    public ResponseIKS<T> ok(T obj){
        this.httpCode = 200;
        this.httpMessage = "";
        this.obj = obj;
        this.messages = new HashMap<>();
        return this;
    }

    public ResponseIKS<T> ok(List<T> objList){
        this.httpCode = 200;
        this.httpMessage = "";
        this.objList = objList;
        this.messages = new HashMap<>();
        return this;
    }

    public ResponseIKS<T> status(Integer httpCode, String httpMessage) {
        this.httpCode = httpCode;
        this.httpMessage = httpMessage;
        this.messages = new HashMap<>();
        return this;
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