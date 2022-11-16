package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.Pathology;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface PathologyService {

	 Page<Pathology> findAllPathology(Pageable pageable);
		
	 Page<Pathology> findAllPathologyByActiveAndName(String name,Boolean active, Pageable pageable);

	 Page<Pathology> findAllPathologyByName(String name, Pageable pageable);
	  
	  List<Pathology> findAllPathology();
	  
	  List<Pathology> findAllActive();
		
	  Optional<Pathology> getPathologyDetails(Long id); 
	  
	  Pathology addPathology(DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  Pathology updatePathology(Long id,DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
}
