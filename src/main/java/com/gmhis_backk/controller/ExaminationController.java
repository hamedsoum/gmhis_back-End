package com.gmhis_backk.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Admission;
import com.gmhis_backk.domain.Examination;
import com.gmhis_backk.domain.Facility;
import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.ExaminationDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.AdmissionService;
import com.gmhis_backk.service.ExaminationService;
import com.gmhis_backk.service.PracticianService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@RestController
@RequestMapping("/examination")
public class ExaminationController {
	
	@Autowired
	private ExaminationService examinationService;
		
	@Autowired
     private AdmissionService admissionService;
	
	@Autowired
    private PracticianService practicianService;
	
	
	@Autowired 
	private UserRepository userRepository;
	
	private Examination examination = null;

	
	@ApiOperation(value = "Ajouter une consultation d'un patient")
	@PostMapping("/add")
	public ResponseEntity<Examination> addExaminations(@RequestBody ExaminationDTO examinationDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException{
//	    System.out.println(examinationDto.toString());
	    
	    	System.out.print(this.getCurrentUserId().getFacilityId());
		
		 Admission admission = admissionService.findAdmissionById(examinationDto.getAdmission()).orElse(null);
			if (admission == null) {
				throw new ResourceNotFoundByIdException("aucune admission d'acte trouv?? pour l'identifiant " );
			}
			
			 Pratician practician = practicianService.findPracticianById(examinationDto.getPratician()).orElse(null);
				if (practician == null) {
					throw new ResourceNotFoundByIdException("aucun practician trouv?? pour l'utilisateur connecte " );
				}
				
//				Facility facility = new Facility();
				Facility facility = new Facility();
//				facility.setId( (long) 1); 
				examination = new Examination();
				examination.setAdmission(admission);
				examination.setConclusion(examinationDto.getConclusion());
				examination.setEndDate(examinationDto.getEndDate());
				examination.setExaminationReasons(examinationDto.getExaminationReasons());
				examination.setExaminationType(examinationDto.getExaminationType());
				examination.setConclusionExamResult(examinationDto.getConclusionExamResult());
				examination.setFacilityId(this.getCurrentUserId().getFacilityId());
//				examination.setFacility(facility); 
				examination.setHistory(examinationDto.getHistory());
				examination.setPratician(practician);
	      		examination.setStartDate(new Date());
				examination = examinationService.saveExamination(examination);
//				facility.setId( (long) 1); 
				
				if(examinationDto.getPathologies() != null && examinationDto.getPathologies().size() != 0) {
					examinationDto.getPathologies().forEach(pathology -> {
						examinationService.addPathologyToExamination(pathology, examination);
					});
				}
				
				if(examinationDto.getSymptoms() != null && examinationDto.getSymptoms().size() != 0) {
					examinationDto.getSymptoms().forEach(symptom -> {
						examinationService.addSymptomToExamination(symptom, examination);
					});
				}
				return new ResponseEntity<Examination>(examination, HttpStatus.OK);

	} 
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	@ApiOperation(value = "Lister la liste de toutes les consultations dans le syst??me")
	@GetMapping("/p_list/by_patient")
	public ResponseEntity<Map<String, Object>> patientPaginatedConsultations (
			@RequestParam(required = false, defaultValue = "") Long patient,
			@RequestParam(defaultValue = "id,desc") String[] sort,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size ) {
		
		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
        
		Page<Examination> pExaminations = null;
	
		pExaminations = examinationService.findPatientExaminations(patient, pageable);
		

		List<Examination> lExaminations = pExaminations.getContent();


		List<Map<String, Object>> examinations = this.getMapFromExaminationList(lExaminations);
		response.put("items", examinations);
		response.put("totalItems", pExaminations.getTotalElements());
		response.put("totalPages", pExaminations.getTotalPages());
		response.put("size", pExaminations.getSize());
		response.put("currentPage", pExaminations.getNumber());
		response.put("first", pExaminations.isFirst());
		response.put("last", pExaminations.isLast());
		response.put("empty", pExaminations.isEmpty());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	protected List<Map<String, Object>> getMapFromExaminationList(List<Examination> exams) {
		List<Map<String, Object>> examsList = new ArrayList<>();
		exams.stream().forEach(examsDto -> {
			Map<String, Object> examsMap = new HashMap<>();
			
			examsMap.put("id", examsDto.getId());
			examsMap.put("admission", examsDto.getAdmission().getId());
			examsMap.put("date", examsDto.getStartDate());
			examsMap.put("conclusion", examsDto.getConclusion());
			examsMap.put("facility", examsDto.getFacility().getName());
			examsMap.put("practicianFirstName", examsDto.getPratician().getUser().getFirstName());
			examsMap.put("practicianLastName", examsDto.getPratician().getUser().getLastName());
			
			examsList.add(examsMap);
		});
		return examsList;
	}
	
	@GetMapping("/getPatientExaminationNumber/{patientId}")
	@ApiOperation("nombre de consultation d'un patient ")
	public  Long getDetail(@PathVariable Long patientId){
	
	return examinationService.findPatientExaminationsNumber(patientId);
	}
}
