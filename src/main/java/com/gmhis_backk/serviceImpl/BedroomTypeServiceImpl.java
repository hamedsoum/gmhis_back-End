package com.gmhis_backk.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.BedroomType;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.BedroomTypeDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.BedroomTypeRepository;
import com.gmhis_backk.service.BedroomTypeService;
import com.gmhis_backk.service.CurrentUserService;
import static com.gmhis_backk.constant.BedroomTypeConstant.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Mathurin
 *
 */
@Transactional
@Service
public class BedroomTypeServiceImpl implements BedroomTypeService {

	private CurrentUserService currentUser;

	private BedroomTypeRepository bedroomTypegRepo;

	public BedroomTypeServiceImpl(CurrentUserService currentUser, BedroomTypeRepository bedroomTypegRepo) {
		this.currentUser = currentUser;
		this.bedroomTypegRepo = bedroomTypegRepo;
	}

	@Override
	public BedroomType addBedroomType(BedroomTypeDto bedroomTypeDto) throws ResourceNameAlreadyExistException {

		User user = currentUser.getCurrentUser();

		String facility = user.getFacilityId();

		BedroomType bedroomTypeByLibelle = bedroomTypegRepo.findByLibelleAndFacility(bedroomTypeDto.getLibelle(),
				facility);
		if (bedroomTypeByLibelle != null)
			throw new ResourceNameAlreadyExistException(BEDROOM_TYPE_NAME_ALREADY_EXISTS);

		BedroomType addBedroomType = new BedroomType();
		BeanUtils.copyProperties(bedroomTypeDto, addBedroomType, "id");
		addBedroomType.setFacilityId(facility);
		bedroomTypegRepo.save(addBedroomType);
		return addBedroomType;
	}

	@Override
	public BedroomType updateBedroomType(Long id, BedroomTypeDto bedroomTypeDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {

		String facility = currentUser.getCurrentUser().getFacilityId();

		BedroomType bedroomTypeToUpdate = bedroomTypegRepo.findById(id).orElse(null);

		if (bedroomTypeToUpdate == null) {
			throw new ResourceNotFoundByIdException(NO_BEDROOM_TYPE_FOUND_BY_ID);

		} else {
			BedroomType bedroomTypeByLibelle = bedroomTypegRepo.findByLibelleAndFacility(bedroomTypeDto.getLibelle(),
					facility);

			if (bedroomTypeByLibelle != null) {

				if (bedroomTypeByLibelle.getId() != bedroomTypeToUpdate.getId()) {
					throw new ResourceNameAlreadyExistException(BEDROOM_TYPE_NAME_ALREADY_EXISTS);
				}
			}
		}
		BeanUtils.copyProperties(bedroomTypeDto, bedroomTypeToUpdate, "id", "facilityId");
		bedroomTypegRepo.save(bedroomTypeToUpdate);
		return bedroomTypeToUpdate;
	}

	@Override
	public BedroomType getDetail(Long id) {
		return bedroomTypegRepo.getDetail(id);
	}

	@Override
	public Page<BedroomType> bedroomTypeList(String libelle, Pageable pageable) {

		User user = currentUser.getCurrentUser();

		String facility = user.getFacilityId();

		return bedroomTypegRepo.findByLibelle(libelle, facility, pageable);
	}

	@Override
	public void deleteBedroomType(Long id) throws ResourceNotFoundByIdException {

		BedroomType bedroomTypeToDelete = bedroomTypegRepo.findById(id).orElse(null);

		if (bedroomTypeToDelete == null)
			throw new ResourceNotFoundByIdException(NO_BEDROOM_TYPE_FOUND_BY_ID);

		bedroomTypegRepo.delete(bedroomTypeToDelete);
	}

	@Override
	public List<BedroomType> bedroomTypeSimpleList() {

		User user = currentUser.getCurrentUser();

		String facility = null;

		List<BedroomType> list = new ArrayList<>();

		if (user != null) {
			facility = user.getFacilityId();
			list = bedroomTypegRepo.listByFacility(facility);
		}
		return list;
	}

}
