package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.AntecedantType;
import com.gmhis_backk.dto.AntecedantTypeDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface AntecendantTypeService {
	
	Page<AntecedantType> findAllAntecedantType(Pageable pageable);
    
	  Page<AntecedantType> findAllAntecedantTypeByActiveAndName(String name,Boolean active, Pageable pageable);
	    
	  Page<AntecedantType> findAllAntecedantTypeByName(String name, Pageable pageable);
	  
	  List<AntecedantType> findAllAntecedantTypes();

	  void deleteAntecedantType(Integer id);
		
	  Optional<AntecedantType> getAntecedantTypeDetails(Long id); 
	  
	  AntecedantType addAntecedantType(AntecedantTypeDTO antecedantTypeDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  AntecedantType updateAntecedantType(Long id,AntecedantTypeDTO antecedantTypeDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;

}
