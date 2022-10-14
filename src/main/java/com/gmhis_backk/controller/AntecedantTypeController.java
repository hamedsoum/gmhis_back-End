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

import com.gmhis_backk.domain.AntecedantType;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.AntecedantTypeDTO;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.AntecendantTypeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/AntecedantType")
public class AntecedantTypeController {
	
	@Autowired
	AntecendantTypeService antecedantTypeService;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/list")
	@ApiOperation("liste paginee de toutes les familles antecedant dans le systeme")
	public ResponseEntity<Map<String, Object>>getAllActCategory(
			
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {
		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<AntecedantType> pageActAntecedantType;
		
		pageActAntecedantType = antecedantTypeService.findAllAntecedantType(paging);
		
		if (StringUtils.isNotBlank(active)) {

			pageActAntecedantType = antecedantTypeService.findAllAntecedantTypeByActiveAndName(name.trim(), Boolean.parseBoolean(active), paging);

		} else if (StringUtils.isNotBlank(name)) {

			pageActAntecedantType = antecedantTypeService.findAllAntecedantTypeByName(name.trim(), paging);
		}else {
			pageActAntecedantType = antecedantTypeService.findAllAntecedantType(paging);
		}
		
		List<AntecedantType> antecedantTypeList = pageActAntecedantType.getContent();

		
		List<Map<String, Object>>antecedantTypes= this.getMapFromAntecedantTypeList(antecedantTypeList);

		response.put("items", antecedantTypes);
		response.put("currentPage", pageActAntecedantType.getNumber());
		response.put("totalItems", pageActAntecedantType.getTotalElements());
		response.put("totalPages", pageActAntecedantType.getTotalPages());
		response.put("size", pageActAntecedantType.getSize());
		response.put("first", pageActAntecedantType.isFirst());
		response.put("last", pageActAntecedantType.isLast());
		response.put("empty", pageActAntecedantType.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	protected List<Map<String, Object>> getMapFromAntecedantTypeList(List<AntecedantType> antecedantType) {
		List<Map<String, Object>> antecedantTypeList = new ArrayList<>();
		antecedantType.stream().forEach(antecedantDto -> {

			Map<String, Object> antecedantTypesMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(antecedantDto.getCreatedBy()) ? new User()
					: userRepository.findById(antecedantDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(antecedantDto.getUpdatedBy()) ? new User()
					: userRepository.findById(antecedantDto.getUpdatedBy()).orElse(null);
			antecedantTypesMap.put("id", antecedantDto.getId());
			antecedantTypesMap.put("name", antecedantDto.getName());
			antecedantTypesMap.put("active", antecedantDto.getActive());
			antecedantTypesMap.put("description", antecedantDto.getDescription());
			antecedantTypesMap.put("createdAt", antecedantDto.getCreatedAt());
			antecedantTypesMap.put("updatedAt", antecedantDto.getUpdatedAt());
			antecedantTypesMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			antecedantTypesMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			antecedantTypesMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			antecedantTypesMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			antecedantTypeList.add(antecedantTypesMap);
		});
		return antecedantTypeList;
	}
	
	@PostMapping("/add")
	@ApiOperation("Ajouter une famille antecedent")
	public ResponseEntity<AntecedantType> addAntecedentType(@RequestBody AntecedantTypeDTO antecedantTypeDTO) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		AntecedantType antecedantType = antecedantTypeService.addAntecedantType(antecedantTypeDTO);
		
		return new ResponseEntity<AntecedantType>(antecedantType,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier une famille d'antecedant dans le systeme")
	public ResponseEntity<AntecedantType>updateAntecedentType(@PathVariable("id") Long id,@RequestBody AntecedantTypeDTO antecedantTypeDTO) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		AntecedantType updateAntecedent = antecedantTypeService.updateAntecedantType(id, antecedantTypeDTO);
		return new ResponseEntity<>(updateAntecedent,HttpStatus.OK);
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'une famille d'antecedent ")
	public  ResponseEntity<Optional<AntecedantType>> getDetail(@PathVariable Long id){
		Optional<AntecedantType> antecedentType= antecedantTypeService.getAntecedantTypeDetails(id);
		return new ResponseEntity<>(antecedentType,HttpStatus.OK);
	}

}
