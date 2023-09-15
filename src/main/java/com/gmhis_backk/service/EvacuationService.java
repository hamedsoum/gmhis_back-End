package com.gmhis_backk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.controller.EvacuationController;
import com.gmhis_backk.domain.ActCategory;
import com.gmhis_backk.domain.Evacuation;
import com.gmhis_backk.domain.EvacuationCreate;
import com.gmhis_backk.domain.EvacuationPartial;
import com.gmhis_backk.domain.Facility;
import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.EvacuationRepository;
import com.gmhis_backk.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class EvacuationService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FacilityService facilityService;
	
	@Autowired
	private ActCategoryService categoryService;
	
	@Autowired 
	private PracticianService practicianService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private EvacuationRepository evacuationRepository;
	
	protected  User getCurrentUser() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	
	protected List<Map<String, Object>> map(List<Evacuation> evacuations) {
		List<Map<String, Object>> evacuationList = new ArrayList<>();
		evacuations.stream().forEach(evacuation -> {
			Map<String, Object> evacuationMap = new HashMap<>();
			evacuationMap.put("id", evacuation.getId());
			evacuationMap.put("evacuationFacilityName", evacuation.getEvacuationFacility().getName());
			evacuationMap.put("startDate", evacuation.getStartDate());
			evacuationMap.put("service", evacuation.getService().getName());
			evacuationMap.put("practicianName", evacuation.getPractician().getNom() + " " + evacuation.getPractician().getPrenoms());
			evacuationMap.put("patientName", evacuation.getPatient().getLastName() + " "+ evacuation.getPatient().getFirstName());
			evacuationMap.put("evacuationReason", evacuation.getEvacuationReason());
			evacuationMap.put("clinicalInformation", evacuation.getClinicalInformation());
			evacuationMap.put("treatmentReceived", evacuation.getTreatmentReceived());
			evacuationMap.put("receptionFacilityName", evacuation.getReceptionFacility().getName());

			evacuationList.add(evacuationMap);
		});
		
		return evacuationList;
		}
	
	public EvacuationPartial toPartial(Evacuation evacuation) {
		
		EvacuationPartial evacuationPartial = new EvacuationPartial();
		
		evacuationPartial.setId(evacuation.getId());
		evacuationPartial.setEvacuationFacilityName(evacuation.getEvacuationFacility().getName());
		evacuationPartial.setStartDate(evacuation.getStartDate());
		evacuationPartial.setService(evacuation.getService().getName());
		evacuationPartial.setPracticianName(evacuation.getPractician().getNom() + " " + evacuation.getPractician().getPrenoms());
		evacuationPartial.setPatientName(evacuation.getPatient().getLastName() + " " + evacuation.getPatient().getFirstName());
		evacuationPartial.setEvacuationReason(evacuation.getEvacuationReason());
		evacuationPartial.setClinicalInformation(evacuation.getClinicalInformation());
		evacuationPartial.setTreatmentReceived(evacuation.getTreatmentReceived());
		evacuationPartial.setReceptionFacilityName(evacuation.getReceptionFacility().getName());

		return evacuationPartial;
	}  
	
	public Evacuation Create(EvacuationCreate evacuationCreate) throws ResourceNotFoundByIdException {
		
		Facility evacuationFacility = facilityService.findFacilityById(evacuationCreate.getEvacuationFacilityID())
		.orElseThrow(() -> new ResourceNotFoundByIdException("l'etablissement evacuateur est inexistant"));
		
		Facility receptionFacility = facilityService.findFacilityById(evacuationCreate.getReceptionFacilityID())
		.orElseThrow(() -> new ResourceNotFoundByIdException("l'etablissement receveur est inexistant"));
		
		ActCategory service = categoryService.findById(evacuationCreate.getServiceID())
		.orElseThrow(() -> new ResourceNotFoundByIdException("Le service est inexistant"));
		
		Pratician practician = practicianService.findPracticianById(evacuationCreate.getPracticianID())
		.orElseThrow(() -> new ResourceNotFoundByIdException("Le practien est inexistant"));
		
		Patient patient = patientService.findById(evacuationCreate.getPatientID());
		if (patient == null) throw new ResourceNotFoundByIdException("Patient Inexistant");
		
		Evacuation evacuation = new Evacuation();
		BeanUtils.copyProperties(evacuationCreate,evacuation,"id");
		evacuation.setEvacuationFacility(evacuationFacility);
		evacuation.setReceptionFacility(receptionFacility);
		evacuation.setService(service);
		evacuation.setPractician(practician);
		evacuation.setPatient(patient);
		evacuation.setCreatedAt(new Date());
		evacuation.setCreatedBy(getCurrentUser().getId());
		
		return evacuationRepository.save(evacuation);
	}
	
	public EvacuationPartial retrieve(UUID EvacuationID) throws ResourceNotFoundByIdException {
		
		Evacuation evacuation = evacuationRepository.findById(EvacuationID).orElse(null);
		if (evacuation == null) throw new ResourceNotFoundByIdException("Evacuation Inexistant");
		
		 return this.toPartial(evacuation);
	}
	
	public Evacuation update(UUID EvacuationID, EvacuationCreate evacuationCreate) throws ResourceNotFoundByIdException {
		
		Evacuation evacuation = evacuationRepository.findById(EvacuationID)
				.orElseThrow(() -> new ResourceNotFoundByIdException("Evacuation inexistant"));

				Facility evacuationFacility = facilityService.findFacilityById(evacuationCreate.getEvacuationFacilityID())
				.orElseThrow(() -> new ResourceNotFoundByIdException("l'etablissement evacuateur est inexistant"));
				
				Facility receptionFacility = facilityService.findFacilityById(evacuationCreate.getReceptionFacilityID())
						.orElseThrow(() -> new ResourceNotFoundByIdException("l'etablissement receveur est inexistant"));
				
				ActCategory service = categoryService.findById(evacuationCreate.getServiceID())
				.orElseThrow(() -> new ResourceNotFoundByIdException("Le service est inexistant"));
				
				Pratician practician = practicianService.findPracticianById(evacuationCreate.getPracticianID())
				.orElseThrow(() -> new ResourceNotFoundByIdException("Le practien est inexistant"));
				
				Patient patient = patientService.findById(evacuationCreate.getPatientID());
				if (patient == null) throw new ResourceNotFoundByIdException("Patient Inexistant");
				
				
				evacuation.setEvacuationFacility(evacuationFacility);
				evacuation.setReceptionFacility(receptionFacility);
				evacuation.setService(service);
				evacuation.setPractician(practician);
				evacuation.setPatient(patient);
				evacuation.setUpdatededAt(new Date());
				evacuation.setUpdatedBy(getCurrentUser().getId());
				BeanUtils.copyProperties(evacuationCreate,evacuation,"id");

				return evacuationRepository.save(evacuation);
	}
	
	
public ResponseEntity<Map<String, Object>>  search(Map<String, ?> evacuationSearch) {
		
		Map<String, Object> response = new HashMap<>();
		
		Long service = (Long) evacuationSearch.get("service");
		log.info("service {}", service);

		int page = (int) evacuationSearch.get("page");
		String[] sort = (String[]) evacuationSearch.get("sort");
		int size = (int) evacuationSearch.get("size");
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
	    Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		Page<Evacuation> pEvacuations = null;


		if(Objects.nonNull(service)) pEvacuations = evacuationRepository.findAllBYService(service,pageable);
		else pEvacuations = evacuationRepository.findAll(pageable);

		List<Evacuation> evacuations = pEvacuations.getContent();
		
		
		List<Map<String, Object>> evacuation = this.map(evacuations);
		response.put("items", evacuation);
		response.put("totalElements", pEvacuations.getTotalElements());
		response.put("totalPages", pEvacuations.getTotalPages());
		response.put("size", pEvacuations.getSize());
		response.put("pageNumber", pEvacuations.getNumber());
		response.put("numberOfElements", pEvacuations.getNumberOfElements());
		response.put("first", pEvacuations.isFirst());
		response.put("last", pEvacuations.isLast());
		response.put("empty", pEvacuations.isEmpty());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	

}
