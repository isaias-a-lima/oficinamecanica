package com.ikservices.oficinamecanica.workshops.application.gateways;

import com.ikservices.oficinamecanica.workshops.domain.Workshop;
import org.hibernate.jdbc.Work;

import java.util.Map;

public interface WorkshopRepository {
    Workshop saveWorkshop(Workshop workshop);
    Workshop updateWorkshop(Workshop workshop);
    Workshop getWorkshop(Long id);
    Map<Long, Workshop> getWorkshopList(Long userId);
    void deleteWorkshop(Long id);
}
