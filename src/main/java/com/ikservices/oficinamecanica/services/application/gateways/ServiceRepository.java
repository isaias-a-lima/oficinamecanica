package com.ikservices.oficinamecanica.services.application.gateways;

import java.util.Map;

import com.ikservices.oficinamecanica.services.domain.Service;

public interface ServiceRepository {
	Map<Long, Service> saveService(Service service);
	Map<Long, Service> updateService(Long id, Service service);
	Map<Long, Service> getService(Long id);
	Map<Long, Service> getServiceList(Long workshopId);
	void deleteService(Long id);
}
