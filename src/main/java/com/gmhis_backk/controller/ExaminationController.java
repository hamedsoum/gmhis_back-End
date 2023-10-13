package com.gmhis_backk.controller;

import java.text.ParseException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Admission;
import com.gmhis_backk.domain.Examination;
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
import javassist.NotFoundException;

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


	@GetMapping("/{examinationID}")
	@ApiOperation("retrieve examination in the systeme")
	public Optional<Examination> retrieve(@PathVariable Long examinationID) throws ParseException{
        return examinationService.findExaminationById(examinationID);
	}
	
	@ApiOperation(value = "Ajouter une consultation d'un patient")
	@PostMapping("/add")
	
	public ResponseEntity<Examination> addExaminations(@Validated @RequestBody ExaminationDTO examinationDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException{
	    
		 Admission admission = admissionService.findAdmissionById(examinationDto.getAdmission())
				 .orElseThrow(() -> new ResourceNotFoundByIdException("aucune admission d'acte trouvé pour l'identifiant " ));
			
			 Pratician practician = practicianService.findPracticianByUser(this.getCurrentUserId().getId()).orElse(null);
				if (practician == null) {
					throw new ResourceNotFoundByIdException("aucun practicien trouvé pour l'utilisateur connecte " );
				}
				examination = new Examination();
				examination.setAnamnesisSocioProfessional(examinationDto.getAnamnesisSocioProfessional());
				examination.setAntecedentsFamily(examinationDto.getAntecedentsFamily());
				examination.setHabits(examinationDto.getHabits());
				examination.setDiagnosisPresumptive(examinationDto.getDiagnosisPresumptive());
				examination.setAdmission(admission);
				examination.setConclusion(examinationDto.getConclusion());
				examination.setHistory(examinationDto.getHistory());
				examination.setOldTreatment(examinationDto.getOldTreatment());
				examination.setEndDate(examinationDto.getEndDate());
				examination.setExaminationReasons(examinationDto.getExaminationReasons());
				examination.setClinicalExamination(examinationDto.getClinicalExamination());
				examination.setExaminationType(examinationDto.getExaminationType());
				examination.setFacilityId(UUID.fromString(this.getCurrentUserId().getFacilityId()));
				examination.setHistory(examinationDto.getHistory());
				examination.setPratician(practician);
	      		examination.setStartDate(new Date());
				examination = examinationService.saveExamination(examination);
				
				if(examinationDto.getPathologies() != null && examinationDto.getPathologies().size() != 0) {
					examinationDto.getPathologies().forEach(pathology -> {
						examinationService.addPathologyToExamination(pathology, examination);
					});
				}
				return new ResponseEntity<Examination>(examination, HttpStatus.OK);

	} 
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	@ApiOperation(value = "Lister la liste de toutes les consultations dans le système")
	@GetMapping("/p_list/by_patient")
	public ResponseEntity<Map<String, Object>> patientPaginatedConsultations (
			@RequestParam(required = false, defaultValue = "") Long patient,
			@RequestParam(required = false, defaultValue = "") Long admissionID,
			@RequestParam(defaultValue = "id,desc") String[] sort,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size ) {
		
		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
        
		Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
        
		Page<Examination> pExaminations = null;
	    
		pExaminations = examinationService.findPatientExaminations(patient,admissionID, pageable);
		

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
			examsMap.put("patientId", examsDto.getAdmission().getPatient().getId());
			examsMap.put("date", examsDto.getStartDate());
			examsMap.put("examinationReasons", examsDto.getExaminationReasons());
			examsMap.put("conclusion", examsDto.getConclusion());
			examsMap.put("history", examsDto.getHistory());
			examsMap.put("oldTreatment", examsDto.getOldTreatment());
			examsMap.put("anamnesisSocioProfessional", examsDto.getAnamnesisSocioProfessional());
			examsMap.put("antecedentsFamily", examsDto.getAntecedentsFamily());
			examsMap.put("habits", examsDto.getHabits());
			examsMap.put("diagnosisPresumptive", examsDto.getDiagnosisPresumptive());
			examsMap.put("clinicalExamination", examsDto.getClinicalExamination());
			examsMap.put("facility", examsDto.getFacility().getName());
			examsMap.put("practicianFirstName", examsDto.getPratician().getUser().getFirstName());
			examsMap.put("practicianLastName", examsDto.getPratician().getUser().getLastName());
			examsMap.put("practicianTel", examsDto.getPratician().getUser().getTel());			
			examsList.add(examsMap);
		});
		return examsList;
	}
	
	@GetMapping("/getPatientExaminationNumber/{admissionID}")
	@ApiOperation("nombre de consultation d'un patient ")
	public  Long getDetail(@PathVariable Long admissionID){
	
	return examinationService.findPatientExaminationsNumberByAdmission(admissionID);
	}
	
	@ApiOperation(value = "Recupérer la derniere consultation du patient pour une admission")
	@GetMapping("/lastExaminatonOfAdmission")
	public ResponseEntity<Map<String, Object>> findPatientFirstExaminationsOfAdmisions (
			@RequestParam(required = false, defaultValue = "") Long patient,
			@RequestParam(defaultValue = "id,desc") String[] sort,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size ) {
		
		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
        
		Page<Examination> pExaminations = null;
	
		pExaminations = examinationService.findPatientFirstExaminationsOfAdmisions(patient, pageable);
		

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
	
	
	@GetMapping("/findPatientExaminationsOfLastAdmission/{patientID}")
	@ApiOperation("consultations de la derniere admission d'un patient")
	public Boolean AdmissionNoHaveExamination(@PathVariable Long patientID) throws ParseException{
	List<Examination> examination = examinationService.findPatientExaminationsOfLastAdmision(patientID);
		if (examination.size() != 0 ) return false;
	return true;
	}
	
	
	@GetMapping("/firstExaminationDayNumber/{admissionID}")
	@ApiOperation("Nombre de jour entre la premiere consultation d'une admission et la date courante")
	protected long RetrieveDayNumberBetweenAdmissionFirstExaminationAndCurrentDate(@PathVariable Long admissionID) throws Exception {
		return examinationService.dayNumberBetweenAdmissionFirstExaminationAndCurrentDate(admissionID);
	}
	
	@PutMapping("/update-diagnostic/{examinationId}")
	@ApiOperation("Inserer le diagnostic d'une consultation")
	public ResponseEntity<Examination> updateExamination(@PathVariable Long examinationId,@RequestBody() String diagnostic) throws NotFoundException{
		Examination examination = examinationService.insertDiagnostic(examinationId,diagnostic);
		return ResponseEntity.accepted().body(examination);
	}
	
	@ApiOperation(value = "find All Practicians Examination in the system")
	@GetMapping("/practicianExamination")
	public ResponseEntity<Map<String, Object>>  search(
			@RequestParam(defaultValue = "id,desc") String[] sort,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "25") int size) throws NotFoundException {
	
		Map<String, Object> search = new HashMap<>();
		
		search.put("sort", sort);
		search.put("page", page);
		search.put("size", size);

		return examinationService.searchPracticianExaminations(search);	
	}
	
}
