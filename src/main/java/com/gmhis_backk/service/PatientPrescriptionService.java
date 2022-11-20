package com.gmhis_backk.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gmhis_backk.domain.PatientPrescription;
import com.gmhis_backk.dto.PatientPrescriptionDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public interface PatientPrescriptionService {

    public PatientPrescription savePatientPrescription(PatientPrescriptionDTO pdto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;

    public PatientPrescription updatePatientPrescription(PatientPrescriptionDTO pdto, UUID Id) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;;

	public PatientPrescription findPatientPresciptionByName(String facility);

	public Optional<PatientPrescription> findPatientPrescriptionById(UUID id);

	public Page<PatientPrescription> findAllPatientPrescriptions(Long patient, Pageable pageable);
	
	public Page<PatientPrescription> findAllPrescriptions(String firstName, String lastName, String patientExternalId,
			String cellPhone, String cnamNumber, String idCardNumber, String state, Pageable pageable);
}
