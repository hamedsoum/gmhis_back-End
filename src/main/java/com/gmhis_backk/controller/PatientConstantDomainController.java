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

import com.gmhis_backk.domain.PatientConstantDomain;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.PatientConstantDomainDto;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.PatientConstantDomainService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/constant_domain")
public class PatientConstantDomainController {
	
	@Autowired
	PatientConstantDomainService patientConstantDomainService;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/list")
	@ApiOperation("liste paginee de tous groupes de  constante dans le systeme")
	public ResponseEntity<Map<String, Object>>getAllPatientConstant(
			
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {
		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<PatientConstantDomain> pageConstantDomain;
		
		pageConstantDomain = patientConstantDomainService.findAllConstatDomain(paging);
		
		if (StringUtils.isNotBlank(active)) {

			pageConstantDomain = patientConstantDomainService.findAllConstatDomainByActiveAndName(name.trim(), Boolean.parseBoolean(active), paging);

		} else if (StringUtils.isNotBlank(name)) {

			pageConstantDomain = patientConstantDomainService.findAllConstatDomainByName(name.trim(), paging);
		}else {
			pageConstantDomain = patientConstantDomainService.findAllConstatDomain(paging);
		}
		
		List<PatientConstantDomain> constantDomainList = pageConstantDomain.getContent();

		
		List<Map<String, Object>> constantDomains= this.getMapConstantDomainList(constantDomainList);

		response.put("items", constantDomains);
		response.put("currentPage", pageConstantDomain.getNumber());
		response.put("totalItems", pageConstantDomain.getTotalElements());
		response.put("totalPages", pageConstantDomain.getTotalPages());
		response.put("size", pageConstantDomain.getSize());
		response.put("first", pageConstantDomain.isFirst());
		response.put("last", pageConstantDomain.isLast());
		response.put("empty", pageConstantDomain.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	
	protected List<Map<String, Object>> getMapConstantDomainList(List<PatientConstantDomain> constantDomains) {
		List<Map<String, Object>> actGroupList = new ArrayList<>();
		constantDomains.stream().forEach(constantDomainDto -> {

			Map<String, Object> constantDomainsMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(constantDomainDto.getCreatedBy()) ? new User()
					: userRepository.findById(constantDomainDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(constantDomainDto.getUpdatedBy()) ? new User()
					: userRepository.findById(constantDomainDto.getUpdatedBy()).orElse(null);
			constantDomainsMap.put("id", constantDomainDto.getId());
			constantDomainsMap.put("name", constantDomainDto.getName());
			constantDomainsMap.put("description", constantDomainDto.getDescription());
			constantDomainsMap.put("active", constantDomainDto.getActive());
			constantDomainsMap.put("createdAt", constantDomainDto.getCreatedAt());
			constantDomainsMap.put("updatedAt", constantDomainDto.getUpdatedAt());
			constantDomainsMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			constantDomainsMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			constantDomainsMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			constantDomainsMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			actGroupList.add(constantDomainsMap);
		});
		return actGroupList;
	}
	
	@PostMapping("/add")
	@ApiOperation("Ajouter un groupe de constant")
	public ResponseEntity<PatientConstantDomain> addActGroup(@RequestBody PatientConstantDomainDto PatientConstantDomainDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		PatientConstantDomain constantDomain = patientConstantDomainService.addConstatDomain(PatientConstantDomainDto);
		
		return new ResponseEntity<PatientConstantDomain>(constantDomain,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier un groupe de constant dans le systeme")
	public ResponseEntity<PatientConstantDomain>updateGroup(@PathVariable("id") Long id,@RequestBody PatientConstantDomainDto patientConstantDomainDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		PatientConstantDomain updateConstantDomain = patientConstantDomainService.updateConstantDomain(id, patientConstantDomainDto);
		return new ResponseEntity<>(updateConstantDomain,HttpStatus.OK);
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'un groupe de constant ")
	public  ResponseEntity<Optional<PatientConstantDomain>> getDetail(@PathVariable Long id){
		Optional<PatientConstantDomain> constantDomain = patientConstantDomainService.getConstatDomainDetails(id);
		return new ResponseEntity<>(constantDomain,HttpStatus.OK);
	}
 
}
