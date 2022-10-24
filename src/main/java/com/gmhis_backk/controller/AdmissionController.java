package com.gmhis_backk.controller;


import java.awt.Window;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.Admission;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.AdmissionDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.AdmissionService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/admission")
public class AdmissionController {
	
	@Autowired
	AdmissionService admissionService;
	
	@Autowired 
	UserRepository userRepository;
	
	@PostMapping("/add")
	@ApiOperation("/Ajouter une admission")
	public ResponseEntity<Admission> addAdmission(@RequestBody AdmissionDTO admissionDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException{
		Admission admission = admissionService.saveAdmission(admissionDto);
		return new ResponseEntity<Admission>(admission, HttpStatus.OK);
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
		    @RequestParam(required = false, defaultValue = "") String fromDate,
		    @RequestParam(required = false, defaultValue = "") String toDate,
		    @RequestParam(required = true, defaultValue = "R") String admissionStatus,
			@RequestParam(defaultValue = "id,desc") String[] sort, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		   

		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));

		Page<Admission> pAdmissions = null;
		
		Calendar cDate1 = Calendar.getInstance();
		Calendar cDate2 = Calendar.getInstance();
//		
//				
		if(ObjectUtils.isEmpty(fromDate)) { 
			cDate1.set(1970, 0, 1);
		}else {
			String[] fd= fromDate.split("/");
			cDate1.set(Integer.parseInt(fd[2]), Integer.parseInt(fd[1]) - 1 , Integer.parseInt(fd[0]), 0, 0);
		}
//		
		if(ObjectUtils.isNotEmpty( toDate)) {
			String[] td= toDate.split("/");
			cDate2.set(Integer.parseInt(td[2]), Integer.parseInt(td[1]) - 1 , Integer.parseInt(td[0]), 23, 59);
		}
		
		Date date1 = cDate1.getTime();
		Date date2 = cDate2.getTime();
		
		pAdmissions = admissionService.findAdmissions(admissionStatus, paging); 
		
		if( ObjectUtils.isNotEmpty(firstName) ||  ObjectUtils.isNotEmpty(lastName) ) {
			pAdmissions = admissionService.findAdmissionsByPatientName(firstName, lastName, admissionStatus, paging);
		}
//		
		if( ObjectUtils.isNotEmpty(admissionNumber) ) {
			System.out.print(admissionNumber);
			pAdmissions = admissionService.findAdmissionsByAdmissionNumber(admissionNumber, admissionStatus, paging);
		}
////		
		if( ObjectUtils.isNotEmpty(patientExternalId)  ) {
			System.out.print(patientExternalId);
			pAdmissions = admissionService.findAdmissionsByPatientExternalId(patientExternalId, admissionStatus, paging);
		} 
//		
		if( ObjectUtils.isNotEmpty(cellPhone)  ) {
			pAdmissions = admissionService.findAdmissionsByCellPhone(cellPhone, admissionStatus, paging);
		} 
//		
		if( ObjectUtils.isNotEmpty(cnamNumber)  ) {
			pAdmissions = admissionService.findAdmissionsByCnamNumber(cnamNumber, admissionStatus, paging);
		} 
//		
		if( ObjectUtils.isNotEmpty(idCardNumber)  ) {
			pAdmissions = admissionService.findAdmissionsByIdCardNumber(idCardNumber, admissionStatus, paging);
		} 
//		
//		if( ObjectUtils.isNotEmpty(practician) ) {
//			pAdmissions = admissionService.findAdmissionsByPractician(practician, admissionStatus, paging);
//		} 
		
		if( ObjectUtils.isNotEmpty(act) ) {
			pAdmissions = admissionService.findAdmissionsByAct(act, admissionStatus, paging);
		} 
//		
		if( ObjectUtils.isNotEmpty(service) ) {
			pAdmissions = admissionService.findAdmissionsByService(service, admissionStatus, paging);
		} 
		
//		if( ObjectUtils.isNotEmpty(fromDate) ||  ObjectUtils.isNotEmpty(toDate) ) {
//			pAdmissions = admissionService.findAdmissionsByDate(date1, date2, admissionStatus, paging);
//		}
	
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
//			System.out.print(admissionDto.getAdmissionNumber());
			Map<String, Object> admissionsMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(admissionDto.getCreatedBy()) ? new User()
					: userRepository.findById(admissionDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(admissionDto.getUpdatedBy()) ? new User()
					: userRepository.findById(admissionDto.getUpdatedBy()).orElse(null);
			admissionsMap.put("id", admissionDto.getId());
			admissionsMap.put("admissionNumber", admissionDto.getAdmissionNumber());
			admissionsMap.put("admissionStatus", admissionDto.getAdmissionStatus());
			admissionsMap.put("patientId", admissionDto.getPatient().getId());
			admissionsMap.put("patientExternalId", admissionDto.getPatient().getPatientExternalId());
			admissionsMap.put("patientFirstName", admissionDto.getPatient().getFirstName());
			admissionsMap.put("patientLastName", admissionDto.getPatient().getLastName());
			admissionsMap.put("patientMaidenName", admissionDto.getPatient().getMaidenName());
			admissionsMap.put("admissionDate", admissionDto.getCreatedAt());
			admissionsMap.put("act", admissionDto.getAct().getName());
			admissionsMap.put("service", admissionDto.getService().getName());
//			admissionsMap.put("practicianFirstName", admissionDto.getPractician().getFirstName());
//			admissionsMap.put("practicianLastName", admissionDto.getPractician().getLastName());
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

}
