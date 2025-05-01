package com.ikservices.oficinamecanica.categories.domain;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;

public enum CategoryTypeEnum {
	INPUT,
	OUTPUT;

	public static CategoryTypeEnum findByIndex(int index) {
		for (CategoryTypeEnum value : CategoryTypeEnum.values()) {
			if (value.ordinal() == index) {
				return value;
			}
		}
		throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), Constants.getINVALID_PARAM_MESSAGE()));
	}
}
