package com.gmhis_backk.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
/**
 * 
 * @author Mathurin
 *
 */

import com.gmhis_backk.domain.Storey;
import com.gmhis_backk.dto.StoreyDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
@Service
public interface StoreyService {

	Storey addStorey(StoreyDto storeyDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
	
	Storey updateStorey(Long id,StoreyDto storeyDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
	
	List<Storey>findSimpleList();
	
	Storey getDetail(Long id);
	
	Page<Storey> storeyList(String libelle, Long buildingId,Pageable pageable);
	
	void deleteStorey(Long id) throws ResourceNotFoundByIdException;
}
