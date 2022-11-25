package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.DrugDci;
import com.gmhis_backk.dto.DrugDciDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface DrugDciService {
	
	 Page<DrugDci> findAllDrugDci(Pageable pageable);
		
	 Page<DrugDci> findAllDrugDciByActiveAndName(String name,Boolean active, Pageable pageable);

	 Page<DrugDci> findAllDrugDciByName(String name, Pageable pageable);
	  
	  List<DrugDci> findAllDrugDci();
	  
	  List<DrugDci> findAllActiveDrugDci();

		
	  Optional<DrugDci> getDrugDciDetails(UUID id); 
	  
	  DrugDci addDrugDci(DrugDciDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  DrugDci updateDrugDci(UUID id,DrugDciDto defaultNameAndActiveDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;

}
