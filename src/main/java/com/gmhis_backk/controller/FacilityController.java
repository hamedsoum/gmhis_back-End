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

import com.gmhis_backk.domain.Admission;
import com.gmhis_backk.domain.Facility;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.FacilityDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.FacilityService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/facility")
public class FacilityController {
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	FacilityService facilityService;
	
	@PostMapping("/add")
	@ApiOperation("/Ajouter un centre de sante")
	public ResponseEntity<Facility>addFacility(@RequestBody FacilityDTO facilityDto) throws ResourceNameAlreadyExistException,ResourceNotFoundByIdException{
		Facility facility = facilityService.saveFacility(facilityDto);
		return new ResponseEntity<Facility>(facility, HttpStatus.OK);
	} 
	
	@PutMapping("/update/{id}")
	@ApiOperation("/Modiifer un centre de sante")
	public ResponseEntity<Facility> updateFacility(@RequestBody FacilityDTO facilityDto, @PathVariable("id") UUID id) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException{
		Facility facility = facilityService.updateFacility(facilityDto, id);
		return new ResponseEntity<Facility>(facility, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste paginee de toutes les centres medicaux dans le système")
	@GetMapping("/p_list")
	public ResponseEntity<Map<String, Object>> paginatedList(
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false) String active, @RequestParam(defaultValue = "id,desc") String[] sort, 
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));

		
       Page<Facility> pFacilities = null;
		
		if (ObjectUtils.isEmpty(active) && ObjectUtils.isEmpty(name)) {
			pFacilities = facilityService.findFacilities(pageable);
		} else if(ObjectUtils.isNotEmpty(active)){
			pFacilities = facilityService.findByActive(name, Boolean.parseBoolean(active), pageable);
		}else if (ObjectUtils.isNotEmpty(name)) {
			pFacilities= facilityService.findFacilitiesContaining(name,pageable);
		} 
		
		List<Facility> lFacilities = pFacilities.getContent();
		
			
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
	
	protected List<Map<String, Object>> getMapFromFacilityList(List<Facility> facilities) {
		List<Map<String, Object>> facilityList = new ArrayList<>();
		facilities.stream().forEach(facilityDto -> {
			Map<String, Object> facilitiesMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(facilityDto.getCreatedBy()) ? new User()
					: userRepository.findById(facilityDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(facilityDto.getUpdatedBy()) ? new User()
					: userRepository.findById(facilityDto.getUpdatedBy()).orElse(null);
			facilitiesMap.put("id", facilityDto.getId());
			facilitiesMap.put("name", facilityDto.getName());
			facilitiesMap.put("active", facilityDto.getActive());
			facilitiesMap.put("dhisCode", facilityDto.getDhisCode());
			facilitiesMap.put("latitude", facilityDto.getLatitude());
			facilitiesMap.put("localCode", facilityDto.getLocalCode());
			facilitiesMap.put("longitude", facilityDto.getLongitude());
			facilitiesMap.put("shortName", facilityDto.getShortName());
			facilitiesMap.put("locality", facilityDto.getLocality().getName());
			facilitiesMap.put("createdAt", facilityDto.getCreatedAt());
			facilitiesMap.put("updatedAt", facilityDto.getUpdatedAt());
			facilitiesMap.put("createdByLogin", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLogin());
			facilitiesMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			facilitiesMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			facilitiesMap.put("UpdatedByLogin", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLogin());
			facilitiesMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			facilitiesMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			facilityList.add(facilitiesMap);
		});
		return facilityList;
	}
	
	@GetMapping("/active_facilities_name")
	@ApiOperation(value = "Lister la liste des ids et noms des centres de sante actives dans le système")
	public ResponseEntity<List<Map<String, Object>>>  activeFacilityName() {
		List<Map<String, Object>>  actCodeList = new ArrayList<>();

		facilityService.findActiveFacilities().forEach(facilityDto -> {
			Map<String, Object> facilityMap = new HashMap<>();
			facilityMap.put("id", facilityDto.getId());
			facilityMap.put("name", facilityDto.getName());
			actCodeList.add(facilityMap);
		});
		
		return new ResponseEntity<>(actCodeList, HttpStatus.OK);
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'un centre de sante ")
	public  ResponseEntity<Map<String, Object>> getDetail(@PathVariable UUID id){
		Map<String, Object> response = new HashMap<>();

		Facility falicity = facilityService.findFacilityById(id).orElse(null);
		response.put("id", falicity.getId());
		response.put("facilityName", falicity.getName());
		response.put("facilityShortNameName", falicity.getShortName());
//		response.put("patientExternalId", admission.getPatient().getPatientExternalId());
//		response.put("act", admission.getAct().getId());
//		response.put("admissionDate", admission.getAdmissionStartDate());
//		response.put("service", admission.getService().getId());
//		response.put("practician", admission.getPractician().getId());
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	

}
