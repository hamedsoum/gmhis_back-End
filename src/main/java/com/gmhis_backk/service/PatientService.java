package com.gmhis_backk.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Examination;
import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.dto.PatientDTO;
import com.gmhis_backk.exception.domain.EmailExistException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public interface PatientService {
	public Page<Patient> findByIdPatientNumber(String patientNumber, Pageable pageable);

	public Page<Patient> findByIdCardNumber(String idCardNumber, Pageable pageable);

	public Patient save(PatientDTO patientDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException, EmailExistException;
	
	public Patient update(Long id,PatientDTO patientDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;

	public Patient findById(Long id);

	public List<Patient> findAll();

	public Page<Patient> findAll(Pageable pageable);

	public String getLastExternalId(int prefixLength);
	
	public Page<Patient> findByFullName(String firstName, String lastName,String cellPhone1,String correspondant,String emergencyContact,String patientExternalId, Pageable pageable);

	public Examination findLastAdmission(Long id);
	
}
