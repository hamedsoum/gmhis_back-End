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

import com.gmhis_backk.domain.Symptom;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.SymptomService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/symptom")
public class SymptomController {
	
@Autowired
UserRepository userRepository;

@Autowired
SymptomService symptomService;

	@GetMapping("/list")
	@ApiOperation("liste paginee de tous les symptoms dans le systeme")
	public ResponseEntity<Map<String, Object>>getAllSymtomß(
			@RequestParam(name = "name", required = false, defaultValue = "") String pathologyName,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort
			){
		
		Map<String, Object> response = new HashMap<>();
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));

		Page<Symptom> pageSymptom;
		
		pageSymptom = symptomService.findAllSymptom(paging);
		
		if (StringUtils.isNotBlank(active)) {
			pageSymptom = symptomService.findAllSymptomByActiveAndName(pathologyName.trim(), Boolean.parseBoolean(active), paging);
		} else if(StringUtils.isNotBlank(active)) {
			pageSymptom = symptomService.findAllSymptomByName(pathologyName.trim(), paging);
		}

		List<Symptom> SymptomList = pageSymptom.getContent();
		
		List<Map<String, Object>> unitOfMeasures= this.getMapFromSymptomList(SymptomList);

		response.put("items", unitOfMeasures);
		response.put("currentPage", pageSymptom.getNumber());
		response.put("totalItems", pageSymptom.getTotalElements());
		response.put("totalPages", pageSymptom.getTotalPages());
		response.put("size", pageSymptom.getSize());
		response.put("first", pageSymptom.isFirst());
		response.put("last", pageSymptom.isLast());
		response.put("empty", pageSymptom.isEmpty());

		return new ResponseEntity<>(response, OK);

	}
	protected List<Map<String, Object>> getMapFromSymptomList(List<Symptom> pathologies) {
		List<Map<String, Object>> unitOfMeasureList = new ArrayList<>();
		pathologies.stream().forEach(pathologyDto -> {

			Map<String, Object> pathologiesMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(pathologyDto.getCreatedBy()) ? new User()
					: userRepository.findById(pathologyDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(pathologyDto.getLastUpdatedBy()) ? new User()
					: userRepository.findById(pathologyDto.getLastUpdatedBy()).orElse(null);
			pathologiesMap.put("id", pathologyDto.getId());
			pathologiesMap.put("name", pathologyDto.getName());
			pathologiesMap.put("active", pathologyDto.getActive());
			pathologiesMap.put("createdAt", pathologyDto.getCreatedAt());
			pathologiesMap.put("updatedAt", pathologyDto.getLastUpdatedAt());
			pathologiesMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			pathologiesMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			pathologiesMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			pathologiesMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			unitOfMeasureList.add(pathologiesMap);
		});
		return unitOfMeasureList;
	}

	@PostMapping("/add")
	@ApiOperation("Ajouter un symptom")
	public ResponseEntity<Symptom> addSymptom(@RequestBody DefaultNameAndActiveDto SymptomDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		Symptom pathology = symptomService.addSymptom(SymptomDto);
		return new ResponseEntity<Symptom>(pathology, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	@ApiOperation("Modifier un symptom dans le systeme")
	public ResponseEntity<Symptom>updateSymptom(@PathVariable("id") Long id,@RequestBody DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		Symptom updateSymptom = symptomService.updateSymptom(id, defaultNameAndActiveDto);
		return new ResponseEntity<>(updateSymptom,HttpStatus.OK);
	}

	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'une pathology")
	public  ResponseEntity<Optional<Symptom>> getDetail(@PathVariable Long id){
		Optional<Symptom> pathology = symptomService.getSymptomDetails(id);
		return new ResponseEntity<>(pathology,HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste des ids et noms des symptoms actives dans le système")
	@GetMapping("/active_symptoms_name")
	public ResponseEntity<List<Map<String, Object>>>  activeActSymptomName() {
		List<Map<String, Object>>  SymptomList = new ArrayList<>();

		symptomService.findAllActive().forEach(symptomDto -> {
			Map<String, Object> symptomMap = new HashMap<>();
			symptomMap.put("id", symptomDto.getId());
			symptomMap.put("name", symptomDto.getName());
			SymptomList.add(symptomMap);
		});
		
		return new ResponseEntity<>(SymptomList, HttpStatus.OK);
	}

}
