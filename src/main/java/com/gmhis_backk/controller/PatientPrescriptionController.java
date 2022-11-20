package com.gmhis_backk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.PatientPrescription;
import com.gmhis_backk.dto.PatientPrescriptionDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.PatientPrescriptionService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/patientPrescription")

public class PatientPrescriptionController {

	@Autowired
	PatientPrescriptionService patientPrescriptionService;
	
	@Autowired
	UserRepository userRepository;
	
	
	@PostMapping("/add")
	@ApiOperation("Ajouter un acte")
	public ResponseEntity<PatientPrescription> addActCode(@RequestBody PatientPrescriptionDTO pDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		PatientPrescription patientPrescription = patientPrescriptionService.savePatientPrescription(pDto);
		return new ResponseEntity<PatientPrescription>(patientPrescription,HttpStatus.OK);
	}
}
