package com.gmhis_backk.service;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.Act;
import com.gmhis_backk.domain.AdmissionHasAct;
import com.gmhis_backk.domain.Convention;
import com.gmhis_backk.domain.ConventionHasAct;
import com.gmhis_backk.dto.ActDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;


@Service
public interface ActService {

public List<Act> findActs();
	
	public Page<Act> findActs(Pageable pageable);
	
	public Page<Act> findActsContaining(String name,Pageable pageable);
	
	public List<Act> findActiveActs();
	
	public List<Act> findActListByName(String name);
	
	public Page<Act> findByActive(String name, Boolean active,Long category, Pageable pageable);
	
	public Page<Act> findActiveActsByCategory(String name, Long category, Pageable pageable);
	
	public List <Act> findActiveNamesAndIdsActsByCategory(Long category);

	
	public List<Act> findActiveActsByGroup(String name, Long group);
	
	public List<Act> findActiveActsByCriteria(String name,Long group, Long category);
	
	public ConventionHasAct findActByConventionAndAct(Convention convention, Act act);
	
	public List<AdmissionHasAct> findActsByBill(Long bill);
		
	public Optional<Act> findActById(Long id); 
	  
	 public Act addAct(ActDTO actDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	 public Act updateAct(Long id,ActDTO actDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
	 
	 public List<Act> findNamesAndIdsByMedicalAnalysisSpeciality();
}
