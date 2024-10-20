package com.ikservices.oficinamecanica.customers.application.usecases;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKMessages;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.commons.vo.PhoneVO;
import com.ikservices.oficinamecanica.customers.application.SearchCriteria;
import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.infra.constants.CustomerConstants;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class ListCustomers {

    private final static Logger LOGGER = IKLoggerUtil.getLogger(ListCustomers.class);

    private final CustomerRepository repository;

    private final IKMessages ikMessages;

    public ListCustomers(CustomerRepository repository, IKMessages ikMessages) {
        this.repository = repository;
        this.ikMessages = ikMessages;
    }

    public List<Customer> execute(Long workshopId, SearchCriteria criteria, String search) {
        List<Customer> customerList = null;
        String loggerID = IKLoggerUtil.getLoggerID();
        String errorMessage = String.format("ERROR_ID: %s, workshopId: %d, criteria: %s, search: %s", loggerID, workshopId, criteria, search);

        try {
            if (criteria == SearchCriteria.PLATE) {
                customerList = repository.getCustomerByVehicles(workshopId, search);
            } else {
                customerList = repository.getCustomerList(workshopId, criteria, validSearch(criteria, search));
            }
        } catch (IKException ie) {
            LOGGER.error(errorMessage, ie);
            throw new IKException(ie.getCode(), ie.getIKMessageType(), ie.getMessage());
        } catch (Exception e) {
            LOGGER.error(errorMessage, e);
            IKMessage ikMessage = ikMessages.getPropertyMessage(CustomerConstants.UNEXPECTED_ERROR_MESSAGE_KEY);
            throw new IKException(ikMessage.getCode(), ikMessage.getIKMessageType(), ikMessage.getMessage());
        }
        return customerList;
    }

    private String validSearch(SearchCriteria criteria, String search) {
        switch (criteria) {
            case DOCUMENT:
                IdentificationDocumentVO doc = new IdentificationDocumentVO(search);
                return doc.getDocument();
            case NAME:
                return search;
            case PHONE:
                return PhoneVO.parsePhoneVO(search).getFullPhone();
            default:
                throw new IKException(HttpStatus.BAD_REQUEST.value(), IKMessageType.WARNING, "Escolha um critério válido.");
        }
    }
}
