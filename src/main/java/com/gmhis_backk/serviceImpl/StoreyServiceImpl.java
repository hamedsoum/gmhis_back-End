package com.gmhis_backk.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.Bedroom;
import com.gmhis_backk.domain.Building;
import com.gmhis_backk.domain.Storey;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.StoreyDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.BedroomRepository;
import com.gmhis_backk.repository.BuildingRepository;
import com.gmhis_backk.repository.StoreyRepository;
import com.gmhis_backk.service.CurrentUserService;
import com.gmhis_backk.service.StoreyService;
import static com.gmhis_backk.constant.StoreyConstant.*;
import static com.gmhis_backk.constant.BuildingConstant.*;


/**
 * 
 * @author MAthurin
 *
 */
@Transactional
@Service
public class StoreyServiceImpl implements StoreyService {
	
	private CurrentUserService currentUser;

	private BuildingRepository buildingRepo;
	
	private BedroomRepository bedroomRepo;
	
	private StoreyRepository storeyRepo;
	
	public StoreyServiceImpl(BuildingRepository buildingRepo,StoreyRepository storeyRepo,CurrentUserService currentUser,BedroomRepository bedroomRepo) {
		
		this.storeyRepo = storeyRepo;
		
		this.buildingRepo = buildingRepo;
		
		this.currentUser = currentUser;
		
		this.bedroomRepo = bedroomRepo;
	}
	
	@Override
	public Storey addStorey(StoreyDto storeyDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		
		Building building = buildingRepo.findById(storeyDto.getBuilding()).orElse(null);
		if(building == null) throw new ResourceNotFoundByIdException(NO_BUILDING_FOUND_BY_ID);
		
		Storey storeyByLibelle = storeyRepo.storeyByLibelleAndBuilding(storeyDto.getLibelle(),storeyDto.getBuilding());
		if(storeyByLibelle !=null) throw new ResourceNameAlreadyExistException(STOREY_NAME_ALREADY_EXISTS);
		
		Storey addStorey = new Storey();
		BeanUtils.copyProperties(storeyDto, addStorey,"id");
		addStorey.setBuilding(building);
		storeyRepo.save(addStorey);
		return addStorey;
	}

	@Override
	public Storey updateStorey(Long id, StoreyDto storeyDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		
		Building building = buildingRepo.findById(storeyDto.getBuilding()).orElse(null);
		if(building == null) throw new ResourceNotFoundByIdException(NO_BUILDING_FOUND_BY_ID);
		
		Storey updateStorey = storeyRepo.findById(id).orElse(null);
		
		if (updateStorey == null) {
			throw new ResourceNotFoundByIdException(NO_STOREY_FOUND_BY_ID);
		}else {
			
			Storey storeyByLibelle = storeyRepo.storeyByLibelleAndBuilding(storeyDto.getLibelle(),storeyDto.getBuilding());
			
			if (storeyByLibelle != null) {
				if (storeyByLibelle.getId() != updateStorey.getId()) {
					throw new ResourceNameAlreadyExistException(STOREY_NAME_ALREADY_EXISTS);
				}
				
			}
			
		}
		BeanUtils.copyProperties(storeyDto, updateStorey,"id");
		updateStorey.setBuilding(building);
		storeyRepo.save(updateStorey);
		return updateStorey;
	}

	@Override
	public List<Storey> findSimpleList() {
        User user = currentUser.getCurrentUser();
		String facility = user.getFacilityId();
		return storeyRepo.findByFacility(facility);
	}

	@Override
	public Storey getDetail(Long id) {
		return storeyRepo.getDetail(id);
	}

	@Override
	public Page<Storey> storeyList(String libelle, Long buildingId,Pageable pageable) {
		
		User user = currentUser.getCurrentUser();
		
		String facility = user.getFacilityId();
		
		if (ObjectUtils.isEmpty(buildingId)) {
			return storeyRepo.findAll(libelle, facility, pageable);
		}else {
			return storeyRepo.findByBuilding(libelle, buildingId, facility, pageable);
		}
		
	}

	@Override
	public void deleteStorey(Long id) throws ResourceNotFoundByIdException {
		
		List<Bedroom> bedroomListByStorey = bedroomRepo.findByStorey(id);
		bedroomRepo.deleteAll(bedroomListByStorey);
		
		Storey deleteStorey = storeyRepo.findById(id).orElse(null);
		if(deleteStorey ==null) throw new ResourceNotFoundByIdException(NO_STOREY_FOUND_BY_ID);
		
		storeyRepo.delete(deleteStorey);
	}

}
