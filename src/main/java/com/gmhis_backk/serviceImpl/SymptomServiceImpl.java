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
import com.gmhis_backk.domain.Symptom;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.SymptomRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.SymptomService;

@Service
public class SymptomServiceImpl implements SymptomService {
	
	@Autowired
	SymptomRepository symptomRepository;
	
	@Autowired
	UserRepository userRepository;
	

	@Override
	public Page<Symptom> findAllSymptom(Pageable pageable) {
		return symptomRepository.findAll(pageable);
	}

	@Override
	public Page<Symptom> findAllSymptomByActiveAndName(String name, Boolean active, Pageable pageable) {
		return symptomRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public Page<Symptom> findAllSymptomByName(String name, Pageable pageable) {
		return symptomRepository.findAllSymptomByName(name, pageable);
	}

	@Override
	public List<Symptom> findAllSymptom() {
		return symptomRepository.findAll();
	}

	@Override
	public Optional<Symptom> getSymptomDetails(Long id) {
		return symptomRepository.findById(id);
	}
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override @Transactional
	public Symptom addSymptom(DefaultNameAndActiveDto defaultNameAndActiveDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		Symptom symptomByName= symptomRepository.findByName(defaultNameAndActiveDto.getName());
		if(symptomByName!= null) {
			throw new ResourceNameAlreadyExistException("Le nom du symptom existe déjà ");  
		} 
		Symptom symptom = new Symptom();		
		BeanUtils.copyProperties(defaultNameAndActiveDto,symptom,"id");
		symptom.setCreatedAt(new Date());
		symptom.setCreatedBy(getCurrentUserId().getId());
		return symptomRepository.save(symptom);
	}

	@Override @Transactional
	public Symptom updateSymptom(Long id, DefaultNameAndActiveDto defaultNameAndActiveDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		Symptom updateSymptom = symptomRepository.findById(id).orElse(null);
		if (updateSymptom == null) {
			 throw new ResourceNotFoundByIdException("Aucun symptom trouvé pour l'identifiant");

		} else {
			Symptom pathologyByName = symptomRepository.findByName(defaultNameAndActiveDto.getName());
			if (pathologyByName != null) {
				if (pathologyByName.getId() != updateSymptom.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom du symptom existe déjà");

				}
			}

		}
		BeanUtils.copyProperties(defaultNameAndActiveDto, updateSymptom,"id");
		updateSymptom.setLastUpdatedAt(new Date());
		updateSymptom.setLastUpdatedBy(getCurrentUserId().getId());
		return symptomRepository.save(updateSymptom);
	}

	@Override
	public List<Symptom> findAllActive() {
		return symptomRepository.findActives();
	}

}
