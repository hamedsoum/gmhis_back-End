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

import com.gmhis_backk.domain.ActGroup;
import com.gmhis_backk.domain.Insurance;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.ActGroupDto;
import com.gmhis_backk.dto.InsuranceDto;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.InsuranceService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/insurance")
public class InsuranceController {
	
	@Autowired
	InsuranceService insuranceServcie;
	
	@Autowired
	UserRepository userRepository;

	@GetMapping("/list")
	@ApiOperation("liste paginee de toutes les assurance dans le systeme")
	public ResponseEntity<Map<String, Object>>getAllInsurance(
			
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {
		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<Insurance> pageInsurance;
		
		pageInsurance = insuranceServcie.findAllInsurance(paging);
		
		if (StringUtils.isNotBlank(active)) {

			pageInsurance = insuranceServcie.findAllInsuranceByActiveAndName(name.trim(), Boolean.parseBoolean(active), paging);

		} else if (StringUtils.isNotBlank(name)) {

			pageInsurance = insuranceServcie.findAllInsuranceByName(name.trim(), paging);
		}else {
			pageInsurance = insuranceServcie.findAllInsurance(paging);
		}
		
		List<Insurance> insuranceList = pageInsurance.getContent();

		
		List<Map<String, Object>> insurances= this.getMapFromInsuranceList(insuranceList);

		response.put("items", insurances);
		response.put("currentPage", pageInsurance.getNumber());
		response.put("totalItems", pageInsurance.getTotalElements());
		response.put("totalPages", pageInsurance.getTotalPages());
		response.put("size", pageInsurance.getSize());
		response.put("first", pageInsurance.isFirst());
		response.put("last", pageInsurance.isLast());
		response.put("empty", pageInsurance.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	
	protected List<Map<String, Object>> getMapFromInsuranceList(List<Insurance> insurances) {
		List<Map<String, Object>> insuranceList = new ArrayList<>();
		insurances.stream().forEach(insuranceDto -> {

			Map<String, Object> insurancesMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(insuranceDto.getCreatedBy()) ? new User()
					: userRepository.findById(insuranceDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(insuranceDto.getUpdatedBy()) ? new User()
					: userRepository.findById(insuranceDto.getUpdatedBy()).orElse(null);
			insurancesMap.put("id", insuranceDto.getId());
			insurancesMap.put("name", insuranceDto.getName());
			insurancesMap.put("active", insuranceDto.getActive());
			insurancesMap.put("code", insuranceDto.getCode());
			insurancesMap.put("compte", insuranceDto.getAccount());
			insurancesMap.put("adresse", insuranceDto.getAddress());
			insurancesMap.put("createdAt", insuranceDto.getCreatedAt());
			insurancesMap.put("updatedAt", insuranceDto.getUpdatedAt());
			insurancesMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			insurancesMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			insurancesMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			insurancesMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			insuranceList.add(insurancesMap);
		});
		return insuranceList;
	}
	
	@PostMapping("/add")
	@ApiOperation("Ajouter une assurance")
	public ResponseEntity<Insurance> addInsurance(@RequestBody InsuranceDto insuranceDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		Insurance insurance = insuranceServcie.addInsurance(insuranceDto);
		
		return new ResponseEntity<Insurance>(insurance,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier une assurance")
	public ResponseEntity<Insurance>updateGroup(@PathVariable("id") Long id,@RequestBody InsuranceDto insuranceDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		Insurance insurance = insuranceServcie.updateInsurance(id, insuranceDto);
		return new ResponseEntity<>(insurance,HttpStatus.OK);
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'une assurance ")
	public  ResponseEntity<Optional<Insurance>> getDetail(@PathVariable Long id){
		Optional<Insurance> insurance= insuranceServcie.getInsuranceDetails(id);
		return new ResponseEntity<>(insurance,HttpStatus.OK);
	}

}
