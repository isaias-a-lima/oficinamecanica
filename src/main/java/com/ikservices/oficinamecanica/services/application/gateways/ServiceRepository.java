package com.ikservices.oficinamecanica.services.application.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.services.domain.Service;
import com.ikservices.oficinamecanica.services.domain.ServiceId;

public interface ServiceRepository {
	Service saveService(Service service);
	Service updateService(Service service);
	Service getService(ServiceId id);
	List<Service> getServiceList(Long workshopId);
	void deleteService(Long id);
}
