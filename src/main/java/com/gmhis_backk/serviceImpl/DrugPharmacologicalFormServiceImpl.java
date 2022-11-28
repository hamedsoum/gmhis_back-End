package com.gmhis_backk.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.DrugPharmacologicalForm;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.DrugPharmacologicFormDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.DrugPharmacologicalFormRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.DrugPharmacologicalFormService;


@Service
public class DrugPharmacologicalFormServiceImpl implements DrugPharmacologicalFormService{

	
	@Autowired
	DrugPharmacologicalFormRepository drugPharmacologicalFormRepository;
	
	@Autowired
	UserRepository userRepository;
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	@Override
	public DrugPharmacologicalForm saveForm(DrugPharmacologicFormDto drugPharmacologicFormDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		DrugPharmacologicalForm drugPharmacologicalFormName = drugPharmacologicalFormRepository.findByName(drugPharmacologicFormDto.getName());
		if(drugPharmacologicalFormName!= null) {
			throw new ResourceNameAlreadyExistException("Le nom de la forme pharmacologique existe déjà ");  
		} 
		DrugPharmacologicalForm drugPharmacologicalForm = new DrugPharmacologicalForm();		
		BeanUtils.copyProperties(drugPharmacologicFormDto,drugPharmacologicalForm,"id");
		drugPharmacologicalForm.setCreatedAt(new Date());
		drugPharmacologicalForm.setCreatedBy(getCurrentUserId().getId());
		return drugPharmacologicalFormRepository.save(drugPharmacologicalForm);
	}

	@Override
	public DrugPharmacologicalForm updateForm(UUID id,DrugPharmacologicFormDto drugPharmacologicFormDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		DrugPharmacologicalForm updateDrugPharmacologicalForm = drugPharmacologicalFormRepository.findById(id).orElse(null);
		if (updateDrugPharmacologicalForm == null) {
			 throw new ResourceNotFoundByIdException("Aucune classe de therapie trouvée pour l'identifiant");

		} else {
			DrugPharmacologicalForm updateDrugPharmacologicalFormByName = drugPharmacologicalFormRepository.findByName(drugPharmacologicFormDto.getName());
			if (updateDrugPharmacologicalFormByName != null) {
				if (updateDrugPharmacologicalFormByName.getId() != updateDrugPharmacologicalFormByName.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom de la dci existe déjà");
				}
			
	      }
		}
		BeanUtils.copyProperties(drugPharmacologicFormDto, updateDrugPharmacologicalForm,"id");
		updateDrugPharmacologicalForm.setUpdatedAt(new Date());
		updateDrugPharmacologicalForm.setUpdatedBy(getCurrentUserId().getId());
		return drugPharmacologicalFormRepository.save(updateDrugPharmacologicalForm);
	}

	@Override
	public DrugPharmacologicalForm findFormByName(String name) {
		return drugPharmacologicalFormRepository.findByName(name);
	}

	@Override
	public Optional<DrugPharmacologicalForm> findFormById(UUID id) {
		return drugPharmacologicalFormRepository.findById(id);
	}

	@Override
	public List<DrugPharmacologicalForm> findForms() {
		return drugPharmacologicalFormRepository.findActiveForms();
	}

	@Override
	public Page<DrugPharmacologicalForm> findForms(Pageable pageable) {
		return drugPharmacologicalFormRepository.findAll(pageable);
	}

	@Override
	public Page<DrugPharmacologicalForm> findFormsContaining(String name, Pageable pageable) {
		return drugPharmacologicalFormRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public List<DrugPharmacologicalForm> findActiveForms() {
		return drugPharmacologicalFormRepository.findActiveForms();
	}

	@Override
	public Page<DrugPharmacologicalForm> findByActive(String name, Boolean active, Pageable pageable) {
		return drugPharmacologicalFormRepository.findByActive(name, active, pageable);
	}

}
