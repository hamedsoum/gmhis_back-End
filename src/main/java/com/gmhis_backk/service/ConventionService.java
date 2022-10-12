package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.Convention;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface ConventionService {
	 Page<Convention> findAllConvention(Pageable pageable);
	    
	  Page<Convention> findAllConventionByActiveAndName(String name,Boolean active, Pageable pageable);
	    
	  Page<Convention> findAllConventionByName(String name, Pageable pageable);
	  
	  List<Convention> findAllConventions();

	  void deleteConvention(Integer id);
		
	  Optional<Convention> getConventionDetails(Long id); 
	  
	  Convention addConvention(DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  Convention updateConvention(Long id,DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
}
