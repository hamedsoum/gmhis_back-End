package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.PatientConstantType;
import com.gmhis_backk.dto.PatientConstantTypeDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public interface PatientConstantTypeService {

	public PatientConstantType save(PatientConstantTypeDTO pcDto)  throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	public PatientConstantType update(Long id , PatientConstantTypeDTO pcDto)  throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;

	public PatientConstantType findByName(String constantType);

	public Optional<PatientConstantType> findById(Long id);

	public List<PatientConstantType> findAll();

	public Page<PatientConstantType> findAll(Pageable pageable);

	public Page<PatientConstantType> findPatientConstantTypesContaining(String name, Pageable pageable);

	public List<PatientConstantType> findActivePatientConstantTypes();
	
	public List<PatientConstantType> findActivePatientConstantTypesByDomain(Long domId);

	public Page<PatientConstantType> findByActive(String name, Boolean active, Pageable pageable);
	
	public Page<PatientConstantType> findByDomain(Long domain, String name, Pageable pageable);

	public Page<PatientConstantType> findByDomainAndActive(Long domain, String name, Boolean active, Pageable pageable);

}
