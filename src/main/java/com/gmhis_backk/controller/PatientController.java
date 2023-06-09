package com.gmhis_backk.controller;


import java.util.Calendar;
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

import com.gmhis_backk.domain.Examination;
import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.dto.PatientDTO;
import com.gmhis_backk.exception.domain.EmailExistException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.PatientRepository;
import com.gmhis_backk.service.PatientService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	PatientService patientService;
	
	@Autowired 
	PatientRepository patientRepository ;
	
	
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
			@RequestParam(required = false, defaultValue = "") String idCardNumber,
			@RequestParam(required = false, defaultValue = "") String correspondant,
			@RequestParam(required = false, defaultValue = "") String emergencyContact,

			@RequestParam(defaultValue = "id,desc") String[] sort, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) throws Exception {

		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));



		Page<Patient> pPatients = null;		

		if (ObjectUtils.isNotEmpty(firstName) || ObjectUtils.isNotEmpty(lastName) || ObjectUtils.isNotEmpty(cellPhone) || ObjectUtils.isNotEmpty(correspondant) || ObjectUtils.isNotEmpty(emergencyContact) || ObjectUtils.isNotEmpty(patientExternalId) || ObjectUtils.isNotEmpty(idCardNumber)  ) {
			pPatients = patientService.findByFullName(firstName, lastName, cellPhone, correspondant, emergencyContact,patientExternalId,idCardNumber, paging);
		} else {
			throw new ResourceNotFoundByIdException("Aucun Resultat");
}
		
		List<Patient> lPatients = pPatients.getContent();

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
	

	@ApiOperation(value = "Retourne les details d'un patient")
	@GetMapping("/detail/{id}")
	public Patient detail(@PathVariable Long id) {
		Patient patient = patientService.findById(id);
		return patient;
	}


	@ApiOperation(value = "Retourne le numero du  patient")
	@GetMapping("/patientNumber")
	public String generatePatientNumber(){
		Patient lPatient = patientRepository.findLastPatient();
		Calendar calendar = Calendar.getInstance();
		String month= String.format("%02d", calendar.get( Calendar.MONTH ) + 1) ;
		String year = String.format("%02d",calendar.get( Calendar.YEAR ) % 100);
		String lPatientYearandMonth = "";
		String lPatientNb = "";
		int  number= 0;
		
		if(lPatient ==  null) {
			lPatientYearandMonth = year + month;
			lPatientNb = "0000000";
		}else {
			 String an = lPatient.getPatientExternalId().substring(2);
			 lPatientYearandMonth = an.substring(0, 4);
			 lPatientNb = an.substring(4);	
		}
		
		
		if(lPatientYearandMonth.equals( year + month)) {
			number = Integer.parseInt(lPatientNb) + 1 ;
		} else {
			number = number +1;
		}
		
		return "PT" + year + month +String.format("%04d", number);
		
	}
	
	@ApiOperation(value="La dernière consultation du patient")
	@GetMapping("/last_consultation/{id}")
	public ResponseEntity<Examination> lastConsultation(@PathVariable Long id) {
		Examination examination = patientService.findLastAdmission(id);
		return ResponseEntity.ok(examination);
	}
	
	
}
