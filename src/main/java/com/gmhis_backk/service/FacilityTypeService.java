package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.FacilityType;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service 
@Transactional
public interface FacilityTypeService  {

public FacilityType saveFacilityType(DefaultNameAndActiveDto f) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	public FacilityType updateFacilityType(DefaultNameAndActiveDto f, UUID Id)throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
;
	public FacilityType findFacilityTypeByName(String facility);
	
	public Optional<FacilityType> findFacilityTypeById(UUID id);

	public List<FacilityType> findFacilitiesType();
	
	public Page<FacilityType> findFacilitiesType(Pageable pageable);
	
	public Page<FacilityType> findFacilitiesTypeContaining(String name,Pageable pageable);
	
	public List<FacilityType> findActiveFacilitiesType();
	
	public Page<FacilityType> findByActive(String namme, Boolean active, Pageable pageable);
}
