package com.gmhis_backk.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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

import com.gmhis_backk.domain.Speciality;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.SpecialityService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/speciality")
public class SpecialityController {
	@Autowired
	SpecialityService conventionService;
	
	@Autowired 
	UserRepository userRepository;

	
	@GetMapping("/list")
	@ApiOperation("liste paginee de toutes les specilialites  dans le systeme")
	public ResponseEntity<Map<String, Object>>getAllSpeciality(
			
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {
		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<Speciality> pageSpeciality;
		
		pageSpeciality = conventionService.findAllSpeciality(paging);
		
		if (StringUtils.isNotBlank(active)) {

			pageSpeciality = conventionService.findAllSpecialityByActiveAndName(name.trim(), Boolean.parseBoolean(active), paging);

		} else if (StringUtils.isNotBlank(name)) {

			pageSpeciality = conventionService.findAllSpecialityByName(name.trim(), paging);
		}else {
			pageSpeciality = conventionService.findAllSpeciality(paging);
		}
		
		List<Speciality> conventionList = pageSpeciality.getContent();

		
		List<Map<String, Object>> conventions= this.getMapFromSpecialityList(conventionList);

		response.put("items", conventions);
		response.put("currentPage", pageSpeciality.getNumber());
		response.put("totalItems", pageSpeciality.getTotalElements());
		response.put("totalPages", pageSpeciality.getTotalPages());
		response.put("size", pageSpeciality.getSize());
		response.put("first", pageSpeciality.isFirst());
		response.put("last", pageSpeciality.isLast());
		response.put("empty", pageSpeciality.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	protected List<Map<String, Object>> getMapFromSpecialityList(List<Speciality> convnetions) {
		List<Map<String, Object>> conventionList = new ArrayList<>();
		convnetions.stream().forEach(convnetionDto -> {

			Map<String, Object> convnetionsMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(convnetionDto.getCreatedBy()) ? new User()
					: userRepository.findById(convnetionDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(convnetionDto.getUpdatedBy()) ? new User()
					: userRepository.findById(convnetionDto.getUpdatedBy()).orElse(null);
			convnetionsMap.put("id", convnetionDto.getId());
			convnetionsMap.put("name", convnetionDto.getName());
			convnetionsMap.put("active", convnetionDto.getActive());
			convnetionsMap.put("createdAt", convnetionDto.getCreatedAt());
			convnetionsMap.put("updatedAt", convnetionDto.getUpdatedAt());
			convnetionsMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			convnetionsMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			convnetionsMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			convnetionsMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			conventionList.add(convnetionsMap);
		});
		return conventionList;
	}
	
	@PostMapping("/add")
	@ApiOperation("Ajouter une specialite")
	public ResponseEntity<Speciality> addConvnetion(@RequestBody DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		Speciality convention = conventionService.addSpeciality(defaultNameAndActiveDto);
		
		return new ResponseEntity<Speciality>(convention,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier une specialite dans le systeme")
	public ResponseEntity<Speciality>updateSpeciality(@PathVariable("id") Long id,@RequestBody DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		Speciality updateSpeciality = conventionService.updateSpeciality(id, defaultNameAndActiveDto);
		return new ResponseEntity<>(updateSpeciality,HttpStatus.OK);
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'une specialite ")
	public  ResponseEntity<Optional<Speciality>> getDetail(@PathVariable Long id){
		Optional<Speciality> convention = conventionService.getSpecialityDetails(id);
		return new ResponseEntity<>(convention,HttpStatus.OK);
	}

}
