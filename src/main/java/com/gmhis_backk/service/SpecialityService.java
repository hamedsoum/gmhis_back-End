package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gmhis_backk.domain.Speciality;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

public interface SpecialityService {
	 Page<Speciality> findAllSpeciality(Pageable pageable);
	    
	  Page<Speciality> findAllSpecialityByActiveAndName(String name,Boolean active, Pageable pageable);
	    
	  Page<Speciality> findAllSpecialityByName(String name, Pageable pageable);
	  
	  List<Speciality> findAllSpecialitys();

	  void deleteSpeciality(Integer id);
		
	  Optional<Speciality> getSpecialityDetails(Long id); 
	  
	  Speciality addSpeciality(DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  Speciality updateSpeciality(Long id,DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
}


