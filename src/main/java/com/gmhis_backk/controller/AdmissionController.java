package com.gmhis_backk.controller;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.gmhis_backk.dto.AdmissionDTO;
import com.gmhis_backk.dto.admissionTakeCareDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.AdmissionService;
import com.gmhis_backk.service.WaitingRoomService;

import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
@RequestMapping("/admission")
public class AdmissionController {
	
	@Autowired
	AdmissionService admissionService;
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	WaitingRoomService waitingRoomService;
	
	@PostMapping("")
	@ApiOperation("/Ajouter une admission")
	public ResponseEntity<Admission> addAdmission(@RequestBody AdmissionDTO admissionDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException{
		Admission admission = admissionService.saveAdmission(admissionDto);
		return new ResponseEntity<Admission>(admission, HttpStatus.OK);
	}
	
	@PatchMapping("{admissionID}")
	@ApiOperation("/Update a admission given by his id")
	public ResponseEntity<Admission> updateAdmission(@PathVariable("admissionID") Long id,  @RequestBody AdmissionDTO admissionDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException{
		Admission admission = admissionService.updateAdmission(id, admissionDto);
		return new ResponseEntity<Admission>(admission, HttpStatus.OK);
	}
	
	@PutMapping("/update-takeCare/{admissionID}")
	@ApiOperation("Update takeCare")
	public ResponseEntity<Admission> updatetakeCareStatus(@PathVariable Long admissionID,@RequestBody admissionTakeCareDTO takeCareDTO) throws NotFoundException{
		System.out.println("admissionID " + admissionID);
		System.out.println("takeCare " + takeCareDTO.getTakeCare());

		Admission admission = admissionService.updatetakeCare(admissionID,takeCareDTO.getTakeCare());
		return ResponseEntity.accepted().body(admission);
	}
	
	@ApiOperation(value = "Lister la liste paginee de toutes les admission dans le système par status (R: register, B: billed")
	@GetMapping("/p_list")
	public ResponseEntity<Map<String, Object>> paginatedList(
			@RequestParam(required = false, defaultValue = "") String admissionNumber,
			@RequestParam(required = false, defaultValue = "") String firstName,
			@RequestParam(required = false, defaultValue = "") String lastName,
			@RequestParam(required = false, defaultValue = "") String patientExternalId,
			@RequestParam(required = false, defaultValue = "") String cellPhone,
			@RequestParam(required = false, defaultValue = "") String cnamNumber,
			@RequestParam(required = false, defaultValue = "") String idCardNumber,
			@RequestParam(required = false) Long practician,
			@RequestParam(required = false) Long service,
			@RequestParam(required = false) Long act,
			@RequestParam(required = false) String facilityId,
		    @RequestParam(required = false, defaultValue = "") String date,
		    @RequestParam(required = true, defaultValue = "R") String admissionStatus,
			@RequestParam(defaultValue = "id,desc") String[] sort, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "50") int size) throws ParseException {
		   
		
		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));

		Page<Admission> pAdmissions = null;
			
		pAdmissions = admissionService.findAdmissionsByFacility(this.getCurrentUserId().getFacilityId(),admissionStatus, paging); 	
		if( ObjectUtils.isNotEmpty(firstName) ||  ObjectUtils.isNotEmpty(lastName) ) {
			pAdmissions = admissionService.findAdmissionsByPatientName(firstName, lastName, admissionStatus,this.getCurrentUserId().getFacilityId(), paging);
		}
		
		if( ObjectUtils.isNotEmpty(admissionNumber) ) {
			pAdmissions = admissionService.findAdmissionsByAdmissionNumber(admissionNumber, admissionStatus,this.getCurrentUserId().getFacilityId(), paging);
		}
		
		if( ObjectUtils.isNotEmpty(patientExternalId)  ) {
			pAdmissions = admissionService.findAdmissionsByPatientExternalId(patientExternalId, admissionStatus,this.getCurrentUserId().getFacilityId(), paging);
		} 
	
		if( ObjectUtils.isNotEmpty(cellPhone)  ) {
			pAdmissions = admissionService.findAdmissionsByCellPhone(cellPhone, admissionStatus,this.getCurrentUserId().getFacilityId(), paging);
		} 
		
		if( ObjectUtils.isNotEmpty(cnamNumber)  ) {
			pAdmissions = admissionService.findAdmissionsByCnamNumber(cnamNumber, admissionStatus,this.getCurrentUserId().getFacilityId(), paging);
		} 
		
		if( ObjectUtils.isNotEmpty(idCardNumber)  ) {
			pAdmissions = admissionService.findAdmissionsByIdCardNumber(idCardNumber, admissionStatus,this.getCurrentUserId().getFacilityId(), paging);
		} 
		
		if( ObjectUtils.isNotEmpty(act) ) {
			pAdmissions = admissionService.findAdmissionsByAct(act, admissionStatus,this.getCurrentUserId().getFacilityId(), paging);
		} 
//		
		if( ObjectUtils.isNotEmpty(service) ) {
			pAdmissions = admissionService.findAdmissionsByService(service, admissionStatus,this.getCurrentUserId().getFacilityId(), paging);
		} 
		
		if( ObjectUtils.isNotEmpty(date)) {
			pAdmissions = admissionService.findAdmissiondByDate(date,this.getCurrentUserId().getFacilityId(), paging);
		}
	
		List<Admission> lAdmissions = pAdmissions.getContent();

		
		List<Map<String, Object>> admission = this.getMapFromAdmissionList(lAdmissions);
		response.put("items", admission);
		response.put("currentPage", pAdmissions.getNumber());
		response.put("totalItems", pAdmissions.getTotalElements());
		response.put("totalPages", pAdmissions.getTotalPages());
		response.put("size", pAdmissions.getSize());
		response.put("first", pAdmissions.isFirst());
		response.put("last", pAdmissions.isLast());
		response.put("empty", pAdmissions.isEmpty());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	protected List<Map<String, Object>> getMapFromAdmissionList(List<Admission> admissions) {
		List<Map<String, Object>> admissionList = new ArrayList<>();
		admissions.stream().forEach(admissionDto -> {
			Map<String, Object> admissionsMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(admissionDto.getCreatedBy()) ? new User() : userRepository.findById(admissionDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(admissionDto.getUpdatedBy()) ? new User() : userRepository.findById(admissionDto.getUpdatedBy()).orElse(null);
			admissionsMap.put("id", admissionDto.getId());
			admissionsMap.put("admissionNumber", admissionDto.getAdmissionNumber());
			admissionsMap.put("facilityName", admissionDto.getFacility().getName());
			admissionsMap.put("facilityType", admissionDto.getFacility().getFacilityType().getName());
			admissionsMap.put("admissionStatus", admissionDto.getAdmissionStatus());
			admissionsMap.put("patientId", admissionDto.getPatient().getId());
			admissionsMap.put("patientExternalId", admissionDto.getPatient().getPatientExternalId());
			admissionsMap.put("patientFirstName", admissionDto.getPatient().getFirstName());
			admissionsMap.put("patientLastName", admissionDto.getPatient().getLastName());

			admissionsMap.put("patientType", admissionDto.getPatient().getIsAssured());
			admissionsMap.put("caution",admissionDto.getCaution());
			admissionsMap.put("admissionDate", admissionDto.getCreatedAt());
			admissionsMap.put("act", admissionDto.getAct().getName());
			admissionsMap.put("actId", admissionDto.getAct().getId());
			admissionsMap.put("actCost", (admissionDto.getAct().getCoefficient() * admissionDto.getAct().getActCode().getValue()));
			admissionsMap.put("service", admissionDto.getSpeciality().getName());
			if (admissionDto.getPractician() != null) {
				admissionsMap.put("practician", admissionDto.getPractician().getUser().getLastName() + " " + admissionDto.getPractician().getUser().getFirstName());
				admissionsMap.put("practicianId", admissionDto.getPractician().getId());
			}
			admissionsMap.put("createdAt", admissionDto.getCreatedAt());
			admissionsMap.put("updatedAt", admissionDto.getUpdatedAt());
			admissionsMap.put("createdByLogin", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLogin());
			admissionsMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			admissionsMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			admissionsMap.put("UpdatedByLogin", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLogin());
			admissionsMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			admissionsMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			admissionList.add(admissionsMap);
		});
		return admissionList;
	}
	
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'une admission ")
	public  ResponseEntity<Map<String, Object>> getDetail(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();

		Admission admission= admissionService.findAdmissionById(id).orElse(null);
		response.put("id", admission.getId());
		response.put("takeCare", admission.getTakeCare());
		response.put("caution", admission.getCaution());
		response.put("patientId", admission.getPatient().getId());
		response.put("patientExternalId", admission.getPatient().getPatientExternalId());
		response.put("admissionDate", admission.getAdmissionStartDate());
		response.put("service", admission.getSpeciality().getName());
		response.put("admissionNumber", admission.getAdmissionNumber());
		response.put("facilityName", admission.getFacility().getName());
		response.put("facilityType", admission.getFacility().getFacilityType().getName());
		response.put("admissionStatus", admission.getAdmissionStatus());
		response.put("patientExternalId", admission.getPatient().getPatientExternalId());
		response.put("patientFirstName", admission.getPatient().getFirstName());
		response.put("patientLastName", admission.getPatient().getLastName());
		response.put("patientType", admission.getPatient().getIsAssured());
		response.put("act", admission.getAct().getName());
		response.put("actId", admission.getAct().getId());
		response.put("actCost", (admission.getAct().getCoefficient() * admission.getAct().getActCode().getValue()));
		if (admission.getPractician() != null) {
			response.put("practician", admission.getPractician().getUser().getFirstName() + " " + admission.getPractician().getUser().getLastName());
			response.put("practicianId", admission.getPractician().getId());
		}
		response.put("createdAt", admission.getCreatedAt());
		response.put("updatedAt", admission.getUpdatedAt());

		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	@ApiOperation(value = "Lister la liste paginee de toutes les admissions en salle d'attente")
	@GetMapping("/queue/p_list")
	public ResponseEntity<Map<String, Object>> queuepaginatedList(
			@RequestParam(required = true) Boolean takeCare,
			@RequestParam(required = false, defaultValue = "") String firstName,
			@RequestParam(required = false, defaultValue = "") String patientExternalId,
			@RequestParam(required = false) Long practician,
			@RequestParam(required = false) Long specilaity,
			@RequestParam(required = false) Long act,
		    @RequestParam(required = false, defaultValue = "") String fromDate,
		    @RequestParam(required = false, defaultValue = "") String toDate,
			@RequestParam(defaultValue = "id,desc") String[] sort, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		   
		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<Admission> queue = null;
		
		Calendar cDate1 = Calendar.getInstance();
		Calendar cDate2 = Calendar.getInstance();
		
		if(ObjectUtils.isEmpty(fromDate)) { 
			cDate1.set(1970, 0, 1);
		}else {
			String[] fd= fromDate.split("/");
			cDate1.set(Integer.parseInt(fd[2]), Integer.parseInt(fd[1]) - 1 , Integer.parseInt(fd[0]), 0, 0);
		}
		
		if(ObjectUtils.isNotEmpty( toDate)) {
			String[] td= toDate.split("/");
			cDate2.set(Integer.parseInt(td[2]), Integer.parseInt(td[1]) - 1 , Integer.parseInt(td[0]), 23, 59);
		}
		
		Date date1 = cDate1.getTime();
		Date date2 = cDate2.getTime();
	
		queue = admissionService.findAdmissionsInQueue(takeCare,this.getCurrentUserId().getFacilityId(), pageable); 

		if( ObjectUtils.isNotEmpty(fromDate) ||  ObjectUtils.isNotEmpty(toDate) ) {
			queue = admissionService.findAdmissionsInQueueByDate(takeCare,date1, date2, pageable);
		}
	
		List<Admission> lAdmissions = queue.getContent();

		List<Map<String, Object>> admission = this.getMapFromAdmissionList(lAdmissions);
		response.put("items", admission);
		response.put("currentPage", queue.getNumber());
		response.put("totalItems", queue.getTotalElements());
		response.put("totalPages", queue.getTotalPages());
		response.put("size", queue.getSize());
		response.put("first", queue.isFirst());
		response.put("last", queue.isLast());
		response.put("empty", queue.isEmpty());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "revocation d'une admission")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> revokeAdmission(@PathVariable() Long id){
		admissionService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	


}
