package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.ActGroup;
import com.gmhis_backk.dto.ActGroupDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface ActGroupService {
	  Page<ActGroup> findAllActGroup(Pageable pageable);
    
	  Page<ActGroup> findAllActGroupByActiveAndName(String name,Boolean active, Pageable pageable);
	    
	  Page<ActGroup> findAllActGroupByName(String name, Pageable pageable);
	  
	  List<ActGroup> findAllActGroups();

	  void deleteActGroup(Integer id);
		
	  Optional<ActGroup> getActGroupDetails(Long id); 
	  
	  ActGroup addAActGroup(ActGroupDto actGroupDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  ActGroup updateActGroup(Long id,ActGroupDto actGroupDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
}
