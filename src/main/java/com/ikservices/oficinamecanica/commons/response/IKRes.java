package com.ikservices.oficinamecanica.commons.response;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
public class IKRes<T> implements Serializable {
    private T obj;
    private List<T> objList;
    private String message;

    public static <T> IKRes<T> build() {
        return new IKRes<T>();
    }

    public IKRes<T> body(T obj) {
        this.obj = obj;
        return this;
    }

    public IKRes<T> body(List<T> objList) {
        this.objList = objList;
        return this;
    }

    public IKRes<T> addMessage(String message) {
        this.message = message;
        return this;
    }
}
