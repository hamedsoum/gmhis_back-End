package com.gmhis_backk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.gmhis_backk.domain.PatientConstantType;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.PatientConstantTypeDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.PatientConstantTypeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/patient_constant_type")
public class PatientConstantTypeController {
	
	@Autowired
	PatientConstantTypeService patientConstantTypeService;
	
	@Autowired
	UserRepository userRepository;
	

	
	@PostMapping("/add")
	@ApiOperation("Ajouter un type de constante")
	public ResponseEntity<PatientConstantType>addPatientConstantType(@RequestBody PatientConstantTypeDTO pcDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		PatientConstantType patientConstantType = patientConstantTypeService.save(pcDto);
		return new ResponseEntity<PatientConstantType>(patientConstantType,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier un type de constante")
	public ResponseEntity<PatientConstantType>updateGroup(@PathVariable("id") Long id,@RequestBody PatientConstantTypeDTO pcDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		PatientConstantType updatePatientConstantType = patientConstantTypeService.update(id, pcDto);
		return new ResponseEntity<>(updatePatientConstantType,HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste paginee de toutes les constantes types dans le système")
	@GetMapping("/p_list")
	public ResponseEntity<Map<String, Object>> paginatedList(
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false) Boolean active,
			@RequestParam(required = false) Long domain,
			@RequestParam(defaultValue = "id,desc") String[] sort, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		

		Page<PatientConstantType> pPatientConstantTypes = null;

		if (ObjectUtils.isEmpty(active) && ObjectUtils.isEmpty(domain)) {
			//pPatientConstantTypes = constantTypeService.findAll(pageable);
			pPatientConstantTypes = patientConstantTypeService.findPatientConstantTypesContaining(name, paging);
		} else if (ObjectUtils.isNotEmpty(active)  && ObjectUtils.isEmpty(domain)) {
			pPatientConstantTypes = patientConstantTypeService.findByActive(name, active, paging);
		} else if (ObjectUtils.isEmpty(active)  && ObjectUtils.isNotEmpty(domain)) {
			pPatientConstantTypes = patientConstantTypeService.findByDomain(domain, name, paging);
		} else if (ObjectUtils.isNotEmpty(active) && ObjectUtils.isNotEmpty(domain)) {
			pPatientConstantTypes = patientConstantTypeService.findByDomainAndActive(domain, name, active, paging);
		}
		
		List<PatientConstantType> lPatientConstantTypes = pPatientConstantTypes.getContent();
	

		List<Map<String, Object>> constantTypes = this.getMapFromPatientConstantTypeList(lPatientConstantTypes);
		response.put("content", constantTypes);
		response.put("totalElements", pPatientConstantTypes.getTotalElements());
		response.put("totalPages", pPatientConstantTypes.getTotalPages());
		response.put("size", pPatientConstantTypes.getSize());
		response.put("pageNumber", pPatientConstantTypes.getNumber());
		response.put("numberOfElements", pPatientConstantTypes.getNumberOfElements());
		response.put("first", pPatientConstantTypes.isFirst());
		response.put("last", pPatientConstantTypes.isLast());
		response.put("empty", pPatientConstantTypes.isEmpty());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	protected List<Map<String, Object>> getMapFromPatientConstantTypeList(List<PatientConstantType> constantTypes) {
		List<Map<String, Object>> constantTypeList = new ArrayList<>();
		constantTypes.stream().forEach(constType -> {
			Map<String, Object> constantTypeMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(constType.getCreatedBy()) ? new User()
					: userRepository.findById(constType.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(constType.getUpdatedBy()) ? new User()
					: userRepository.findById(constType.getUpdatedBy()).orElse(null);
			constantTypeMap.put("id", constType.getId());
			constantTypeMap.put("name", constType.getName());
			constantTypeMap.put("active", constType.getActive());
			constantTypeMap.put("constantDomain", constType.getPatientConstantDomain().getName());
			constantTypeMap.put("constantUnit", constType.getUnitOfMesure().getName());
//			constantTypeMap.put("resultType", constType.getResultType());
			constantTypeMap.put("description", constType.getDescription());
			constantTypeMap.put("shortName", constType.getShortName());
			constantTypeMap.put("significantDigits", constType.getSignificantDigits());
//			constantTypeMap.put("resultType", constType.getResultType().getCode());
			constantTypeMap.put("createdAt", constType.getCreatedAt());
			constantTypeMap.put("options", constType.getOptions());
			constantTypeMap.put("updatedAt", constType.getUpdatedAt());
			constantTypeMap.put("createdByLogin", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLogin());
			constantTypeMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			constantTypeMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			constantTypeMap.put("UpdatedByLogin", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLogin());
			constantTypeMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			constantTypeMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			constantTypeList.add(constantTypeMap);
		});
		return constantTypeList;
	}
	
	@ApiOperation(value = "Lister la liste des ids et noms des types contantes actifs dans le système")
	@GetMapping("/active_constante_type_name")
	public ResponseEntity<List<Map<String, Object>>> activeActName() {
		List<Map<String, Object>> actList = new ArrayList<>();

		patientConstantTypeService.findActivePatientConstantTypes().stream().forEach(actDto -> {
			Map<String, Object> actMap = new HashMap<>();
			actMap.put("id", actDto.getId());
			actMap.put("name", actDto.getName());
			actList.add(actMap);
		});

		return new ResponseEntity<>(actList, HttpStatus.OK);
	}

}
