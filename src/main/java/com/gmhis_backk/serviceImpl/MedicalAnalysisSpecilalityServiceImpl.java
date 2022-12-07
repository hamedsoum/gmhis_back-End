package com.gmhis_backk.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.MedicalAnalysisSpecilaity;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.MedicalAnalysisRepository;
import com.gmhis_backk.service.MedicalAnalysisSpecilaityService;

@Service @Transactional
public class MedicalAnalysisSpecilalityServiceImpl implements MedicalAnalysisSpecilaityService {

	@Autowired
	MedicalAnalysisRepository medicalAnalysisSpecialityRepository;
	
	@Override
	public Page<MedicalAnalysisSpecilaity> findAllMedicalAnalysisSpecilaity(Pageable pageable) {
		return medicalAnalysisSpecialityRepository.findAll(pageable);
	}

	@Override
	public Page<MedicalAnalysisSpecilaity> findAllMedicalAnalysisSpecilaityByActiveAndName(String name, Boolean active,
			Pageable pageable) {
		return medicalAnalysisSpecialityRepository.findMedicalAnalysisSpecialityByNameAndActiceAndDci(name, active, pageable);
	}

	@Override
	public Page<MedicalAnalysisSpecilaity> findAllMedicalAnalysisSpecilaityByName(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MedicalAnalysisSpecilaity> findAllMedicalAnalysisSpecilaity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MedicalAnalysisSpecilaity> findAllActive() {
		return medicalAnalysisSpecialityRepository.findAllActive();
	}

	@Override
	public Optional<MedicalAnalysisSpecilaity> getMedicalAnalysisSpecilaityDetails(UUID id) {
		// TODO Auto-generated method stub
		return medicalAnalysisSpecialityRepository.findById(id);
	}

	@Override
	public MedicalAnalysisSpecilaity addMedicalAnalysisSpecilaity(DefaultNameAndActiveDto defaultNameAndActiveDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		MedicalAnalysisSpecilaity medicalAnalaysisSpecialityName = medicalAnalysisSpecialityRepository.findMedicalAnalysisSpecilaityByName(defaultNameAndActiveDto.getName());
		if (medicalAnalaysisSpecialityName != null) {
			throw new ResourceNameAlreadyExistException("la specialite existe déjà");
		}
		MedicalAnalysisSpecilaity medicalAnalysisSpecilaity = new MedicalAnalysisSpecilaity();
		BeanUtils.copyProperties(defaultNameAndActiveDto,medicalAnalysisSpecilaity,"id");
		
		
		return medicalAnalysisSpecialityRepository.save(medicalAnalysisSpecilaity);
	}

	@Override
	public MedicalAnalysisSpecilaity updateMedicalAnalysisSpecilaity(UUID id, DefaultNameAndActiveDto defaultNameAndActiveDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		// TODO Auto-generated method stub
		return null;
	}
 
}
