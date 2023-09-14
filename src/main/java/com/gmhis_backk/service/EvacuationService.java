package com.gmhis_backk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.gmhis_backk.domain.ActCategory;
import com.gmhis_backk.domain.Cashier;
import com.gmhis_backk.domain.CashierCreate;
import com.gmhis_backk.domain.Evacuation;
import com.gmhis_backk.domain.EvacuationCreate;
import com.gmhis_backk.domain.Facility;
import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.EvacuationRepository;
import com.gmhis_backk.repository.UserRepository;

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
			evacuationMap.put("evacuationFacility", evacuation.getEvacuationFacility());
			evacuationMap.put("startDate", evacuation.getStartDate());
			evacuationMap.put("service", evacuation.getService());
			evacuationMap.put("practician", evacuation.getPractician());
			evacuationMap.put("patient", evacuation.getPatient());
			evacuationMap.put("evacuationReason", evacuation.getEvacuationReason());
			evacuationMap.put("clinicalInformation", evacuation.getClinicalInformation());
			evacuationMap.put("treatmentReceived", evacuation.getTreatmentReceived());
			evacuationMap.put("receptionFacility", evacuation.getReceptionFacility());

			evacuationList.add(evacuationMap);
		});
		
		return evacuationList;
		}
	
	
	public Evacuation Create(EvacuationCreate evacuationCreate) throws ResourceNotFoundByIdException {
		
		Facility evacuationFacility = facilityService.findFacilityById(evacuationCreate.getEvacuationFacilityID())
		.orElseThrow(() -> new ResourceNotFoundByIdException("l'etablissement evacuateur est inexistant"));
		
		ActCategory service = categoryService.findById(evacuationCreate.getServiceID())
		.orElseThrow(() -> new ResourceNotFoundByIdException("Le service est inexistant"));
		
		Pratician practician = practicianService.findPracticianById(evacuationCreate.getPracticianID())
		.orElseThrow(() -> new ResourceNotFoundByIdException("Le practien est inexistant"));
		
		Patient patient = patientService.findById(evacuationCreate.getPatientID());
		if (patient == null) throw new ResourceNotFoundByIdException("Patient Inexistant");
		
		Evacuation evacuation = new Evacuation();
		BeanUtils.copyProperties(evacuationCreate,evacuation,"id");
		evacuation.setEvacuationFacility(evacuationFacility);
		evacuation.setService(service);
		evacuation.setPractician(practician);
		evacuation.setPatient(patient);
		evacuation.setCreatedAt(new Date());
		evacuation.setCreatedBy(getCurrentUser().getId());
		
		return evacuationRepository.save(evacuation);
	}
	
	public Evacuation retrieve(UUID EvacuationID) throws ResourceNotFoundByIdException {
		
		Evacuation evacuation = evacuationRepository.findById(EvacuationID).orElse(null);
		if (evacuation == null) throw new ResourceNotFoundByIdException("Evacuation Inexistant");
		 return evacuation;
	}
	
	public Evacuation update(UUID EvacuationID, EvacuationCreate evacuationCreate) throws ResourceNotFoundByIdException {
		
				Facility evacuationFacility = facilityService.findFacilityById(evacuationCreate.getEvacuationFacilityID())
				.orElseThrow(() -> new ResourceNotFoundByIdException("l'etablissement evacuateur est inexistant"));
				
				ActCategory service = categoryService.findById(evacuationCreate.getServiceID())
				.orElseThrow(() -> new ResourceNotFoundByIdException("Le service est inexistant"));
				
				Pratician practician = practicianService.findPracticianById(evacuationCreate.getPracticianID())
				.orElseThrow(() -> new ResourceNotFoundByIdException("Le practien est inexistant"));
				
				Patient patient = patientService.findById(evacuationCreate.getPatientID());
				if (patient == null) throw new ResourceNotFoundByIdException("Patient Inexistant");
				
				
				Evacuation evacuation = new Evacuation();
				BeanUtils.copyProperties(evacuationCreate,evacuation,"id");
				evacuation.setEvacuationFacility(evacuationFacility);
				evacuation.setService(service);
				evacuation.setPractician(practician);
				evacuation.setPatient(patient);
				evacuation.setCreatedAt(new Date());
				evacuation.setCreatedBy(getCurrentUser().getId());
				
				return evacuationRepository.save(evacuation);
	}
	
	
public ResponseEntity<Map<String, Object>>  search(Map<String, ?> cashierSearch) {
		
		Map<String, Object> response = new HashMap<>();
		
		int page = (int) cashierSearch.get("page");
		String[] sort = (String[]) cashierSearch.get("sort");
		int size = (int) cashierSearch.get("size");
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
	    Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		Page<Evacuation> pCashiers = null;

		pCashiers = evacuationRepository.findAll(pageable);
		
		List<Evacuation> cashiers = pCashiers.getContent();
		
		List<Map<String, Object>> cashier = this.map(cashiers);
		response.put("items", cashier);
		response.put("totalElements", pCashiers.getTotalElements());
		response.put("totalPages", pCashiers.getTotalPages());
		response.put("size", pCashiers.getSize());
		response.put("pageNumber", pCashiers.getNumber());
		response.put("numberOfElements", pCashiers.getNumberOfElements());
		response.put("first", pCashiers.isFirst());
		response.put("last", pCashiers.isLast());
		response.put("empty", pCashiers.isEmpty());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	

}
