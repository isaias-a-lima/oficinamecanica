package com.ikservices.oficinamecanica.commons.vo;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.constants.IKConstants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.slf4j.Logger;

import java.util.Objects;

@Getter
@EqualsAndHashCode
public class EmailVO {

    private static final Logger LOGGER = IKLoggerUtil.getLogger(EmailVO.class);

    private final String mailAddress;

    public EmailVO(String mailAddress) {
        if (Objects.nonNull(mailAddress) && !mailAddress.isEmpty() && !mailAddress.matches(IKConstants.EMAIL_PATTERN)) {
            LOGGER.error(String.format("ERRO: %s - %s", Constants.getEMAIL_VO_INVALID_MESSAGE(), mailAddress));
            throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), Constants.getEMAIL_VO_INVALID_MESSAGE()));
        }
        this.mailAddress = mailAddress;
    }
}
