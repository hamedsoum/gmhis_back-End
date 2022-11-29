package com.gmhis_backk.controller;


import static org.springframework.http.HttpStatus.OK;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.Admission;
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
 * @author Hamed soumahoro
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
	public ResponseEntity<AnalysisRequest> addAnalysisRequests(@RequestBody AnalysisRequestDTO analysisRequestDto)  throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
     
		AnalysisRequest analys =  analysisRequestService.saveAnalysisRequest(analysisRequestDto);
		
		return new ResponseEntity<AnalysisRequest>(analys, OK);
	}
	
	
	@ApiOperation(value = "Liste paginee de toutes les demandes d'analyses")
	@GetMapping("/p_list")
	public ResponseEntity<Map<String, Object>>p_list(
			 @RequestParam(required = false, defaultValue = "") String date,
				@RequestParam(required = false, defaultValue = "N") String  state,
				@RequestParam(required = false, defaultValue = "") String firstName,
				@RequestParam(required = false, defaultValue = "") String lastName,
				@RequestParam(required = false, defaultValue = "") String patientExternalId,
				@RequestParam(required = false, defaultValue = "") String cellPhone,
				@RequestParam(required = false, defaultValue = "") String cnamNumber,
				@RequestParam(required = false, defaultValue = "") String idCardNumber,
				@RequestParam(required = false, defaultValue = "") String admissionNumber,
				@RequestParam(defaultValue = "id,desc") String[] sort, @RequestParam(defaultValue = "0") int page,
				@RequestParam(defaultValue = "50") int size){
		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		
		Page<AnalysisRequest> p_analysisRequest = null; 
		
		if (ObjectUtils.isNotEmpty(admissionNumber.trim())) {
			p_analysisRequest = analysisRequestService.findAnalysisRequestsByAdmissionNumber(admissionNumber, paging);
		}else {
			p_analysisRequest = analysisRequestService.findAll(paging);
		}			
		
	    List<AnalysisRequest> lAnalyst = p_analysisRequest.getContent();
	    
		List<Map<String, Object>> analysis = this.getMapFromAnalystRequestAllList(lAnalyst);
		response.put("items", analysis);
		response.put("currentPage", p_analysisRequest.getNumber());
		response.put("totalItems", p_analysisRequest.getTotalElements());
		response.put("totalPages", p_analysisRequest.getTotalPages());
		response.put("size", p_analysisRequest.getSize());
		response.put("first", p_analysisRequest.isFirst());
		response.put("last", p_analysisRequest.isLast());
		response.put("empty", p_analysisRequest.isEmpty());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	protected List<Map<String, Object>> getMapFromAnalystRequestAllList(List<AnalysisRequest> analystResquest) {
		List<Map<String, Object>> analystRequestList = new ArrayList<>();
		analystResquest.stream().forEach(analystDto -> {
			Map<String, Object> analystMap = new HashMap<>();
			analystMap.put("id", analystDto.getId());
			analystMap.put("act", analystDto.getAct().getName());
			analystMap.put("date", analystDto.getCreatedAt());
			analystMap.put("facilityName", analystDto.getAdmission().getFacility().getName());
			analystMap.put("patientNumber", analystDto.getAdmission().getPatient().getPatientExternalId());
			analystMap.put("admissionNumber", analystDto.getAdmission().getAdmissionNumber());
			analystMap.put("idCardNumber", analystDto.getAdmission().getPatient().getIdCardNumber());
			analystRequestList.add(analystMap);
		});
		return analystRequestList;
	}
	
	@ApiOperation(value = "list paginee des demandes d'analyses d'un patient")
	@GetMapping("getPatientAnalysisRequest")
	public ResponseEntity<Map<String, Object>>analyseRequestByPatient(
			 @RequestParam(required = false, defaultValue = "") String date,
			    @RequestParam(required = true, defaultValue = "") String patientId,
				@RequestParam(defaultValue = "id,desc") String[] sort, @RequestParam(defaultValue = "0") int page,
				@RequestParam(defaultValue = "50") int size){
		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		System.out.print(patientId);
		Page<AnalysisRequest> p_analysisRequest = analysisRequestService.findAnalysisRequestsByPatient(Long.parseLong(patientId), paging);
//		
	    List<AnalysisRequest> lAnalyst = p_analysisRequest.getContent();
	    
		List<Map<String, Object>> analysis = this.getMapFromAnalystRequestList(lAnalyst);
		response.put("items", analysis);
		response.put("currentPage", p_analysisRequest.getNumber());
		response.put("totalItems", p_analysisRequest.getTotalElements());
		response.put("totalPages", p_analysisRequest.getTotalPages());
		response.put("size", p_analysisRequest.getSize());
		response.put("first", p_analysisRequest.isFirst());
		response.put("last", p_analysisRequest.isLast());
		response.put("empty", p_analysisRequest.isEmpty());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	protected List<Map<String, Object>> getMapFromAnalystRequestList(List<AnalysisRequest> analystResquest) {
		List<Map<String, Object>> analystRequestList = new ArrayList<>();
		analystResquest.stream().forEach(analystDto -> {
			Map<String, Object> analystMap = new HashMap<>();
			analystMap.put("id", analystDto.getId());
			analystMap.put("act", analystDto.getAct().getName());
			analystMap.put("date", analystDto.getCreatedAt());
			analystMap.put("facilityName", analystDto.getAdmission().getFacility().getName());
			analystMap.put("state", analystDto.isState());
			analystRequestList.add(analystMap);
		});
		return analystRequestList;
	}
	
	

	@GetMapping("/getanalyseRequestNumber/{patientId}")
	@ApiOperation("nombre de consultation d'un patient ")
	public  Long getDetail(@PathVariable Long patientId){
	
	return analysisRequestService.findAnalyseNumber(patientId);
	}
	

}