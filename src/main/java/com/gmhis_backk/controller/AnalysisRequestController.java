package com.gmhis_backk.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.AnalysisRequest;
import com.gmhis_backk.dto.AnalysisRequestDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.AdmissionService;
import com.gmhis_backk.service.AnalysisRequestService;
import com.gmhis_backk.service.PatientService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Adjara
 *
 */
@RestController
@RequestMapping("/analysis-request")
public class AnalysisRequestController {

	@Autowired
	private AnalysisRequestService analysisRequestService;

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private AdmissionService admissionService;
	
	@Autowired
	UserRepository 	userRepository;

	
	@ApiOperation(value = "Ajouter les demandes d'analyses")
	@PostMapping("/add")
	public AnalysisRequest addAnalysisRequests(@RequestBody AnalysisRequestDTO analysisRequestDto)  throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
     
		AnalysisRequest analys =  analysisRequestService.saveAnalysisRequest(analysisRequestDto);
		
		return null;
	}

	

}
