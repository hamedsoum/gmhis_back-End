package com.gmhis_backk.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.BedroomType;
import com.gmhis_backk.dto.BedroomTypeDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

/**
 * 
 * @author Mathurin
 *
 */
@Service
public interface BedroomTypeService {
 
	BedroomType addBedroomType(BedroomTypeDto bedroomTypeDto) throws ResourceNameAlreadyExistException;
	
	BedroomType updateBedroomType(Long id,BedroomTypeDto bedroomTypeDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
	
	BedroomType getDetail(Long id);
	
	Page<BedroomType> bedroomTypeList(String libelle,Pageable pageable);
	
	void deleteBedroomType(Long id) throws ResourceNotFoundByIdException;
	
	List<BedroomType> bedroomTypeSimpleList();
}
