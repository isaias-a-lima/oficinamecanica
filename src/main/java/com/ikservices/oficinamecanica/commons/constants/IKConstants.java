package com.ikservices.oficinamecanica.commons.constants;

import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import org.slf4j.Logger;

public class IKConstants {

    private static final Logger LOGGER = IKLoggerUtil.getLogger(Constants.class);
    public static final String DEFAULT_SUCCESS_CODE = "00";
    public static final String DEFAULT_ERROR_CODE = "-1";
    public static final String IK_HTTP_BAD_REQUEST_CODE = "400";
    public static final String IK_HTTP_NOT_FOUND_CODE = "404";
    public static final String IK_HTTP_ALREADY_EXISTS = "409";
    public static final String EMAIL_PATTERN = "^[^.]+[a-zA-Z0-9-_.]*[a-zA-Z-_]+[a-zA-Z0-9-_]*@[a-zA-Z0-9]+\\.[a-zA-Z.]{2,}$";
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{4,}$";
    public static final String PASSWORD_LATIN_PATTERN = "^[a-zA-Z0-9@$!%*?&#-_]+$";
    public static String PASSWORD_INVALID_MESSAGE = "Invalid password";
    public static String PASSWORD_NO_LATIN_MESSAGE = "Password must have latin letters";
    public static String PASSWORD_NULL_OR_EMPTY_MESSAGE = "Password cannot be null or empty";
    public static String RESOURCE_NOT_FOUND_MESSAGE = "Resource not found";
    public static String RESOURCE_TO_UPDATE_NOT_FOUND_MESSAGE = "Resource to update not found";
    public static String EMPTY_LIST_MESSAGE = "The list is empty";
    public static String NULL_OBJECT_MESSAGE = "The object cannot be null";
    public static String EXPIRED_DELETE_DATE = "Expired deletion date";
}
