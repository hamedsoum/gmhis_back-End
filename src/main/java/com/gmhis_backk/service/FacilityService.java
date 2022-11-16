package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Facility;
import com.gmhis_backk.dto.FacilityDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;



/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service 
@Transactional
public interface FacilityService {

	public Facility saveFacility(FacilityDTO f) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	public Facility updateFacility(FacilityDTO f, UUID Id)throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
;
	public Facility findFacilityByName(String facility);
	
	public Optional<Facility> findFacilityById(UUID id);

	public List<Facility> findFacilities();
	
	public Page<Facility> findFacilities(Pageable pageable);
	
	public Page<Facility> findFacilitiesContaining(String name,Pageable pageable);
	
	public List<Facility> findActiveFacilities();
	
	public Page<Facility> findByActive(String namme, Boolean active, Pageable pageable);
}
