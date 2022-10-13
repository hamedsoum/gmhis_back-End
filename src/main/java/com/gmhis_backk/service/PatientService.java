package com.gmhis_backk.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public Patient save(PatientDTO patientDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException, EmailExistException;
	
	public Patient update(Long id,PatientDTO patientDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;


	public Patient findById(Long id);

	public List<Patient> findByFirstName(String s);

	public List<Patient> findByLastName(String s);

	public List<Patient> findByPatientExternalId(String s);

	public List<Patient> findByCellPhone(String s);

	public List<Patient> findByEmail(String s);

	public List<Patient> findByCnamNumber(String s);

	public List<Patient> findByIdCardNumber(String s);

	public List<Patient> findAll();

	public Page<Patient> findAll(Pageable pageable);

	public Page<Patient> findAllContaining(String firstName, String lastName, String patientExternalId,
			String cellPhone, String cnamNumber, String idCardNumber, Pageable pageable);

	public String getLastExternalId(int prefixLength);
	
	public Page<Patient> findByFullName(String firstName, String lastName, Pageable pageable);
	
	public Page<Patient> findByFirstName(String firstName, Pageable pageable);

	public Page<Patient> findByLastName(String lastName, Pageable pageable);

	public Page<Patient> findByPatientExternalId(String patientExternalId, Pageable pageable);

	public Page<Patient> findByCellPhone(String cellPhone, Pageable pageable);

	public Page<Patient> findByCnamNumber(String cnamNumber, Pageable pageable);

	public Page<Patient> findByIdCardNumber(String idCardNumber, Pageable pageable);
	
	
}
