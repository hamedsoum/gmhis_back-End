package com.gmhis_backk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.gmhis_backk.domain.Prescription;
import com.gmhis_backk.domain.PrescriptionItem;
import com.gmhis_backk.dto.PrescriptionDto;
import com.gmhis_backk.exception.domain.EmailExistException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.PrescriptionItemService;
import com.gmhis_backk.service.PrescriptionService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@RestController
@RequestMapping("/prescription")
public class PrescriptionController {
	
	@Autowired
	private PrescriptionService prescriptionService;
	
	@Autowired
	private PrescriptionItemService prescriptionItemService;
	
	@PostMapping("/add")
	@ApiOperation("Ajouter une ordonnance")
	public  ResponseEntity<Prescription>addPrescription(@RequestBody PrescriptionDto pDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException,EmailExistException {
		Prescription prescription = prescriptionService.save(pDto);
		return new ResponseEntity<Prescription>(prescription,HttpStatus.OK);
	}
	

	@ApiOperation(value = "Lister la liste de toutes les ordonnances dans le système")
	@GetMapping("/p_list/by_patient")
	public ResponseEntity<Map<String, Object>> patientPaginatedPrescription (
			@RequestParam(required = false, defaultValue = "") Long patient,
			@RequestParam(defaultValue = "id,desc") String[] sort,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size ) {
		
		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
        
		Page<Prescription> pPrescriptions = null;

		pPrescriptions = prescriptionService.findAll(pageable);
		
		if( ObjectUtils.isNotEmpty(patient) ) {
			pPrescriptions = prescriptionService.findAllPatientPrescriptions(patient, pageable);
		} 
//		
//
		List<Prescription> lPrescriptions = pPrescriptions.getContent();


		List<Map<String, Object>> prescription = this.getMapFromPrescriptionList(lPrescriptions);
		response.put("items", prescription);
		response.put("totalItems", pPrescriptions.getTotalElements());
		response.put("totalPages", pPrescriptions.getTotalPages());
		response.put("size", pPrescriptions.getSize());
		response.put("currentPage", pPrescriptions.getNumber());
		response.put("first", pPrescriptions.isFirst());
		response.put("last", pPrescriptions.isLast());
		response.put("empty", pPrescriptions.isEmpty());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	protected List<Map<String, Object>> getMapFromPrescriptionList(List<Prescription> exams) {
		List<Map<String, Object>> prescriptionList = new ArrayList<>();
		exams.stream().forEach(prescriptionDto -> {
			Map<String, Object> examsMap = new HashMap<>();
			
			examsMap.put("id", prescriptionDto.getId());
			examsMap.put("prescritionDate", prescriptionDto.getPrescriptionDate());
			examsMap.put("prescriptionNumber", prescriptionDto.getPrescriptionNumber());
			examsMap.put("faciityName", prescriptionDto.getExamination().getFacility().getName());
			examsMap.put("prescriptionStatus", prescriptionDto.getPrescriptionStatus());
			examsMap.put("practicienFirstName", prescriptionDto.getExamination().getPratician().getUser().getFirstName());
			examsMap.put("practicienLastName", prescriptionDto.getExamination().getPratician().getUser().getLastName());
			prescriptionList.add(examsMap);
		});
		return prescriptionList;
	}
	

	@GetMapping("/getPrescriptionItems/{prescriptionId}")
	@ApiOperation("listes des prescription par l'id de l'ordonnance ")
	public  ResponseEntity<List<Map<String, Object>>> getPrescriptionItemsByPrescriptionId(@PathVariable UUID prescriptionId){
		Map<String, Object> response = new HashMap<>();

		List<PrescriptionItem> prescriptions = prescriptionItemService.findPrescriptionItemsByPrescription(prescriptionId);
		List<Map<String, Object>> prescriptionItemList = this.getMapFromPrescriptionItemList(prescriptions);

		return new ResponseEntity<>(prescriptionItemList, HttpStatus.OK);

	}
	
	protected List<Map<String, Object>> getMapFromPrescriptionItemList(List<PrescriptionItem> prescriptionItems) {
		List<Map<String, Object>> prescriptionItemList = new ArrayList<>();
		prescriptionItems.stream().forEach(prescriptionItemDto -> {
			Map<String, Object> examsMap = new HashMap<>();
			
			examsMap.put("id", prescriptionItemDto.getId());
			examsMap.put("dosage", prescriptionItemDto.getDosage());
			examsMap.put("quantity", prescriptionItemDto.getQuantity());
			examsMap.put("drug", prescriptionItemDto.getDrug().getName());
			examsMap.put("collected", prescriptionItemDto.getCollected());
			examsMap.put("duration", prescriptionItemDto.getDuration());
			prescriptionItemList.add(examsMap);
		});
		return prescriptionItemList;
	}
	
	@GetMapping("/getPrescriptionNumber/{patientId}")
	@ApiOperation("nombre de consultation d'un patient ")
	public  Long getDetail(@PathVariable Long patientId){
	
	return prescriptionService.findPrescriptionsNumber(patientId);
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'une ordonnance ")
	public  ResponseEntity<Map<String, Object>> getDetail(@PathVariable UUID id){
		Map<String, Object> response = new HashMap<>();

		Prescription prescription = prescriptionService.findPrescriptionById(id).orElse(null);
//		response.put("prescription", prescription);
		response.put("id", prescription.getId());
		response.put("practicienFirstName", prescription.getExamination().getPratician().getUser().getFirstName());
		response.put("practicienLastName", prescription.getExamination().getPratician().getUser().getLastName());
		response.put("facilityName", prescription.getExamination().getFacility().getName());
		response.put("serviceName", prescription.getExamination().getAdmission().getService().getName());
		response.put("facilityLocality", prescription.getExamination().getFacility().getLocality().getName());
		response.put("facilityCountry", prescription.getExamination().getFacility().getLocality().getCity().getCountry().getName());
		response.put("facilityCity", prescription.getExamination().getFacility().getLocality().getCity().getName());
		response.put("prescriptionDate", prescription.getPrescriptionDate());
		response.put("prescriptionNumber", prescription.getPrescriptionNumber());
		response.put("prescriptionObservation", prescription.getObservation());
		response.put("patientFirstName", prescription.getExamination().getAdmission().getPatient().getFirstName());
		response.put("patientLastName", prescription.getExamination().getAdmission().getPatient().getLastName());
		response.put("patientCivility", prescription.getExamination().getAdmission().getPatient().getCivility());

//		response.put("prescriptionItem", prescription.getPrescriptionItems());


//		response.put("facilityName", prescription.getName());
//		response.put("facilityShortNameName", falicity.getShortName());
//		response.put("faciityType", falicity.getFacilityType().getName());
//		response.put("faciityCategory", falicity.getFacilityCategory().getName());
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
