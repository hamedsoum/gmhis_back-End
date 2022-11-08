package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.UnitOfMeasure;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface UnitOfMeasureService {
	
	 Page<UnitOfMeasure> findAllUnitOfMeasure(Pageable pageable);
	
	 Page<UnitOfMeasure> findAllUnitOfMeasureByActiveAndName(String name,Boolean active, Pageable pageable);

	 Page<UnitOfMeasure> findAllUnitOfMeasureByName(String name, Pageable pageable);
	  
	  List<UnitOfMeasure> findAllUnitOfMeasures();

	  List<UnitOfMeasure> findAllActiveUnitOfMeasure();
	  
	  void deleteUnitOfMeasure(Integer id);
		
	  Optional<UnitOfMeasure> getUnitOfMeasureDetails(Long id); 
	  
	  UnitOfMeasure addUnitOfMeasure(DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  UnitOfMeasure updateUnitOfMeasure(Long id,DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;

}
