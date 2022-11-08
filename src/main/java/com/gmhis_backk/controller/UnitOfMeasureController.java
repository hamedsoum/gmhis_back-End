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

import com.gmhis_backk.domain.UnitOfMeasure;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.UnitOfMeasureService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/unit_of_measure")
public class UnitOfMeasureController {
	
	@Autowired
	UnitOfMeasureService unitOfMeasureService;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/list")
	@ApiOperation("liste paginee de toutes les unites de mesures dans le systeme")
	public ResponseEntity<Map<String, Object>>getAllUnitOfMeasure(
			
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {
		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<UnitOfMeasure> pageUnitOfMeasure;
		
		pageUnitOfMeasure = unitOfMeasureService.findAllUnitOfMeasure(paging);
		
		if (StringUtils.isNotBlank(active)) {

			pageUnitOfMeasure = unitOfMeasureService.findAllUnitOfMeasureByActiveAndName(name.trim(), Boolean.parseBoolean(active), paging);

		} else if (StringUtils.isNotBlank(name)) {

			pageUnitOfMeasure = unitOfMeasureService.findAllUnitOfMeasureByName(name.trim(), paging);
		}else {
			pageUnitOfMeasure = unitOfMeasureService.findAllUnitOfMeasure(paging);
		}
		
		List<UnitOfMeasure> unitOfMeasueList = pageUnitOfMeasure.getContent();

		
		List<Map<String, Object>> unitOfMeasures= this.getMapFromUnitOfMeasureList(unitOfMeasueList);

		response.put("items", unitOfMeasures);
		response.put("currentPage", pageUnitOfMeasure.getNumber());
		response.put("totalItems", pageUnitOfMeasure.getTotalElements());
		response.put("totalPages", pageUnitOfMeasure.getTotalPages());
		response.put("size", pageUnitOfMeasure.getSize());
		response.put("first", pageUnitOfMeasure.isFirst());
		response.put("last", pageUnitOfMeasure.isLast());
		response.put("empty", pageUnitOfMeasure.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	protected List<Map<String, Object>> getMapFromUnitOfMeasureList(List<UnitOfMeasure> unitOfMeasures) {
		List<Map<String, Object>> unitOfMeasureList = new ArrayList<>();
		unitOfMeasures.stream().forEach(unitOfMeasureDto -> {

			Map<String, Object> unitOfMEasuresMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(unitOfMeasureDto.getCreatedBy()) ? new User()
					: userRepository.findById(unitOfMeasureDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(unitOfMeasureDto.getUpdatedBy()) ? new User()
					: userRepository.findById(unitOfMeasureDto.getUpdatedBy()).orElse(null);
			unitOfMEasuresMap.put("id", unitOfMeasureDto.getId());
			unitOfMEasuresMap.put("name", unitOfMeasureDto.getName());
			unitOfMEasuresMap.put("active", unitOfMeasureDto.getActive());
			unitOfMEasuresMap.put("createdAt", unitOfMeasureDto.getCreatedAt());
			unitOfMEasuresMap.put("updatedAt", unitOfMeasureDto.getUpdatedAt());
			unitOfMEasuresMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			unitOfMEasuresMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			unitOfMEasuresMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			unitOfMEasuresMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			unitOfMeasureList.add(unitOfMEasuresMap);
		});
		return unitOfMeasureList;
	}
	
	@PostMapping("/add")
	@ApiOperation("Ajouter une unite de mesure")
	public ResponseEntity<UnitOfMeasure> addUnitOfMeasure(@RequestBody DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		UnitOfMeasure unitOfMeasure = unitOfMeasureService.addUnitOfMeasure(defaultNameAndActiveDto);
		
		return new ResponseEntity<UnitOfMeasure>(unitOfMeasure,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier une unite de mesur")
	public ResponseEntity<UnitOfMeasure>updateUnitOfMeasure(@PathVariable("id") Long id,@RequestBody DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		UnitOfMeasure updateUnitOfMeasure = unitOfMeasureService.updateUnitOfMeasure(id, defaultNameAndActiveDto);
		return new ResponseEntity<>(updateUnitOfMeasure,HttpStatus.OK);
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'une unite de mesure ")
	public  ResponseEntity<Optional<UnitOfMeasure>> getDetail(@PathVariable Long id){
		Optional<UnitOfMeasure> unitOfMeasure= unitOfMeasureService.getUnitOfMeasureDetails(id);
		return new ResponseEntity<>(unitOfMeasure,HttpStatus.OK);
	}

	@ApiOperation(value = "Lister la liste des ids et noms des unites de mesure actifs dans le système")
	@GetMapping("/active_unitOfMeasures_name")
	public ResponseEntity<List<Map<String, Object>>> activePracticianName() {
		List<Map<String, Object>> practicianList = new ArrayList<>();

		unitOfMeasureService.findAllActiveUnitOfMeasure().stream().forEach(uOmDto -> {
			Map<String, Object> uMap = new HashMap<>();
			uMap.put("id", uOmDto.getId());
			uMap.put("name", uOmDto.getName());
			practicianList.add(uMap);
		});
		return new ResponseEntity<>(practicianList, HttpStatus.OK);
	}
}
