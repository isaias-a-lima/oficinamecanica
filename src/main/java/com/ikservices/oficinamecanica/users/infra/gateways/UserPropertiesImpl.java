package com.ikservices.oficinamecanica.users.infra.gateways;

import com.ikservices.oficinamecanica.users.application.gateways.UserProperties;
import org.springframework.core.env.Environment;

public class UserPropertiesImpl implements UserProperties {

    private final String errorFailureInOperation;
    private final String errorLoginCode;
    private final String errorLoginMessage;
    private final String errorNotFoundDatasMessage;
    private final String errorNullObjectMessage;
    private final String successUserRemoved;

    public UserPropertiesImpl(Environment environment) {
        errorLoginCode = environment.getProperty(ERROR_LOGIN_CODE_KEY);
        errorLoginMessage = environment.getProperty(ERROR_LOGIN_MESSAGE_KEY);
        errorNotFoundDatasMessage = environment.getProperty(ERROR_NOT_FOUND_DATAS_MESSAGE_KEY);
        errorNullObjectMessage = environment.getProperty(ERROR_NULL_OBJECT_MESSAGE_KEY);
        successUserRemoved = environment.getProperty(SUCCESS_USER_REMOVED_MESSAGE_KEY);
        errorFailureInOperation = environment.getProperty(ERROR_FAILURE_IN_OPERATION_KEY);
    }

    @Override
    public String getErrorFailureInOperation() {
        return errorFailureInOperation;
    }

    @Override
    public String getErrorLoginCode() {
        return errorLoginCode;
    }

    @Override
    public String getErrorLoginMessage() {
        return errorLoginMessage;
    }

    @Override
    public String getErrorNotFoundDatas() {
        return errorNotFoundDatasMessage;
    }

    @Override
    public String getErrorNullObject(String objectName) {
        return String.format(errorNullObjectMessage, objectName);
    }

    @Override
    public String getSuccessUserRemoved() {
        return successUserRemoved;
    }
}
