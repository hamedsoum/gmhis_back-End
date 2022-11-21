package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.PrescriptionItem;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service @Transactional
public interface PrescriptionItemService {

	public PrescriptionItem savePrescriptionItem(PrescriptionItem pItem) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	public List<PrescriptionItem> findPrescriptionItemsByPrescription(UUID prescriptionId);
	
	public Optional<PrescriptionItem> findPrescriptionItemById(UUID id);


}
