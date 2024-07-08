package com.ikservices.oficinamecanica.customers.application.usecases;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessages;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.infra.constants.CustomerConstants;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;
import org.slf4j.Logger;

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

    public List<Customer> execute(Long workshopId) {
        List<Customer> customerList = null;
        String loggerID = IKLoggerUtil.getLoggerID();

        try {
            customerList = repository.getCustomerList(workshopId);
        } catch (Exception e) {
            String errorMessage = String.format("ERROR_ID: %s, workshopId: %d", loggerID, workshopId);
            LOGGER.error(errorMessage, e);

            IKMessage ikMessage = ikMessages.getPropertyMessage(CustomerConstants.UNEXPECTED_ERROR_MESSAGE_KEY);

            throw new IKException(ikMessage.getCode(), ikMessage.getIKMessageType(), ikMessage.getMessage());
        }
        return customerList;
    }
}
