package com.gmhis_backk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.FacilityType;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.FacilityTypeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/facility_type")
public class FacilityTypeContyroller {

	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	FacilityTypeService facilityTypeService;
	
	@PostMapping("/add")
	@ApiOperation("/Ajouter un type de centre de sante")
	public ResponseEntity<FacilityType>addFacilityTtpe(@RequestBody DefaultNameAndActiveDto f) throws ResourceNameAlreadyExistException,ResourceNotFoundByIdException{
		FacilityType facilityType = facilityTypeService.saveFacilityType(f);
		return new ResponseEntity<FacilityType>(facilityType, HttpStatus.OK);
	} 
	
	@PutMapping("/update/{id}")
	@ApiOperation("/Ajouter un type de centre de sante")
	public ResponseEntity<FacilityType> updateFacilityType(@RequestBody DefaultNameAndActiveDto f, @PathVariable("id") UUID id) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException{
		FacilityType facilityType = facilityTypeService.updateFacilityType(f, id);
		return new ResponseEntity<FacilityType>(facilityType, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste paginee de toutes les types de centres medicaux dans le système")
	@GetMapping("/p_list")
	public ResponseEntity<Map<String, Object>> paginatedList(
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false) String active, @RequestParam(defaultValue = "id,desc") String[] sort, 
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));

		
       Page<FacilityType> pFacilities = null;
		
		if (ObjectUtils.isEmpty(active) && ObjectUtils.isEmpty(name)) {
			pFacilities = facilityTypeService.findFacilitiesType(pageable);
		} else if(ObjectUtils.isNotEmpty(active)){
			pFacilities = facilityTypeService.findByActive(name, Boolean.parseBoolean(active), pageable);
		}else if (ObjectUtils.isNotEmpty(name)) {
			pFacilities= facilityTypeService.findFacilitiesTypeContaining(name,pageable);
		} 
		
		List<FacilityType> lFacilities = pFacilities.getContent();
		
			
		List<Map<String, Object>> facilities = this.getMapFromFacilityList(lFacilities);
		response.put("items", facilities);
		response.put("totalElements", pFacilities.getTotalElements());
		response.put("totalPages", pFacilities.getTotalPages());
		response.put("size", pFacilities.getSize());
		response.put("pageNumber", pFacilities.getNumber());
		response.put("numberOfElements", pFacilities.getNumberOfElements());
		response.put("first", pFacilities.isFirst());
		response.put("last", pFacilities.isLast());
		response.put("empty", pFacilities.isEmpty());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	protected List<Map<String, Object>> getMapFromFacilityList(List<FacilityType> facilitiesTypes) {
		List<Map<String, Object>> facilityList = new ArrayList<>();
		facilitiesTypes.stream().forEach(facilityDto -> {
			Map<String, Object> facilitiesMap = new HashMap<>();
			facilitiesMap.put("id", facilityDto.getId());
			facilitiesMap.put("name", facilityDto.getName());
			facilitiesMap.put("active", facilityDto.getActive());
			facilityList.add(facilitiesMap);
		});
		return facilityList;
	}
	
	@GetMapping("/active_facilities_name")
	@ApiOperation(value = "Lister la liste des ids et noms des types centres de sante actives dans le système")
	public ResponseEntity<List<Map<String, Object>>>  activeFacilityName() {
		List<Map<String, Object>>  actCodeList = new ArrayList<>();

		facilityTypeService.findActiveFacilitiesType().forEach(facilityTypeDto -> {
			Map<String, Object> facilityTypeMap = new HashMap<>();
			facilityTypeMap.put("id", facilityTypeDto.getId());
			facilityTypeMap.put("name", facilityTypeDto.getName());
			actCodeList.add(facilityTypeMap);
		});
		
		return new ResponseEntity<>(actCodeList, HttpStatus.OK);
	}
}
