package com.ikservices.oficinamecanica.workshops.infra.persistense;

import com.ikservices.oficinamecanica.users.infra.UserConverter;
import com.ikservices.oficinamecanica.workshops.domain.Workshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorkshopConverter {

    private final UserConverter userConverter;

    public WorkshopConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    public Workshop parseWorkshop(WorkshopEntity entity) {
        return new Workshop(entity.getDocId(), userConverter.toModel(entity.getUser()), entity.getName(), entity.getImage());
    }

    public List<Workshop> parseWorkshopList(List<WorkshopEntity> entityList) {
        List<Workshop> workshopList = new ArrayList<>();
        if (Objects.nonNull(entityList) && !entityList.isEmpty()) {
            for (WorkshopEntity entity : entityList) {
                workshopList.add(parseWorkshop(entity));
            }
        }
        return workshopList;
    }

}
