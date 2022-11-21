package com.gmhis_backk.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.PrescriptionItem;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.PrescriptionItemRepository;
import com.gmhis_backk.service.PrescriptionItemService;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
public class PrescriptionItemServiceImpl implements PrescriptionItemService {

	@Autowired
	private PrescriptionItemRepository prescriptionItemRepository;
	
	@Override
	public PrescriptionItem savePrescriptionItem(PrescriptionItem pItem)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		return prescriptionItemRepository.save(pItem);
	}

	@Override
	public List<PrescriptionItem> findPrescriptionItemsByPrescription(UUID prescriptionId) {
		// TODO Auto-generated method stub
		return prescriptionItemRepository.findPrescritionItemByPrescription(prescriptionId);
	}

	@Override
	public Optional<PrescriptionItem> findPrescriptionItemById(UUID id) {
		return prescriptionItemRepository.findById(id);
	}
 
}
