package com.gmhis_backk.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Speciality;
import com.gmhis_backk.dto.SpecialityDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.SpecialityRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.SpecialityService;

@Service
public class SpecialityServiceImpl implements SpecialityService {
	@Autowired
	SpecialityRepository specialityRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Page<Speciality> findAllSpeciality(Pageable pageable) {
		return specialityRepository.findAll(pageable);
	}
	
	@Override
	public Optional<Speciality> findById(Long id) {
		return specialityRepository.findById(id);
	}

	@Override
	public Page<Speciality> findAllSpecialityByActiveAndName(String name, Boolean active, Pageable pageable) {
		return specialityRepository.findByActive(name, name, pageable);
	}

	@Override
	public Page<Speciality> findAllSpecialityByName(String name, Pageable pageable) {
		return specialityRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public List<Speciality> findAllSpecialitys() {
		return specialityRepository.findActiveSpecialities();
	}

	@Override
	public void deleteSpeciality(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Speciality> getSpecialityDetails(Long id) {
		return specialityRepository.findById(id);
	}
	
	protected com.gmhis_backk.domain.User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override @Transactional 
	public Speciality addSpeciality(SpecialityDto specialityDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		Speciality SpecialityByName = specialityRepository.findByName(specialityDto.getName());
		if(SpecialityByName!=null) {
			throw new ResourceNameAlreadyExistException("La spécialité de la famille existe déjà ");  
		} 
		Speciality speciality = new Speciality();		
		BeanUtils.copyProperties(specialityDto,speciality,"id");
		speciality.setCreatedAt(new Date());
		speciality.setCreatedBy(getCurrentUserId().getId());
		return specialityRepository.save(speciality);
	}

	@Override @Transactional 
	public Speciality updateSpeciality(Long id, SpecialityDto specialityDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		   Speciality updateSpeciality = specialityRepository.findById(id).orElse(null);
			
			if (updateSpeciality == null) {
				 throw new ResourceNotFoundByIdException("Aucune spécialité trouvée pour l'identifiant");
			} else {
				Speciality conventionByName = specialityRepository.findByName(specialityDto.getName());
				if(conventionByName != null) {
					if(conventionByName.getId() != updateSpeciality.getId()) {
						throw new ResourceNameAlreadyExistException("Le nom de la spécialité existe déjà");
					}
				}
			}
			BeanUtils.copyProperties(specialityDto, updateSpeciality,"id");
			updateSpeciality.setUpdatedAt(new Date());
			updateSpeciality.setUpdatedBy(getCurrentUserId().getId());
			return specialityRepository.save(updateSpeciality);
	}

}
