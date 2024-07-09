package com.ikservices.oficinamecanica.services.application.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.services.domain.Service;

public interface ServiceRepository {
	Service saveService(Service service);
	Service updateService(Long id, Service service);
	Service getService(Long id);
	List<Service> getServiceList(Long workshopId);
	void deleteService(Long id);
}
