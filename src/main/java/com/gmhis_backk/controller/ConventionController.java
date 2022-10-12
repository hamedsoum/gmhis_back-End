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

import com.gmhis_backk.domain.Convention;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ConventionService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/convention")
public class ConventionController {
	
	@Autowired
	ConventionService conventionService;
	
	@Autowired 
	UserRepository userRepository;

	
	@GetMapping("/list")
	@ApiOperation("liste paginee de toutes les conventions  dans le systeme")
	public ResponseEntity<Map<String, Object>>getAllConvention(
			
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {
		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<Convention> pageConvention;
		
		pageConvention = conventionService.findAllConvention(paging);
		
		if (StringUtils.isNotBlank(active)) {

			pageConvention = conventionService.findAllConventionByActiveAndName(name.trim(), Boolean.parseBoolean(active), paging);

		} else if (StringUtils.isNotBlank(name)) {

			pageConvention = conventionService.findAllConventionByName(name.trim(), paging);
		}else {
			pageConvention = conventionService.findAllConvention(paging);
		}
		
		List<Convention> conventionList = pageConvention.getContent();

		
		List<Map<String, Object>> conventions= this.getMapFromConventionList(conventionList);

		response.put("items", conventions);
		response.put("currentPage", pageConvention.getNumber());
		response.put("totalItems", pageConvention.getTotalElements());
		response.put("totalPages", pageConvention.getTotalPages());
		response.put("size", pageConvention.getSize());
		response.put("first", pageConvention.isFirst());
		response.put("last", pageConvention.isLast());
		response.put("empty", pageConvention.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	protected List<Map<String, Object>> getMapFromConventionList(List<Convention> convnetions) {
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
	@ApiOperation("Ajouter une convnetion")
	public ResponseEntity<Convention> addConvnetion(@RequestBody DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		Convention convention = conventionService.addConvention(defaultNameAndActiveDto);
		
		return new ResponseEntity<Convention>(convention,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier une convnetion dans le systeme")
	public ResponseEntity<Convention>updateConvention(@PathVariable("id") Long id,@RequestBody DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		Convention updateConvention = conventionService.updateConvention(id, defaultNameAndActiveDto);
		return new ResponseEntity<>(updateConvention,HttpStatus.OK);
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'une convnetion ")
	public  ResponseEntity<Optional<Convention>> getDetail(@PathVariable Long id){
		Optional<Convention> convention = conventionService.getConventionDetails(id);
		return new ResponseEntity<>(convention,HttpStatus.OK);
	}

}
