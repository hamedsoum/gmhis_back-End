package com.gmhis_backk.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.Act;
import com.gmhis_backk.domain.ActCategory;
import com.gmhis_backk.dto.ActCategoryDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;


@Service
public interface ActService {

public List<Act> findActs();
	
	public Page<Act> findActs(Pageable pageable);
	
	public Page<Act> findActsContaining(String name,Pageable pageable);
	
	public List<Act> findActiveActs();
	
	public List<Act> findActListByName(String name);
	
	public Page<Act> findByActive(String name, String active, Pageable pageable);
	
	public List<Act> findActiveActsByCategory(String name, Long category);
	
	public List<Act> findActiveActsByGroup(String name, Long group);
	
	public List<Act> findActiveActsByCriteria(String name,Long group, Long category);
	
//	public ConventionHasAct findActByConventionAndAct(Convention convention, Act act);
	
//	public List<AdmissionHasAct> findActsByBill(Long bill);
		
	  ActCategory getActCategoryDetails(Integer id); 
	  
	  ActCategory addActCategory(ActCategoryDto actCategoryDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  ActCategory updateActCategory(Long id,ActCategoryDto actCategoryDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
}
