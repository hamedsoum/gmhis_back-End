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
import com.gmhis_backk.domain.Convention;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.ConventionRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ConventionService;

@Service
public class ConventionServiceImpl implements ConventionService{
	
	@Autowired
	ConventionRepository conventionRepository;
	
	@Autowired
	UserRepository userRepository;

	
	@Override
	public Page<Convention> findAllConvention(Pageable pageable) {
		return conventionRepository.findAll(pageable);
	}

	@Override
	public Page<Convention> findAllConventionByActiveAndName(String name, Boolean active, Pageable pageable) {
		// TODO Auto-generated method stub
		return conventionRepository.findAllConventionByActiveAndName(name, active, pageable);
	}

	@Override
	public Page<Convention> findAllConventionByName(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return conventionRepository.findAllConventionByName(name, pageable);
	}

	@Override
	public List<Convention> findAllConventions() {
		// TODO Auto-generated method stub
		return conventionRepository.findAllConventionSimpleList();
	}

	@Override
	public void deleteConvention(Integer id) {
		// TODO Auto-generated method stub	
	}

	@Override
	public Optional<Convention> getConventionDetails(Long id) {
		// TODO Auto-generated method stub
		return conventionRepository.findById(id);
	}
	
	protected com.gmhis_backk.domain.User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override @Transactional 
	public Convention addConvention(DefaultNameAndActiveDto defaultNameAndActiveDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		Convention ConventionByName = conventionRepository.findByName(defaultNameAndActiveDto.getName());
		if(ConventionByName!=null) {
			throw new ResourceNameAlreadyExistException("La convention de la famille existe déjà ");  
		} 
		Convention convention = new Convention();		
		BeanUtils.copyProperties(defaultNameAndActiveDto,convention,"id");
		convention.setCreatedAt(new Date());
		convention.setCreatedBy(getCurrentUserId().getId());
		return conventionRepository.save(convention);
	}

	@Override @Transactional 
	public Convention updateConvention(Long id, DefaultNameAndActiveDto defaultNameAndActiveDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
    Convention updateConvention = conventionRepository.findById(id).orElse(null);
		
		if (updateConvention == null) {
			 throw new ResourceNotFoundByIdException("Aucune convention trouvé pour l'identifiant");
		} else {
			Convention conventionByName = conventionRepository.findByName(defaultNameAndActiveDto.getName());
			if(conventionByName != null) {
				if(conventionByName.getId() != updateConvention.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom de la convention existe déjà");
				}
			}
		}
		BeanUtils.copyProperties(defaultNameAndActiveDto, updateConvention,"id");
		updateConvention.setUpdatedAt(new Date());
		updateConvention.setUpdatedBy(getCurrentUserId().getId());
		return conventionRepository.save(updateConvention);
	}

}
