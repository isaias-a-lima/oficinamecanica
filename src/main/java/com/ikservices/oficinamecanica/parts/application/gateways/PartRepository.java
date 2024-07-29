package com.ikservices.oficinamecanica.parts.application.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.parts.domain.Part;
import com.ikservices.oficinamecanica.parts.domain.PartId;

public interface PartRepository {
	Part savePart(Part part);
	Part updatePart(Part part);
	Part getPart(PartId partId);
	List<Part> getPartList(Long workshopId);
}
