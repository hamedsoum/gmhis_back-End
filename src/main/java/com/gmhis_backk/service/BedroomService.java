package com.gmhis_backk.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.Bedroom;
import com.gmhis_backk.dto.BedroomDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

/**
 * 
 * @author Mathurin
 *
 */
@Service
public interface BedroomService {
 
	Bedroom addBedroom(BedroomDto bedroomDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	Bedroom updateBedroom(Long id,BedroomDto bedroomDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
	
	Bedroom getDetail(Long id);
	
	Page<Bedroom> bedroomList(String libelle,Long StoreyId, Long type,Pageable pageable);
	
	void deleteBedroom(Long id) throws ResourceNotFoundByIdException;
	
	List<Bedroom> bedroomSimpleList();
}
