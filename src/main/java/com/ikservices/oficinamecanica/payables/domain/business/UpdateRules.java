package com.ikservices.oficinamecanica.payables.domain.business;

import com.ikservices.oficinamecanica.commons.constants.IKConstants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.interfaces.IKBusiness;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.payables.domain.Payable;

import java.time.LocalDate;
import java.util.Objects;

public class UpdateRules implements IKBusiness<Payable> {

    public static final Integer CATEGORY_ID = 3;

    @Override
    public void execute(Payable object) {
        if(Objects.isNull(object.getCreationDate())) {
            throw IKException.build(IKConstants.IK_HTTP_EXPECTATION_FAILED, IKMessageType.WARNING, "O campo Data de criação deve ser preenchido");
        }
        if(Objects.isNull(object.getDescription())) {
            throw IKException.build(IKConstants.IK_HTTP_EXPECTATION_FAILED, IKMessageType.WARNING, "O campo Descrição deve ser preenchido");
        }
        if(Objects.isNull(object.getDueDate())) {
            throw IKException.build(IKConstants.IK_HTTP_EXPECTATION_FAILED, IKMessageType.WARNING, "O campo Data de Vencimento deve ser preenchido");
        }
        if(Objects.isNull(object.getPayValue())) {
            throw IKException.build(IKConstants.IK_HTTP_EXPECTATION_FAILED, IKMessageType.WARNING, "O campo Valor deve ser preenchido");
        }
        if(Objects.isNull(object.getCategory()) || Objects.isNull(object.getCategory().getId())) {
            throw IKException.build(IKConstants.IK_HTTP_EXPECTATION_FAILED, IKMessageType.WARNING, "O campo Categoria deve ser preenchido");
        }
        if(object.getCategory().getId().getCategoryId().equals(CATEGORY_ID) && Objects.isNull(object.getSupplierId())) {
            throw IKException.build(IKConstants.IK_HTTP_EXPECTATION_FAILED, IKMessageType.WARNING, "O campo Fornecedor deve ser preenchido");
        }

        object.setSupplierId(!object.getCategory().getId().getCategoryId().equals(3) ? null : object.getSupplierId());

        if(Objects.nonNull(object.getPayDate()) && object.getPayDate().isBefore(LocalDate.now())) {
            throw IKException.build(IKConstants.IK_HTTP_EXPECTATION_FAILED, IKMessageType.WARNING, "Esta conta não pode ser alterada porque já foi paga");
        }
    }
}
