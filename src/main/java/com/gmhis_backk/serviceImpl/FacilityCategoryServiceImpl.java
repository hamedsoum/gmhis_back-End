package com.gmhis_backk.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.FaciityCategory;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.FacilityCategoryRepository;
import com.gmhis_backk.service.FacilityCategoryService;

@Service
public class FacilityCategoryServiceImpl implements FacilityCategoryService {
	
	@Autowired
	private FacilityCategoryRepository facilityCategoryRepository;

	@Override
	public FaciityCategory saveFaciityCategory(DefaultNameAndActiveDto f)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		FaciityCategory facilityCategoryByName = facilityCategoryRepository.findByName(f.getName());
		if (facilityCategoryByName != null) {
			throw new ResourceNameAlreadyExistException("la categorie de centre de santé existe déjà");
		}
		FaciityCategory facilityCategory = new FaciityCategory();
		BeanUtils.copyProperties(f,facilityCategory,"id");
		return facilityCategoryRepository.save(facilityCategory);
	}

	@Override
	public FaciityCategory updateFaciityCategory(DefaultNameAndActiveDto f, UUID Id)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		FaciityCategory updateFacilityCategory = facilityCategoryRepository.findById(Id).orElse(null);
		
		if (updateFacilityCategory == null) {
			 throw new ResourceNotFoundByIdException("Aucun type centre de sante trouvé pour l'identifiant");
		} else {
			FaciityCategory facilityTypeByName = facilityCategoryRepository.findByName(f.getName());
			if (facilityTypeByName != null) {
				if (facilityTypeByName.getId() != updateFacilityCategory.getId()) {
					throw new ResourceNameAlreadyExistException("le nom du type de centre sante existe deja");
				}
			}
		}
		BeanUtils.copyProperties(f, updateFacilityCategory,"id");
		return facilityCategoryRepository.save(updateFacilityCategory);
	}

	@Override
	public FaciityCategory findFaciityCategoryByName(String facility) {
		return facilityCategoryRepository.findByName(facility);
	}

	@Override
	public Optional<FaciityCategory> findFaciityCategoryById(UUID id) {
		return facilityCategoryRepository.findById(id);
	}

	@Override
	public List<FaciityCategory> findFacilityCategory() {
		return facilityCategoryRepository.findActiveFacilityCategories();
	}

	@Override
	public Page<FaciityCategory> findFacilityCategories(Pageable pageable) {
		return facilityCategoryRepository.findAll(pageable);
	}

	@Override
	public Page<FaciityCategory> findFacilityCategoryContaining(String name, Pageable pageable) {
		return facilityCategoryRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public List<FaciityCategory> findActiveFacilityCategory() {
		return facilityCategoryRepository.findActiveFacilityCategories();
	}

	@Override
	public Page<FaciityCategory> findByActive(String namme, Boolean active, Pageable pageable) {
		return facilityCategoryRepository.findByActive(namme, active, pageable);
	}


 
}
