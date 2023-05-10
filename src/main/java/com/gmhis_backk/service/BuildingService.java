package com.gmhis_backk.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.Building;
import com.gmhis_backk.dto.BuildingDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

/**
 * 
 * @author Mathurin
 *
 */
@Service
public interface BuildingService {
 
	Building addBuilding(BuildingDto buildingDto) throws ResourceNameAlreadyExistException;
	
	Building updateBuilding(Long id,BuildingDto buildingDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
	
	Building getDetail(Long id);
	
	Page<Building> buildingList(String libelle,Pageable pageable);
	
	void deleteBuilding(Long id) throws ResourceNotFoundByIdException;
	
	List<Building> buildingSimpleList();
}
