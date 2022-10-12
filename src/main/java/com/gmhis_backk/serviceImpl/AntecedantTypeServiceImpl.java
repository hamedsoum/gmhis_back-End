package com.gmhis_backk.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.AntecedantType;
import com.gmhis_backk.dto.AntecedantTypeDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.AntecedentTypeRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.AntecendantTypeService;

@Service
public class AntecedantTypeServiceImpl implements AntecendantTypeService {
	
	@Autowired
	AntecedentTypeRepository antecedentTypeRepo;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public Page<AntecedantType> findAllAntecedantType(Pageable pageable) {
		return antecedentTypeRepo.findAll(pageable);
	}

	@Override
	public Page<AntecedantType> findAllAntecedantTypeByActiveAndName(String name, Boolean active, Pageable pageable) {
		return antecedentTypeRepo.findAlAntecedantTypeByActiveAndName(name, active, pageable);
	}

	@Override
	public Page<AntecedantType> findAllAntecedantTypeByName(String name, Pageable pageable) {
		return antecedentTypeRepo.findAllAntecedantTypeByName(name, pageable);
	}

	@Override
	public List<AntecedantType> findAllAntecedantTypes() {
		return antecedentTypeRepo.findAllAntecedantTypeSimpleList();
	}

	@Override
	public void deleteAntecedantType(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<AntecedantType> getAntecedantTypeDetails(Long id) {
		// TODO Auto-generated method stub
		return antecedentTypeRepo.findById(id);
	}
	
	protected com.gmhis_backk.domain.User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override
	public AntecedantType addAntecedantType(AntecedantTypeDTO antecedantTypeDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		// TODO Auto-generated method stub
		 AntecedantType antecedanTypeByName = antecedentTypeRepo.findByName(antecedantTypeDto.getName());
		if(antecedanTypeByName!=null) {
			throw new ResourceNameAlreadyExistException("Le nom de la famille d'antecedent existe déjà ");  
		} 
		AntecedantType antecedantType = new AntecedantType();		
		BeanUtils.copyProperties(antecedantTypeDto,antecedantType,"id");
		antecedantType.setCreatedAt(new Date());
		antecedantType.setCreatedBy(getCurrentUserId().getId());
		return antecedentTypeRepo.save(antecedantType);
	}

	@Override
	public AntecedantType updateAntecedantType(Long id, AntecedantTypeDTO antecedantTypeDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		AntecedantType updateAntecedent = antecedentTypeRepo.findById(id).orElse(null);
		
		if (updateAntecedent == null) {
			 throw new ResourceNotFoundByIdException("Aucune famille trouvé pour l'identifiant");
		} else {
			AntecedantType updateAntecedentByName = antecedentTypeRepo.findByName(antecedantTypeDto.getName());
			if(updateAntecedentByName != null) {
				if(updateAntecedentByName.getId() != updateAntecedent.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom de la famille existe déjà");
				}
			}
		}
		BeanUtils.copyProperties(antecedantTypeDto, updateAntecedent,"id");
		updateAntecedent.setUpdatedAt(new Date());
		updateAntecedent.setUpdatedBy(getCurrentUserId().getId());
		return antecedentTypeRepo.save(updateAntecedent);
	}
	
}
