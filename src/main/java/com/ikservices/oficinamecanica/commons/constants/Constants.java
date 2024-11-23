package com.ikservices.oficinamecanica.commons.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Constants {

    public static final String DEFAULT_SUCCESS_CODE = "00";
    public static final String DEFAULT_ERROR_CODE = "-1";

    private static String LANGUAGE;
    private static String COUNTRY;
    private static Locale LOCALE;
    private static String TIME_ZONE;
    private static String NULL_PARAM_MESSAGE;
    private static String INVALID_PARAM_MESSAGE;
    private static String PHONE_VO_ERROR_MESSAGE;
    private static String ADDRESS_VO_INVALID_POSTAL_CODE_MESSAGE;
    private static String ADDRESS_VO_INVALID_STREET_MESSAGE;
    private static String IDENTIFICATION_DOCUMENT_VO_INVALID_MESSAGE;
    private static String EMAIL_VO_INVALID_MESSAGE;

    public static String getLANGUAGE() {
        return LANGUAGE;
    }

    public static String getCOUNTRY() {
        return COUNTRY;
    }

    public static Locale getLOCALE() {
        return LOCALE;
    }

    public static String getTIME_ZONE() {
        return TIME_ZONE;
    }

    public static String getNULL_PARAM_MESSAGE() {
        return NULL_PARAM_MESSAGE;
    }

    public static String getINVALID_PARAM_MESSAGE() {
        return INVALID_PARAM_MESSAGE;
    }

    public static String getPHONE_VO_ERROR_MESSAGE() {
        return PHONE_VO_ERROR_MESSAGE;
    }

    public static String getADDRESS_VO_INVALID_POSTAL_CODE_MESSAGE() {
        return ADDRESS_VO_INVALID_POSTAL_CODE_MESSAGE;
    }

    public static String getADDRESS_VO_INVALID_STREET_MESSAGE() {
        return ADDRESS_VO_INVALID_STREET_MESSAGE;
    }

    public static String getIDENTIFICATION_DOCUMENT_VO_INVALID_MESSAGE() {
        return IDENTIFICATION_DOCUMENT_VO_INVALID_MESSAGE;
    }

    public static String getEMAIL_VO_INVALID_MESSAGE() {
        return EMAIL_VO_INVALID_MESSAGE;
    }

    public Constants(@Value("${app.language}") String language,
                     @Value("${app.country}") String country,
                     @Value("${app.timezone}") String timezone,
                     @Value("${null.param.message}") String nullParamMessage,
                     @Value("${invalid.param.message}") String invalidParamMessage,
                     @Value("${phone.vo.error.message}") String phoneVOErrorMessage,
                     @Value("${address.vo.invalid.postal.code.message}") String addressVOInvalidPostalCodeMessage,
                     @Value("${address.vo.Invalid.street.message}") String addressVOInvalidStreetMessage,
                     @Value("${identification.document.vo.invalid.message}") String identificationDocumentVOInvalidMessage,
                     @Value("${email.vo.invalid.message}") String emailVOInvalidMessage) {
        LANGUAGE = language;
        COUNTRY = country;
        TIME_ZONE = timezone;
        LOCALE = new Locale(LANGUAGE, COUNTRY);
        NULL_PARAM_MESSAGE = nullParamMessage;
        INVALID_PARAM_MESSAGE = invalidParamMessage;
        PHONE_VO_ERROR_MESSAGE = phoneVOErrorMessage;
        ADDRESS_VO_INVALID_POSTAL_CODE_MESSAGE = addressVOInvalidPostalCodeMessage;
        ADDRESS_VO_INVALID_STREET_MESSAGE = addressVOInvalidStreetMessage;
        IDENTIFICATION_DOCUMENT_VO_INVALID_MESSAGE = identificationDocumentVOInvalidMessage;
        EMAIL_VO_INVALID_MESSAGE = emailVOInvalidMessage;
    }


}
