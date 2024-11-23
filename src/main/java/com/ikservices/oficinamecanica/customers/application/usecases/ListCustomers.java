package com.ikservices.oficinamecanica.customers.application.usecases;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.commons.vo.PhoneVO;
import com.ikservices.oficinamecanica.customers.application.SearchCriteria;
import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import org.slf4j.Logger;

import java.util.List;

public class ListCustomers {

    private final static Logger LOGGER = IKLoggerUtil.getLogger(ListCustomers.class);

    private final CustomerRepository repository;

    public ListCustomers(CustomerRepository repository) {
        this.repository = repository;
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
            throw ie;
        } catch (Exception e) {
            LOGGER.error(errorMessage, e);
            throw new IKException(new IKMessage("-1", IKMessageType.ERROR.getCode(), e.getMessage()), e);
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
                throw new IKException(new IKMessage("-1", IKMessageType.WARNING.getCode(), "Escolha um critério válido."));
        }
    }
}
