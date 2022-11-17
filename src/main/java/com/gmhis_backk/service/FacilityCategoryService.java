package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.FaciityCategory;
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
public interface FacilityCategoryService  {

public FaciityCategory saveFaciityCategory(DefaultNameAndActiveDto f) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	public FaciityCategory updateFaciityCategory(DefaultNameAndActiveDto f, UUID Id)throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
;
	public FaciityCategory findFaciityCategoryByName(String facility);
	
	public Optional<FaciityCategory> findFaciityCategoryById(UUID id);

	public List<FaciityCategory> findFacilityCategory();
	
	public Page<FaciityCategory> findFacilityCategories(Pageable pageable);
	
	public Page<FaciityCategory> findFacilityCategoryContaining(String name,Pageable pageable);
	
	public List<FaciityCategory> findActiveFacilityCategory();
	
	public Page<FaciityCategory> findByActive(String namme, Boolean active, Pageable pageable);
}
