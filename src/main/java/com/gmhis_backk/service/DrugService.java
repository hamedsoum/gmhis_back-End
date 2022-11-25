package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Drug;
import com.gmhis_backk.dto.DrugDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service 
@Transactional
public interface DrugService {

public Drug saveDrug(DrugDto dDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	public Drug updateDrug(DrugDto dDto, UUID Id)throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
;
	public Drug findDrugByName(String drug);
	
	public Optional<Drug> findDrugById(UUID id);

	
	public Page<Drug> findDrugs(Pageable pageable);
	
	public Page<Drug> findDrugsContaining(String name,Pageable pageable);
	
	public List<Drug> findActiveDrugsType();
	
	public Page<Drug> findByActive(String namme, Boolean active, Pageable pageable);
	
	public Page<Drug> finddrugByDrugDci(UUID drugDci, Pageable pageable);
	
	public Page<Drug> finddrugByActiveAndNameAndDrugDci(String namme, Boolean active, UUID drugDci, Pageable pageable);
}
