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
import com.gmhis_backk.domain.ActGroup;
import com.gmhis_backk.dto.ActGroupDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.ActGroupRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ActGroupService;

@Service
public class ActGroupServiceImpl implements ActGroupService {

	@Autowired
	ActGroupRepository actGroupRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Page<ActGroup> findAllActGroup(Pageable pageable) {
		return actGroupRepository.findAll(pageable);
	}

	@Override
	public Page<ActGroup> findAllActGroupByActiveAndName(String name, Boolean active, Pageable pageable) {
		return actGroupRepository.findAllActGroupByActiveAndName(name, active, pageable);
	}

	@Override
	public Page<ActGroup> findAllActGroupByName(String name, Pageable pageable) {
		return actGroupRepository.findAllActGroupByName(name, pageable);
	}

	@Override
	public List<ActGroup> findAllActGroups() {
		return actGroupRepository.findAllActGroupSimpleList();
	}

	@Override
	public void deleteActGroup(Integer id) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public Optional<ActGroup> getActGroupDetails(Long id) {
		return actGroupRepository.findById(id);
 
	}
	
	protected com.gmhis_backk.domain.User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override  @Transactional 
	public ActGroup addAActGroup(ActGroupDto actGroupDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		ActGroup actGroupByName = actGroupRepository.findByName(actGroupDto.getName());
		if(actGroupByName!=null) {
			throw new ResourceNameAlreadyExistException("Le nom de la famille existe déjà ");  
		} 
		ActGroup actGroup = new ActGroup();		
		BeanUtils.copyProperties(actGroupDto,actGroup,"id");
		actGroup.setCreatedAt(new Date());
		actGroup.setCreatedBy(getCurrentUserId().getId());
		return actGroupRepository.save(actGroup);
	}

	@Override @Transactional 
	public ActGroup updateActGroup(Long id, ActGroupDto actGroupDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		ActGroup updateActGroup = actGroupRepository.findById(id).orElse(null);
		
		if (updateActGroup == null) {
			 throw new ResourceNotFoundByIdException("Aucune famille trouvé pour l'identifiant");
		} else {
			ActGroup actGroupByName = actGroupRepository.findByName(actGroupDto.getName());
			if(actGroupByName != null) {
				if(actGroupByName.getId() != updateActGroup.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom de la famille existe déjà");
				}
			}
		}
		BeanUtils.copyProperties(actGroupDto, updateActGroup,"id");
		updateActGroup.setUpdatedAt(new Date());
		updateActGroup.setUpdatedBy(getCurrentUserId().getId());
		return actGroupRepository.save(updateActGroup);
	}

	@Override
	public List<ActGroup> findAllActive() {
		// TODO Auto-generated method stub
		return actGroupRepository.findAllActive();
	}

}
