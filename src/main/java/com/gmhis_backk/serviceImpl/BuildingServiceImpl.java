package com.gmhis_backk.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.Building;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.BuildingDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.BuildingRepository;
import com.gmhis_backk.service.BuildingService;
import com.gmhis_backk.service.CurrentUserService;
import static com.gmhis_backk.constant.BuildingConstant.*;

import java.util.List;

/**
 * 
 * @author Mathurin
 *
 */
@Transactional
@Service
public class BuildingServiceImpl implements BuildingService{
	
	private CurrentUserService currentUser;
	
	private BuildingRepository buildingRepo;
	
	public BuildingServiceImpl(CurrentUserService currentUser,BuildingRepository buildingRepo) {
		this.currentUser = currentUser;
		this.buildingRepo = buildingRepo;
	}

	@Override
	public Building addBuilding(BuildingDto buildingDto) throws ResourceNameAlreadyExistException {
		
		User user = currentUser.getCurrentUser();
		
		String facility = user.getFacilityId();
		
		Building buildingByLibelle = buildingRepo.findByLibelleAndFacility(buildingDto.getLibelle(), facility);
		if(buildingByLibelle !=null) throw new ResourceNameAlreadyExistException(BUILDING_NAME_ALREADY_EXISTS);
		
		Building addBuilding = new Building();
		BeanUtils.copyProperties(buildingDto, addBuilding,"id");
		addBuilding.setFacilityId(facility);
		buildingRepo.save(addBuilding);
		return addBuilding;
	}

	@Override
	public Building updateBuilding(Long id, BuildingDto buildingDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		
        User user = currentUser.getCurrentUser();
		
		String facility = user.getFacilityId();
		
		Building buildingToUpdate = buildingRepo.findById(id).orElse(null);
		
		if (buildingToUpdate == null) {
			throw new ResourceNotFoundByIdException(NO_BUILDING_FOUND_BY_ID);
			
		}else {
			
			Building buildingByLibelle = buildingRepo.findByLibelleAndFacility(buildingDto.getLibelle(), facility);
			
			if (buildingByLibelle.getId() != null) {
				
				if (buildingByLibelle.getId() != buildingToUpdate.getId()) {
					throw new ResourceNameAlreadyExistException(BUILDING_NAME_ALREADY_EXISTS);
				}
			}
		}
		BeanUtils.copyProperties(buildingDto, buildingToUpdate,"id","facilityId");
		buildingRepo.save(buildingToUpdate);
		return buildingToUpdate;
	}

	@Override
	public Building getDetail(Long id) {
	
		return buildingRepo.getDetail(id);
	}

	@Override
	public Page<Building> buildingList(String libelle, Pageable pageable) {
		
        User user = currentUser.getCurrentUser();
		
		String facility = user.getFacilityId();
		
		return buildingRepo.findByLibelle(libelle, facility, pageable);
	}

	@Override
	public void deleteBuilding(Long id) throws ResourceNotFoundByIdException {
		
		Building buildingToDelete = buildingRepo.findById(id).orElse(null);
		
		if(buildingToDelete == null) throw new ResourceNotFoundByIdException(NO_BUILDING_FOUND_BY_ID);
		
		buildingRepo.delete(buildingToDelete);
	}

	@Override
	public List<Building> buildingSimpleList() {
		return buildingRepo.findAll();
	}

}
