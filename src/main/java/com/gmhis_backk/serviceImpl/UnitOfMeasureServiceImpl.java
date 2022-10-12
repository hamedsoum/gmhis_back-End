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
import com.gmhis_backk.domain.UnitOfMeasure;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UnitOfMeasureRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.UnitOfMeasureService;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
	
	@Autowired
	UnitOfMeasureRepository unitOfMeasureRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Page<UnitOfMeasure> findAllUnitOfMeasure(Pageable pageable) {
		// TODO Auto-generated method stub
		return unitOfMeasureRepository.findAll(pageable);
	}

	@Override
	public Page<UnitOfMeasure> findAllUnitOfMeasureByActiveAndName(String name, Boolean active, Pageable pageable) {
		// TODO Auto-generated method stub
		return unitOfMeasureRepository.findAllUnitOfMeasureByActiveAndName(name, active, pageable);
	}

	@Override
	public Page<UnitOfMeasure> findAllUnitOfMeasureByName(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return unitOfMeasureRepository.findAllUnitOfMeasureByName(name, pageable);
	}

	@Override
	public List<UnitOfMeasure> findAllUnitOfMeasures() {
		// TODO Auto-generated method stub
		return unitOfMeasureRepository.findUnitOfMeasureSimpleList();
	}

	@Override
	public void deleteUnitOfMeasure(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<UnitOfMeasure> getUnitOfMeasureDetails(Long id) {
		return unitOfMeasureRepository.findById(id);
	}
	
	protected com.gmhis_backk.domain.User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override  @Transactional 
	public UnitOfMeasure addUnitOfMeasure(DefaultNameAndActiveDto defaultNameAndActiveDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		UnitOfMeasure UnitOfMeasurByName = unitOfMeasureRepository.findByName(defaultNameAndActiveDto.getName());
		if(UnitOfMeasurByName!=null) {
			throw new ResourceNameAlreadyExistException("Le nom de l'unite de messure existe déjà ");  
		} 
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();		
		BeanUtils.copyProperties(defaultNameAndActiveDto,unitOfMeasure,"id");
		unitOfMeasure.setCreatedAt(new Date());
		unitOfMeasure.setCreatedBy(getCurrentUserId().getId());
		return unitOfMeasureRepository.save(unitOfMeasure);
	}

	@Override @Transactional 
	public UnitOfMeasure updateUnitOfMeasure(Long id, DefaultNameAndActiveDto defaultNameAndActiveDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		UnitOfMeasure updateUnitOfMeasure = unitOfMeasureRepository.findById(id).orElse(null);
		
		if (updateUnitOfMeasure == null) {
			 throw new ResourceNotFoundByIdException("Aucun unité de messure trouvé pour l'identifiant");
		} else {
			UnitOfMeasure unitOfMeasureByName = unitOfMeasureRepository.findByName(defaultNameAndActiveDto.getName());
			if(unitOfMeasureByName != null) {
				if(unitOfMeasureByName.getId() != updateUnitOfMeasure.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom de la famille existe déjà");
				}
			}
		}
		BeanUtils.copyProperties(defaultNameAndActiveDto, updateUnitOfMeasure,"id");
		updateUnitOfMeasure.setUpdatedAt(new Date());
		updateUnitOfMeasure.setUpdatedBy(getCurrentUserId().getId());
		return unitOfMeasureRepository.save(updateUnitOfMeasure);
	}

}
