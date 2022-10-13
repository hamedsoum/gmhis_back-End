package com.gmhis_backk.controller;


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

import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.dto.PatientDTO;
import com.gmhis_backk.exception.domain.EmailExistException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.PatientService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	PatientService patientService;
	
	@PostMapping("/add")
	@ApiOperation("Ajouter un Patient")
	public  ResponseEntity<Patient>addPatient(@RequestBody PatientDTO patienDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException,EmailExistException {
		Patient patient = patientService.save(patienDto);
		return new ResponseEntity<Patient>(patient,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier un Patient")
	public  ResponseEntity<Patient>updatePatient(@PathVariable("id") Long id,@RequestBody PatientDTO patienDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException,EmailExistException {
		Patient patient = patientService.update(id,patienDto);
		return new ResponseEntity<Patient>(patient,HttpStatus.OK);
	}
	
	@ApiOperation(value = "retourne la liste paginee des patients")
	@GetMapping("/p_list")
	public ResponseEntity<Map<String, Object>> paginatedList(
			@RequestParam(required = false, defaultValue = "") String firstName,
			@RequestParam(required = false, defaultValue = "") String lastName,
			@RequestParam(required = false, defaultValue = "") String patientExternalId,
			@RequestParam(required = false, defaultValue = "") String cellPhone,
			@RequestParam(required = false, defaultValue = "") String cnamNumber,
			@RequestParam(required = false, defaultValue = "") String idCardNumber,
			@RequestParam(defaultValue = "id,desc") String[] sort, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));

		//		Page<Patient> pPatients = pService.findAllContaining(firstName, lastName, patientExternalId, cellPhone,
		//				cnamNumber, idCardNumber, pageable);

		Page<Patient> pPatients = null;

		pPatients = patientService.findByPatientExternalId("@@@@@@@######@@@@", paging);

		if (ObjectUtils.isNotEmpty(firstName) || ObjectUtils.isNotEmpty(lastName)) {
			pPatients = patientService.findByFullName(firstName, lastName, paging);
		}

		if (ObjectUtils.isNotEmpty(patientExternalId)) {
			pPatients = patientService.findByPatientExternalId(patientExternalId, paging);
		}

		if (ObjectUtils.isNotEmpty(cellPhone)) {
			pPatients = patientService.findByCellPhone(cellPhone, paging);
		}

		if (ObjectUtils.isNotEmpty(cnamNumber)) {
			pPatients = patientService.findByCnamNumber(cnamNumber, paging);
		}

		if (ObjectUtils.isNotEmpty(idCardNumber)) {
			pPatients = patientService.findByIdCardNumber(idCardNumber, paging);
		}

		List<Patient> lPatients = pPatients.getContent();


	//	List<Map<String, Object>> patients = this.getMapFromPatientList(lPatients);
	
		
		response.put("items", lPatients);
		response.put("currentPage", pPatients.getNumber());
		response.put("totalItems", pPatients.getTotalElements());
		response.put("totalPages", pPatients.getTotalPages());
		response.put("size", pPatients.getSize());
		response.put("first", pPatients.isFirst());
		response.put("last", pPatients.isLast());
		response.put("empty", pPatients.isEmpty());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
}