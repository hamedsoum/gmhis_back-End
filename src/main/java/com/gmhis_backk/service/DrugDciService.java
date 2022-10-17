package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.DrugDci;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface DrugDciService {
	
	 Page<DrugDci> findAllDrugDci(Pageable pageable);
		
	 Page<DrugDci> findAllDrugDciByActiveAndName(String name,Boolean active, Pageable pageable);

	 Page<DrugDci> findAllDrugDciByName(String name, Pageable pageable);
	  
	  List<DrugDci> findAllDrugDci();
		
	  Optional<DrugDci> getDrugDciDetails(Long id); 
	  
	  DrugDci addDrugDci(DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  DrugDci updateDrugDci(Long id,DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;

}
