package com.gmhis_backk.serviceImpl;



import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.ActCategory;
import com.gmhis_backk.dto.ActCategoryDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.ActCategoryRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ActCategoryService;



@Service
public class actCategoryServiceImpl implements ActCategoryService {
	
	@Autowired
	ActCategoryRepository actCategoryRepo;
	
	@Autowired
	UserRepository userRepository;


	@Override
	public Page<ActCategory> findAllActCategory(Pageable pageable) {
		return actCategoryRepo.findAll(pageable);
	}

	@Override
	public Page<ActCategory> findAllActCategoryByActiveAndName(String name, Boolean active, Pageable pageable) {
		// TODO Auto-generated method stub
		return actCategoryRepo.findAllActCategoryByActiveAndName(name, active, pageable);
	};

	@Override
	public Page<ActCategory> findAllActCategoryByName(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return actCategoryRepo.findAllActCategoryByName(name, pageable);
	}

	@Override
	public void deleteActCategory(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActCategory getActCategoryDetails(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected com.gmhis_backk.domain.User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	
	@Override @Transactional 
	public ActCategory addActCategory(ActCategoryDto actCategoryDto) throws ResourceNameAlreadyExistException,
			ResourceNotFoundByIdException {
		ActCategory actCategoryByName = actCategoryRepo.findByName(actCategoryDto.getName());
		if(actCategoryByName!=null) {
			throw new ResourceNameAlreadyExistException("Le nom de la categorie existe déjà ");  
		} 
		ActCategory actCategory = new ActCategory();		
		BeanUtils.copyProperties(actCategoryDto,actCategory,"id");
		actCategory.setCreatedAt(new Date());
		actCategory.setCreatedBy(getCurrentUserId().getId());
		return actCategoryRepo.save(actCategory);
	}

	

	@Override
	public List<ActCategory> findAllActCategories() {
		return actCategoryRepo.findAllActCategorySimpleList();
	}

	@Override @Transactional 
	public ActCategory updateActCategory(Long id, ActCategoryDto actCategoryDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
ActCategory updateActCategory = actCategoryRepo.findById(id).orElse(null);
		
		if (updateActCategory == null) {
			 throw new ResourceNotFoundByIdException("Aucun groupe trouvé pour l'identifiant");
		} else {
			ActCategory actCategoryByName = actCategoryRepo.findByName(actCategoryDto.getName());
			if(actCategoryByName != null) {
				if(actCategoryByName.getId() != updateActCategory.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom de la commune existe déjà");
				}
			}
		}
		BeanUtils.copyProperties(actCategoryDto, updateActCategory,"id");
		updateActCategory.setUpdatedAt(new Date());
		updateActCategory.setUpdatedBy(getCurrentUserId().getId());
		return actCategoryRepo.save(updateActCategory);
	}

}
