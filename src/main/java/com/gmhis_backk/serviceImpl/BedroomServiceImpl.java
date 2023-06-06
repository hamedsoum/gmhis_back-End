package com.gmhis_backk.serviceImpl;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.Bedroom;
import com.gmhis_backk.domain.BedroomType;
import com.gmhis_backk.domain.Storey;
import com.gmhis_backk.dto.BedroomDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.BedroomRepository;
import com.gmhis_backk.repository.BedroomTypeRepository;
import com.gmhis_backk.repository.StoreyRepository;
import com.gmhis_backk.service.BedroomService;
import com.gmhis_backk.service.CurrentUserService;
import static com.gmhis_backk.constant.BedroomTypeConstant.*;
import static com.gmhis_backk.constant.StoreyConstant.*;
import static com.gmhis_backk.constant.BedroomConstant.*;
import java.util.List;

/**
 * 
 * @author Mathurin
 *
 */
@Transactional
@Service
public class BedroomServiceImpl implements BedroomService {

	private CurrentUserService currentUser;
	
	private StoreyRepository storeyRepo;

	private BedroomTypeRepository bedroomTypeRepo;
	
	private BedroomRepository bedroomRepo;

	public BedroomServiceImpl(CurrentUserService currentUser, BedroomTypeRepository bedroomTypeRepo,StoreyRepository storeyRepo,BedroomRepository bedroomRepo) {
		this.currentUser = currentUser;
		this.bedroomTypeRepo = bedroomTypeRepo;
		this.storeyRepo = storeyRepo;
		this.bedroomRepo = bedroomRepo;
	}

	@Override
	public Bedroom addBedroom(BedroomDto bedroomDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		
		BedroomType bedroomType = bedroomTypeRepo.findById(bedroomDto.getBedroomType()).orElse(null);
		if (bedroomType == null)
			throw new ResourceNotFoundByIdException(NO_BEDROOM_TYPE_FOUND_BY_ID);
		
		Storey storey = storeyRepo.findById(bedroomDto.getStorey()).orElse(null);
		if (storey == null)
			throw new ResourceNotFoundByIdException(NO_STOREY_FOUND_BY_ID);

		Bedroom bedroomByLibelle = bedroomRepo.findByLibelleAndStorey(bedroomDto.getLibelle(), storey.getId());
		if (bedroomByLibelle != null)
			throw new ResourceNameAlreadyExistException(BEDROOM_NAME_ALREADY_EXISTS);

		Bedroom addBedroom = new Bedroom();
		BeanUtils.copyProperties(bedroomDto, addBedroom, "id");
		addBedroom.setBedroomType(bedroomType);
		addBedroom.setStorey(storey);
		bedroomRepo.save(addBedroom);
		return addBedroom;
	}

	@Override
	public Bedroom updateBedroom(Long id, BedroomDto bedroomDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {

		BedroomType bedroomType = bedroomTypeRepo.findById(bedroomDto.getBedroomType()).orElse(null);
		if (bedroomType == null)
			throw new ResourceNotFoundByIdException(NO_BEDROOM_TYPE_FOUND_BY_ID);
		
		Storey storey = storeyRepo.findById(bedroomDto.getStorey()).orElse(null);
		if (storey == null)
			throw new ResourceNotFoundByIdException(NO_STOREY_FOUND_BY_ID);

		Bedroom updateBedroom = bedroomRepo.findById(id).orElse(null);

		if (updateBedroom == null) {
			throw new ResourceNotFoundByIdException(NO_BEDROOM_FOUND_BY_ID);

		} else {
			Bedroom bedroomByLibelle = bedroomRepo.findByLibelleAndStorey(bedroomDto.getLibelle(), updateBedroom.getStorey().getId());
			if (bedroomByLibelle != null) {

				if (bedroomByLibelle.getId() != updateBedroom.getId()) {
					throw new ResourceNameAlreadyExistException(BEDROOM_NAME_ALREADY_EXISTS);
				}
			}
		}
		
		BeanUtils.copyProperties(bedroomDto, updateBedroom, "id");
		updateBedroom.setBedroomType(bedroomType);
		updateBedroom.setStorey(storey);
		bedroomRepo.save(updateBedroom);
		return updateBedroom;
	}

	@Override
	public Bedroom getDetail(Long id) {
		return bedroomRepo.getDetail(id);
	}

	@Override
	public Page<Bedroom> bedroomList(String libelle,Long StoreyId, Long type,Pageable pageable) {

		String facility = currentUser.getCurrentUser().getFacilityId();

		if (ObjectUtils.isEmpty(StoreyId)&&ObjectUtils.isEmpty(type)) {
			return bedroomRepo.findByLibelle(libelle, facility, pageable);
			
		}else if (ObjectUtils.isNotEmpty(StoreyId)&&ObjectUtils.isEmpty(type)) {
			return bedroomRepo.findByStorey(libelle, facility, StoreyId, pageable);
			
		}else if (ObjectUtils.isEmpty(StoreyId)&&ObjectUtils.isNotEmpty(type)) {
			return bedroomRepo.findByBedroomType(libelle, facility, type, pageable);
			
		}else {
			return bedroomRepo.findByBedroomTypeAndStorey(libelle, facility, type, StoreyId, pageable);
		}
		
	}

	@Override
	public void deleteBedroom(Long id) throws ResourceNotFoundByIdException {

		Bedroom bedroomToDelete = bedroomRepo.findById(id).orElse(null);

		if (bedroomToDelete == null)
			throw new ResourceNotFoundByIdException(NO_BEDROOM_FOUND_BY_ID);
		bedroomRepo.delete(bedroomToDelete);
	}

	@Override
	public List<Bedroom> bedroomSimpleList() {

		String facility = currentUser.getCurrentUser().getFacilityId();
		List<Bedroom> simpleBedroomList = bedroomRepo.listByFacility(facility);
		return simpleBedroomList;
	}

}
