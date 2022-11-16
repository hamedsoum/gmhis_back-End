package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.Symptom;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface SymptomService {

	 Page<Symptom> findAllSymptom(Pageable pageable);
		
	 Page<Symptom> findAllSymptomByActiveAndName(String name,Boolean active, Pageable pageable);

	 Page<Symptom> findAllSymptomByName(String name, Pageable pageable);
	  
	  List<Symptom> findAllSymptom();
	  
	  List<Symptom> findAllActive();

		
	  Optional<Symptom> getSymptomDetails(Long id); 
	  
	  Symptom addSymptom(DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  Symptom updateSymptom(Long id,DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
}
