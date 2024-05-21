package com.ikservices.oficinamecanica.users.application.gateways;

public interface UserProperties {

    String ERROR_FAILURE_IN_OPERATION_KEY = "users.errorFailureInOperation.message";
    String ERROR_LOGIN_MESSAGE_KEY = "users.errorlogin.message";
    String ERROR_LOGIN_CODE_KEY = "users.errorlogin.code";
    String ERROR_NOT_FOUND_DATAS_MESSAGE_KEY = "users.errorNotFoundDatas.message";
    String ERROR_NULL_OBJECT_MESSAGE_KEY = "users.errorNullObject.message";
    String SUCCESS_USER_REMOVED_MESSAGE_KEY = "users.successUserRemoved.message";

    String getErrorFailureInOperation();

    String getErrorLoginCode();

    String getErrorLoginMessage();

    String getErrorNotFoundDatas();

    String getErrorNullObject(String objectName);

    String getSuccessUserRemoved();
}
