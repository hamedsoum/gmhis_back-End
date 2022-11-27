package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Prescription;
import com.gmhis_backk.dto.PrescriptionDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service @Transactional
public interface PrescriptionService {
	
	public Prescription save(PrescriptionDto prescriptionDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	public Prescription update(PrescriptionDto prescriptionDto, UUID id) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;

	public List<Prescription> findAll();

	public Page<Prescription> findAll(Pageable pageable);
	
	public Page <Prescription> findAllPatientPrescriptions(Long Patient, Pageable pageable);
	
	public Long findPrescriptionsNumber(Long patientId);

	public Optional<Prescription> findPrescriptionById(UUID id);
	
	public Prescription findPrescriptionByPrescriptionNumber(String prescriptionNumber);

}
