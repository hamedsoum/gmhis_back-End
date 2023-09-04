package com.gmhis_backk.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Facility;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.FacilityDTO;
import com.gmhis_backk.dto.UserDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.FacilityRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.FacilityCategoryService;
import com.gmhis_backk.service.FacilityService;
import com.gmhis_backk.service.FacilityTypeService;
import com.gmhis_backk.service.UserService;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public class FacilityServiceImpl implements FacilityService{

	@Autowired
	private FacilityRepository facilityRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userServiceImpl;
	
	
	@Autowired 
	FacilityCategoryService facilityCategoryService;
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	@Override
	public Facility saveFacility(FacilityDTO facilityDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		
		Facility facilityByName = facilityRepository.findByName(facilityDto.getName());
		if (facilityByName != null) throw new ResourceNameAlreadyExistException("le centre de santé existe déjà");	

		Facility facility = new Facility();
		BeanUtils.copyProperties(facilityDto,facility,"id");
		facility.setCreatedAt(new Date());
		facility.setCreatedBy(getCurrentUserId().getId());
		
		Facility facilitySaved =  facilityRepository.save(facility);
		facilityAdmin(facilitySaved);
		
		return facilitySaved;
	}
	
	private void facilityAdmin(Facility facility) {
		UserDto newUser = new UserDto();
		
		newUser.setFirstName(facility.getShortName());
		newUser.setLastName(facility.getShortName());
		newUser.setTel(facility.getContact());
		newUser.setFacilityId(facility.getId());
		newUser.setPassword("Admin123#");
		newUser.setEmail(facility.getEmail());
		
		ArrayList<Integer> list =new ArrayList<Integer>();
		list.add(1);
		newUser.setRoles(list);
		
		userServiceImpl.addNewUser(newUser);
	}

	@Override
	public Facility updateFacility(FacilityDTO facilityDto, UUID id) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		Facility updateFacility = facilityRepository.findById(id).orElse(null);
		
		if (updateFacility == null) {
			 throw new ResourceNotFoundByIdException("Aucun centre de sante trouvé pour l'identifiant");
		} else {
			Facility facilityByName = facilityRepository.findByName(facilityDto.getName());
			if (facilityByName != null) {
				if (facilityByName.getId() != updateFacility.getId()) {
					throw new ResourceNameAlreadyExistException("le nom du centre sante existe deja");
				}
			}
		}
		BeanUtils.copyProperties(facilityDto, updateFacility,"id");
		updateFacility.setUpdatedAt(new Date());
		updateFacility.setUpdatedBy(getCurrentUserId().getId());
		return facilityRepository.save(updateFacility);
	}

	@Override
	public Facility findFacilityByName(String facility) {
		return facilityRepository.findByName(facility);
	}

	@Override
	public Optional<Facility> findFacilityById(UUID id) {
		return facilityRepository.findById(id);
	}

	@Override
	public List<Facility> findFacilities() {
		return facilityRepository.findActiveFacilities();
	}

	@Override
	public Page<Facility> findFacilities(Pageable pageable) {
		return facilityRepository.findAll(pageable);
	}

	@Override
	public Page<Facility> findFacilitiesContaining(String name, Pageable pageable) {
		return facilityRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public List<Facility> findActiveFacilities() {
		return facilityRepository.findActiveFacilities();
	}

	@Override
	public Page<Facility> findByActive(String namme, Boolean active, Pageable pageable) {
		return facilityRepository.findByActive(namme, active, pageable);
	}

	@Override
	public Page<Facility> findByCategory(String namme, Boolean active, String facilityCategoryId, Pageable pageable) {
		return facilityRepository.findFacilityByCategoryId(namme, active, facilityCategoryId, pageable);
	}

}
