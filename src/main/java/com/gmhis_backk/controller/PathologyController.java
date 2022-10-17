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

import com.gmhis_backk.domain.Pathology;
import com.gmhis_backk.domain.UnitOfMeasure;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.PathologyService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pathology")
public class PathologyController {
	
@Autowired
UserRepository userRepository;

@Autowired
PathologyService pathologyService;

@GetMapping("/list")
@ApiOperation("liste paginee de toutes les pathology dans le systeme")
public ResponseEntity<Map<String, Object>>getAllPathology(
		@RequestParam(name = "name", required = false, defaultValue = "") String pathologyName,
		@RequestParam(required = false, defaultValue = "") String active,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "id,desc") String[] sort
		){
	
	Map<String, Object> response = new HashMap<>();
	
	Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

	Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));

	Page<Pathology> pagePathology;
	
	pagePathology = pathologyService.findAllPathology(paging);
	
	if (StringUtils.isNotBlank(active)) {
		pagePathology = pathologyService.findAllPathologyByActiveAndName(pathologyName.trim(), Boolean.parseBoolean(active), paging);
	} else if(StringUtils.isNotBlank(active)) {
		pagePathology = pathologyService.findAllPathologyByName(pathologyName.trim(), paging);
	}

	List<Pathology> unitOfMeasueList = pagePathology.getContent();
	
	List<Map<String, Object>> unitOfMeasures= this.getMapFromPathologyList(unitOfMeasueList);

	response.put("items", unitOfMeasures);
	response.put("currentPage", pagePathology.getNumber());
	response.put("totalItems", pagePathology.getTotalElements());
	response.put("totalPages", pagePathology.getTotalPages());
	response.put("size", pagePathology.getSize());
	response.put("first", pagePathology.isFirst());
	response.put("last", pagePathology.isLast());
	response.put("empty", pagePathology.isEmpty());

	return new ResponseEntity<>(response, OK);

}
protected List<Map<String, Object>> getMapFromPathologyList(List<Pathology> pathologies) {
	List<Map<String, Object>> unitOfMeasureList = new ArrayList<>();
	pathologies.stream().forEach(pathologyDto -> {

		Map<String, Object> pathologiesMap = new HashMap<>();
		User createdBy = ObjectUtils.isEmpty(pathologyDto.getCreatedBy()) ? new User()
				: userRepository.findById(pathologyDto.getCreatedBy()).orElse(null);
		User updatedBy = ObjectUtils.isEmpty(pathologyDto.getUpdatedBy()) ? new User()
				: userRepository.findById(pathologyDto.getUpdatedBy()).orElse(null);
		pathologiesMap.put("id", pathologyDto.getId());
		pathologiesMap.put("name", pathologyDto.getName());
		pathologiesMap.put("active", pathologyDto.getActive());
		pathologiesMap.put("createdAt", pathologyDto.getCreatedAt());
		pathologiesMap.put("updatedAt", pathologyDto.getUpdatedAt());
		pathologiesMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
		pathologiesMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
		pathologiesMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
		pathologiesMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
		unitOfMeasureList.add(pathologiesMap);
	});
	return unitOfMeasureList;
}

@PostMapping("/add")
@ApiOperation("Ajouter une pathologie dans")
public ResponseEntity<Pathology> addPathology(@RequestBody DefaultNameAndActiveDto PathologyDto) throws ResourceNameAlreadyExistException,
ResourceNotFoundByIdException {
	Pathology pathology = pathologyService.addPathology(PathologyDto);
	return new ResponseEntity<Pathology>(pathology, HttpStatus.OK);
}

@PutMapping("/update/{id}")
@ApiOperation("Modifier une pathologie dans le systeme")
public ResponseEntity<Pathology>updatePathology(@PathVariable("id") Long id,@RequestBody DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
	Pathology updatePathology = pathologyService.updatePathology(id, defaultNameAndActiveDto);
	return new ResponseEntity<>(updatePathology,HttpStatus.OK);
}

@GetMapping("/get-detail/{id}")
@ApiOperation("detail d'une pathology")
public  ResponseEntity<Optional<Pathology>> getDetail(@PathVariable Long id){
	Optional<Pathology> pathology = pathologyService.getPathologyDetails(id);
	return new ResponseEntity<>(pathology,HttpStatus.OK);
}




}
