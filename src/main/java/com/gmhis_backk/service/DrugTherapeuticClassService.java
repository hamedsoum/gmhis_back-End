package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.DrugTherapeuticClass;
import com.gmhis_backk.dto.DrugTherapeuticClassDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface DrugTherapeuticClassService {
	
	 Page<DrugTherapeuticClass> findAllDrugTherapeuticClass(Pageable pageable);
		
	 Page<DrugTherapeuticClass> findAllDrugTherapeuticClassByActiveAndName(String name,Boolean active, Pageable pageable);

	 Page<DrugTherapeuticClass> findAllDrugTherapeuticClassByName(String name, Pageable pageable);
	  
	  List<DrugTherapeuticClass> findAllDrugTherapeuticClass();
		
	  Optional<DrugTherapeuticClass> getDrugTherapeuticClassDetails(UUID id); 
	  
	  DrugTherapeuticClass addDrugTherapeuticClass(DrugTherapeuticClassDto drugTherapeuticClassDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  DrugTherapeuticClass updateDrugTherapeuticClass(UUID id,DrugTherapeuticClassDto drugTherapeuticClassDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;


}
