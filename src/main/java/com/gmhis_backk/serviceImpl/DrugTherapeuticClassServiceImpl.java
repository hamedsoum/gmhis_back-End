package com.gmhis_backk.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.DrugTherapeuticClass;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.DrugTherapeuticRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.DrugTherapeuticClassService;

@Service
public class DrugTherapeuticClassServiceImpl implements DrugTherapeuticClassService {
	
	@Autowired
	DrugTherapeuticRepository drugTherapeuticRepository ;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public Page<DrugTherapeuticClass> findAllDrugTherapeuticClass(Pageable pageable) {
		return drugTherapeuticRepository.findAll(pageable);
	}

	@Override
	public Page<DrugTherapeuticClass> findAllDrugTherapeuticClassByActiveAndName(String name, Boolean active,
			Pageable pageable) {
		return drugTherapeuticRepository.findAllAllDrugDciByActiveAndName(name, active, pageable);
	}

	@Override
	public Page<DrugTherapeuticClass> findAllDrugTherapeuticClassByName(String name, Pageable pageable) {
		return drugTherapeuticRepository.findAllDrugTherapeuticClassByName(name, pageable);
	}

	@Override
	public List<DrugTherapeuticClass> findAllDrugTherapeuticClass() {
		return drugTherapeuticRepository.findAllDrugTherapeuticClassSimpleList();
	}

	@Override
	public Optional<DrugTherapeuticClass> getDrugTherapeuticClassDetails(Long id) {
		return drugTherapeuticRepository.findById(id);
	}
	
	protected com.gmhis_backk.domain.User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override @Transactional
	public DrugTherapeuticClass addDrugTherapeuticClass(DefaultNameAndActiveDto defaultNameAndActiveDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		DrugTherapeuticClass drugTherapeuticClassByName = drugTherapeuticRepository.findByName(defaultNameAndActiveDto.getName());
		if(drugTherapeuticClassByName!= null) {
			throw new ResourceNameAlreadyExistException("Le nom de la classe existe déjà ");  
		} 
		DrugTherapeuticClass drugTherapeuticClass = new DrugTherapeuticClass();		
		BeanUtils.copyProperties(defaultNameAndActiveDto,drugTherapeuticClass,"id");
		drugTherapeuticClass.setCreatedAt(new Date());
		drugTherapeuticClass.setCreatedBy(getCurrentUserId().getId());
		return drugTherapeuticRepository.save(drugTherapeuticClass);
	}

	@Override @Transactional
	public DrugTherapeuticClass updateDrugTherapeuticClass(Long id, DefaultNameAndActiveDto defaultNameAndActiveDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		DrugTherapeuticClass updateDrugTherapeuticClass = drugTherapeuticRepository.findById(id).orElse(null);
		if (updateDrugTherapeuticClass == null) {
			 throw new ResourceNotFoundByIdException("Aucune classe de therapie trouvée pour l'identifiant");

		} else {
			DrugTherapeuticClass updateDrugTherapeuticClassByName = drugTherapeuticRepository.findByName(defaultNameAndActiveDto.getName());
			if (updateDrugTherapeuticClassByName != null) {
				if (updateDrugTherapeuticClassByName.getId() != updateDrugTherapeuticClassByName.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom de la dci existe déjà");
				}
			}

		}
		BeanUtils.copyProperties(defaultNameAndActiveDto, updateDrugTherapeuticClass,"id");
		updateDrugTherapeuticClass.setUpdatedAt(new Date());
		updateDrugTherapeuticClass.setUpdatedBy(getCurrentUserId().getId());
		return drugTherapeuticRepository.save(updateDrugTherapeuticClass);
	}
 
}
