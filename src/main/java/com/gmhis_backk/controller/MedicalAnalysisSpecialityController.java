package com.gmhis_backk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.MedicalAnalysisSpecilaity;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.MedicalAnalysisSpecilaityService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/medicalAnalysisSpecilaty")
public class MedicalAnalysisSpecialityController {

	@Autowired
	MedicalAnalysisSpecilaityService medicalAnalysisSpecilaityService;
	@PostMapping("/add")
	@ApiOperation("/Ajouter une specilialite")
	public ResponseEntity<MedicalAnalysisSpecilaity>addDrugTtpe(@RequestBody DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException,ResourceNotFoundByIdException{
		MedicalAnalysisSpecilaity medicalAnalysisSpecilaity = medicalAnalysisSpecilaityService.addMedicalAnalysisSpecilaity(defaultNameAndActiveDto);
		return new ResponseEntity<MedicalAnalysisSpecilaity>(medicalAnalysisSpecilaity, HttpStatus.OK);
	} 
	
	@ApiOperation(value = "Lister la liste des ids et noms des specialites  d'actes actives dans le système")
	@GetMapping("/active_medical_analysis_speciality_name")
	public ResponseEntity<List<Map<String, Object>>>  activeMedicalAnalysisSpecilaityName() {
		List<Map<String, Object>>  medicalAnalysisSpecialityList = new ArrayList<>();

		medicalAnalysisSpecilaityService.findAllActive().forEach(medicalAnalysisSpeciality -> {
			Map<String, Object> medicalAnalysisSpecilality = new HashMap<>();
			medicalAnalysisSpecilality.put("id", medicalAnalysisSpeciality.getId());
			medicalAnalysisSpecilality.put("name", medicalAnalysisSpeciality.getName());
			medicalAnalysisSpecialityList.add(medicalAnalysisSpecilality);
		});
		
		return new ResponseEntity<>(medicalAnalysisSpecialityList, HttpStatus.OK);
	}
}
