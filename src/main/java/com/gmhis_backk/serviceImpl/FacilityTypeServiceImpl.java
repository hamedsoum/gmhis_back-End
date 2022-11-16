package com.gmhis_backk.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.FacilityType;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.FacilityTypeRepository;
import com.gmhis_backk.service.FacilityTypeService;

@Service
public class FacilityTypeServiceImpl implements FacilityTypeService {

	@Autowired
	private FacilityTypeRepository facilityTypeRepository;

	@Override
	public FacilityType saveFacilityType(DefaultNameAndActiveDto f)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		FacilityType facilityTypeByName = facilityTypeRepository.findByName(f.getName());
		if (facilityTypeByName != null) {
			throw new ResourceNameAlreadyExistException("le type centre de santé existe déjà");
		}
		FacilityType facilityType = new FacilityType();
		BeanUtils.copyProperties(f,facilityType,"id");
		return facilityTypeRepository.save(facilityType);
	}

	@Override
	public FacilityType updateFacilityType(DefaultNameAndActiveDto f, UUID Id)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		FacilityType updateFacilityType = facilityTypeRepository.findById(Id).orElse(null);
		
		if (updateFacilityType == null) {
			 throw new ResourceNotFoundByIdException("Aucun type centre de sante trouvé pour l'identifiant");
		} else {
			FacilityType facilityTypeByName = facilityTypeRepository.findByName(f.getName());
			if (facilityTypeByName != null) {
				if (facilityTypeByName.getId() != updateFacilityType.getId()) {
					throw new ResourceNameAlreadyExistException("le nom du type de centre sante existe deja");
				}
			}
		}
		BeanUtils.copyProperties(f, updateFacilityType,"id");
		return facilityTypeRepository.save(updateFacilityType);
	}

	@Override
	public FacilityType findFacilityTypeByName(String facility) {
		return facilityTypeRepository.findByName(facility);
	}

	@Override
	public Optional<FacilityType> findFacilityTypeById(UUID id) {
		return facilityTypeRepository.findById(id);
	}

	@Override
	public List<FacilityType> findFacilitiesType() {
		return facilityTypeRepository.findActiveFacilitiesType();
	}

	@Override
	public Page<FacilityType> findFacilitiesType(Pageable pageable) {
		return facilityTypeRepository.findAll(pageable);
	}

	@Override
	public Page<FacilityType> findFacilitiesTypeContaining(String name, Pageable pageable) {
		return facilityTypeRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public List<FacilityType> findActiveFacilitiesType() {
		return facilityTypeRepository.findActiveFacilitiesType();
	}

	@Override
	public Page<FacilityType> findByActive(String namme, Boolean active, Pageable pageable) {
		return facilityTypeRepository.findByActive(namme, active, pageable);
	}
 
}
