package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.MedicalAnalysisSpecilaity;
import com.gmhis_backk.domain.Pathology;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface MedicalAnalysisSpecilaityService {
	
	 Page<MedicalAnalysisSpecilaity> findAllMedicalAnalysisSpecilaity(Pageable pageable);
		
	 Page<MedicalAnalysisSpecilaity> findAllMedicalAnalysisSpecilaityByActiveAndName(String name,Boolean active, Pageable pageable);

	 Page<MedicalAnalysisSpecilaity> findAllMedicalAnalysisSpecilaityByName(String name, Pageable pageable);
	  
	  List<MedicalAnalysisSpecilaity> findAllMedicalAnalysisSpecilaity();
	  
	  List<MedicalAnalysisSpecilaity> findAllActive();
		
	  Optional<MedicalAnalysisSpecilaity> getMedicalAnalysisSpecilaityDetails(UUID id); 
	  
	  MedicalAnalysisSpecilaity addMedicalAnalysisSpecilaity(DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  MedicalAnalysisSpecilaity updateMedicalAnalysisSpecilaity(UUID id,DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
}
