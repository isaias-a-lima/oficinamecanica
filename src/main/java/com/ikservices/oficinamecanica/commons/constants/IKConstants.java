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

    public static String RESOURCE_NOT_FOUND_MESSAGE;
    public static String RESOURCE_TO_UPDATE_NOT_FOUND_MESSAGE;
}
