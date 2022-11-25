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
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.DrugDci;
import com.gmhis_backk.dto.DrugDciDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.DrugDciRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.DrugDciService;

@Service
public class DrugDciServiceImpl implements DrugDciService {
	
	@Autowired
	DrugDciRepository drugRepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	

	@Override
	public Page<DrugDci> findAllDrugDci(Pageable pageable) {
		return drugRepository.findAll(pageable);
	}

	@Override
	public Page<DrugDci> findAllDrugDciByActiveAndName(String name, Boolean active, Pageable pageable) {
		// TODO Auto-generated method stub
		return drugRepository.findAllAllDrugDciByActiveAndName(name, active, pageable);
	}

	@Override
	public Page<DrugDci> findAllDrugDciByName(String name, Pageable pageable) {
		return drugRepository.findAllDrugDciByName(name, pageable);
	}

	@Override
	public List<DrugDci> findAllDrugDci() {
		// TODO Auto-generated method stub
		return drugRepository.findAllDrugDciSimpleList();
	}

	@Override
	public Optional<DrugDci> getDrugDciDetails(UUID id) {
		return drugRepository.findById(id);
	}
	
	protected com.gmhis_backk.domain.User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override @Transactional
	public DrugDci addDrugDci(DrugDciDto drugDciDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		DrugDci drugDciByName = drugRepository.findByName(drugDciDto.getName());
		if(drugDciByName!= null) {
			throw new ResourceNameAlreadyExistException("Le nom du DCI existe déjà ");  
		} 
		DrugDci drugDci = new DrugDci();		
		BeanUtils.copyProperties(drugDciDto,drugDci,"id");
		drugDci.setCreatedAt(new Date());
		drugDci.setCreatedBy(getCurrentUserId().getId());
		return drugRepository.save(drugDci);
	}

	@Override @Transactional
	public DrugDci updateDrugDci(UUID id, DrugDciDto drugDciDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		DrugDci updateDrugDci = drugRepository.findById(id).orElse(null);
		if (updateDrugDci == null) {
			 throw new ResourceNotFoundByIdException("Aucun DCI trouvé pour l'identifiant");

		} else {
			DrugDci drugDciByName = drugRepository.findByName(drugDciDto.getName());
			if (drugDciByName != null) {
				if (drugDciByName.getId() != updateDrugDci.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom de la dci existe déjà");
				}
			}

		}
		BeanUtils.copyProperties(drugDciDto, updateDrugDci,"id");
		updateDrugDci.setUpdatedAt(new Date());
		updateDrugDci.setUpdatedBy(getCurrentUserId().getId());
		return drugRepository.save(updateDrugDci);
	}

	@Override
	public List<DrugDci> findAllActiveDrugDci() {
		return drugRepository.findAllActive();
	}

}
