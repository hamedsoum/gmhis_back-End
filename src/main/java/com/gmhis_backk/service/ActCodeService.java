package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.ActCode;
import com.gmhis_backk.domain.Convention;
import com.gmhis_backk.domain.ConventionHasActCode;
import com.gmhis_backk.dto.ActCodeDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface ActCodeService {
	
	Page<ActCode> findAllActCode(Pageable pageable);
    
	  Page<ActCode> findAllActCodeByActiveAndName(String name,Boolean active, Pageable pageable);
	    
		public ConventionHasActCode findActCodeByConventionAndAct(Convention convention, ActCode actCode);

	  
	  Page<ActCode> findAllActCodeByName(String name, Pageable pageable);
	  
	  List<ActCode> findAllActCodes();
	  
		List<ActCode> findAllActive();


	  void deleteActCode(Integer id);
		
	  Optional<ActCode> getActCodeDetails(Long id); 
	  
	  ActCode addActCode(ActCodeDto actCodeDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	  ActCode updateActCode(Long id,ActCodeDto actCodeDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
}


