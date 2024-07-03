package com.ikservices.oficinamecanica.workshops.application.usecases;

import com.ikservices.oficinamecanica.commons.response.IKMessages;
import com.ikservices.oficinamecanica.workshops.application.gateways.WorkshopRepository;
import com.ikservices.oficinamecanica.workshops.domain.Workshop;

import java.util.Map;

public class ListWorkshop {

    private final WorkshopRepository repository;
    private IKMessages messages;

    public ListWorkshop(WorkshopRepository repository, IKMessages messages) {
        this.repository = repository;
        this.messages = messages;
    }

    public Map<Long, Workshop> execute(Long userId) {
        return repository.getWorkshopList(userId);
    }
}
