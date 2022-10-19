package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.DrugPharmacologicalForm;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;


/**
 * 
 * @author Adjara
 *
 */
@Service
@Transactional
public interface DrugPharmacologicalFormService {
	
	public DrugPharmacologicalForm saveForm(DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	public DrugPharmacologicalForm updateForm(DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;


	public DrugPharmacologicalForm findFormByName(String name);
	
	public Optional<DrugPharmacologicalForm> findFormById(Long id);

	public List<DrugPharmacologicalForm> findForms();
	
	public Page<DrugPharmacologicalForm> findForms(Pageable pageable);
	
	public Page<DrugPharmacologicalForm> findFormsContaining(String name,Pageable pageable);
	
	public List<DrugPharmacologicalForm> findActiveForms();
	
	public Page<DrugPharmacologicalForm> findByActive(String name, String active, Pageable pageable);
}
