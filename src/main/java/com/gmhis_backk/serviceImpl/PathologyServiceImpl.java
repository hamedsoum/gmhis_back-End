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
import com.gmhis_backk.domain.Pathology;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.PathologyRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.PathologyService;

@Service
public class PathologyServiceImpl implements PathologyService {
	
	@Autowired
	PathologyRepository pathologyRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Page<Pathology> findAllPathology(Pageable pageable) {
		return pathologyRepository.findAll(pageable);
	}

	@Override
	public Page<Pathology> findAllPathologyByActiveAndName(String name, Boolean active, Pageable pageable) {
		return pathologyRepository.findAllAllPathologyByActiveAndName(name, active, pageable);
	}

	@Override
	public Page<Pathology> findAllPathologyByName(String name, Pageable pageable) {
		return pathologyRepository.findAllPathologyByName(name, pageable);
	}

	@Override
	public List<Pathology> findAllPathology() {
		return pathologyRepository.findAllActPathologySimpleList();
	}


	@Override
	public Optional<Pathology> getPathologyDetails(Long id) {
		return pathologyRepository.findById(id);
	}
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}


	@Override @Transactional
	public Pathology addPathology(DefaultNameAndActiveDto defaultNameAndActiveDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		Pathology pathologyByName= pathologyRepository.findByName(defaultNameAndActiveDto.getName());
		if(pathologyByName!= null) {
			throw new ResourceNameAlreadyExistException("Le nom de la pathologie existe déjà ");  
		} 
		Pathology pathology = new Pathology();		
		BeanUtils.copyProperties(defaultNameAndActiveDto,pathology,"id");
		pathology.setCreatedAt(new Date());
		pathology.setCreatedBy(getCurrentUserId().getId());
		return pathologyRepository.save(pathology);
	}

	@Override @Transactional
	public Pathology updatePathology(Long id, DefaultNameAndActiveDto defaultNameAndActiveDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		Pathology updatePathology = pathologyRepository.findById(id).orElse(null);
		if (updatePathology == null) {
			 throw new ResourceNotFoundByIdException("Aucune pathologie trouvé pour l'identifiant");

		} else {
			Pathology pathologyByName = pathologyRepository.findByName(defaultNameAndActiveDto.getName());
			if (pathologyByName != null) {
				if (pathologyByName.getId() != updatePathology.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom de la pathologie existe déjà");

				}
			}

		}
		BeanUtils.copyProperties(defaultNameAndActiveDto, updatePathology,"id");
		updatePathology.setUpdatedAt(new Date());
		updatePathology.setUpdatedBy(getCurrentUserId().getId());
		return pathologyRepository.save(updatePathology);
	}

	@Override
	public List<Pathology> findAllActive() {
		return pathologyRepository.findAllActive();
	}

}
