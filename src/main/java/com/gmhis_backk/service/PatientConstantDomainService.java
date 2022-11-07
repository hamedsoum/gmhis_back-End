package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.PatientConstantDomain;
import com.gmhis_backk.dto.PatientConstantDomainDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface PatientConstantDomainService {

	Page<PatientConstantDomain> findAllConstatDomain(Pageable pageable);
    
	  Page<PatientConstantDomain> findAllConstatDomainByActiveAndName(String name,Boolean active, Pageable pageable);
	    
	  Page<PatientConstantDomain> findAllConstatDomainByName(String name, Pageable pageable);
	  
	  List<PatientConstantDomain> findAllConstatDomains();
	  
		public List<PatientConstantDomain> findActivePatientConstantDomaines();


	  void deleteConstatDomain(Integer id);
		
	  Optional<PatientConstantDomain> getConstatDomainDetails(Long id); 
	  
	  PatientConstantDomain addConstatDomain(PatientConstantDomainDto patientConstantDomainDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  PatientConstantDomain updateConstantDomain(Long id,PatientConstantDomainDto patientConstantDomainDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
}
