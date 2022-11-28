package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.DrugPharmacologicalForm;
import com.gmhis_backk.dto.DrugPharmacologicFormDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public interface DrugPharmacologicalFormService {
	
	public DrugPharmacologicalForm saveForm(DrugPharmacologicFormDto drugPharmacologicFormDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	public DrugPharmacologicalForm updateForm(UUID id,DrugPharmacologicFormDto drugPharmacologicFormDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;


	public DrugPharmacologicalForm findFormByName(String name);
	
	public Optional<DrugPharmacologicalForm> findFormById(UUID id);

	public List<DrugPharmacologicalForm> findForms();
	
	public Page<DrugPharmacologicalForm> findForms(Pageable pageable);
	
	public Page<DrugPharmacologicalForm> findFormsContaining(String name,Pageable pageable);
	
	public List<DrugPharmacologicalForm> findActiveForms();
	
	public Page<DrugPharmacologicalForm> findByActive(String name, Boolean active, Pageable pageable);
}
