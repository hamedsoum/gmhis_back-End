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
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
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
	public Speciality addSpeciality(DefaultNameAndActiveDto defaultNameAndActiveDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		Speciality SpecialityByName = specialityRepository.findByName(defaultNameAndActiveDto.getName());
		if(SpecialityByName!=null) {
			throw new ResourceNameAlreadyExistException("La convention de la famille existe déjà ");  
		} 
		Speciality convention = new Speciality();		
		BeanUtils.copyProperties(defaultNameAndActiveDto,convention,"id");
		convention.setCreatedAt(new Date());
		convention.setCreatedBy(getCurrentUserId().getId());
		return specialityRepository.save(convention);
	}

	@Override @Transactional 
	public Speciality updateSpeciality(Long id, DefaultNameAndActiveDto defaultNameAndActiveDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		   Speciality updateSpeciality = specialityRepository.findById(id).orElse(null);
			
			if (updateSpeciality == null) {
				 throw new ResourceNotFoundByIdException("Aucune convention trouvé pour l'identifiant");
			} else {
				Speciality conventionByName = specialityRepository.findByName(defaultNameAndActiveDto.getName());
				if(conventionByName != null) {
					if(conventionByName.getId() != updateSpeciality.getId()) {
						throw new ResourceNameAlreadyExistException("Le nom de la convention existe déjà");
					}
				}
			}
			BeanUtils.copyProperties(defaultNameAndActiveDto, updateSpeciality,"id");
			updateSpeciality.setUpdatedAt(new Date());
			updateSpeciality.setUpdatedBy(getCurrentUserId().getId());
			return specialityRepository.save(updateSpeciality);
	}

}
