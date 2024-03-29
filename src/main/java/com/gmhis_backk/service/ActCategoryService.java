package com.gmhis_backk.service;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.ActCategory;
import com.gmhis_backk.dto.ActCategoryDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;


@Service
public interface ActCategoryService {

	  Page<ActCategory> findAllActCategory(Pageable pageable);
	  
	  List<ActCategory> findAllActive();
	    
	  Page<ActCategory> findAllActCategoryByActiveAndName(String name,Boolean active, Pageable pageable);
	    
	  Page<ActCategory> findAllActCategoryByName(String name, Pageable pageable);
	  
	  List<ActCategory> findAllActCategories();

	  void deleteActCategory(Integer id);
	  
	  Optional<ActCategory> findById(Long id); 
		
	  Optional<ActCategory> getActCategoryDetails(Long id); 
	  
	  ActCategory addActCategory(ActCategoryDto actCategoryDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  ActCategory updateActCategory(Long id,ActCategoryDto actCategoryDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
}
