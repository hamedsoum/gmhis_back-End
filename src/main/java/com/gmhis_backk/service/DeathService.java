package com.gmhis_backk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Death;
import com.gmhis_backk.domain.DeathPartial;
import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.domain.deathCreate;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.DeathRepository;
import com.gmhis_backk.repository.UserRepository;

@Service
@Transactional
public class DeathService {
	
	@Autowired
	private DeathRepository deathRepository;
	

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PatientService patientService;
	
	protected  User getCurrentUser() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	public DeathPartial toPartial(Death death) {
		DeathPartial deathPartial = new DeathPartial();
		deathPartial.setId(death.getId());
		deathPartial.setCode(death.getCode());
		deathPartial.setDeathDate(death.getDeathDate());
		deathPartial.setDeathDeclarationDate(death.getDeathDeclarationDate());
		deathPartial.setDeathDeclaratedByName(death.getDeathDeclarationBy().getFirstName() + " " + death.getDeathDeclarationBy().getLastName());
		deathPartial.setDeathDeclarationByID(death.getDeathDeclarationBy().getId());
		deathPartial.setDeathReason(death.getDeathReason());
		deathPartial.setPatientName(death.getPatient().getFirstName() + " "+ death.getPatient().getLastName());
		deathPartial.setPatientID(death.getPatient().getId());
		
		return deathPartial;
	}
	
	protected List<DeathPartial> map(List<Death> deaths) {
		List<DeathPartial> deathList = new ArrayList<>();
		
			deaths.stream().forEach(death -> {
			deathList.add(toPartial(death));
			});
			
		return deathList;
	}
	
	public DeathPartial update(UUID deathID, deathCreate deathCreate) throws ResourceNotFoundByIdException {
		
		Death deathToUpdate = deathRepository.findById(deathID)
					.orElseThrow(() -> new ResourceNotFoundByIdException("le deces evacuateur est inexistant"));
		
		User beathDeclarationBy = userRepository.findById(deathCreate.getDeathDeclarationByID())
				.orElseThrow(() -> new ResourceNotFoundByIdException("utilisateur inexistant"));
		deathToUpdate.setDeathDeclarationBy(beathDeclarationBy);
		
		Patient patient = patientService.findById(deathCreate.getPatientID());
		if (patient == null) throw new ResourceNotFoundByIdException("Patient inexistant");
		deathToUpdate.setPatient(patient);
		
		BeanUtils.copyProperties(deathCreate,deathToUpdate,"id");
		deathToUpdate.setDeathDate(deathCreate.getDeathDate());
		deathToUpdate.setDeathDeclarationDate(deathCreate.getDeathDeclarationDate());
		deathToUpdate.setUpdatededAt(new Date());
		deathToUpdate.setUpdatedBy(getCurrentUser().getId());
		
		return toPartial(deathRepository.save(deathToUpdate));
	}
	
	 
	public DeathPartial create(deathCreate deathCreate) throws ResourceNotFoundByIdException {
		Death death = new Death();
		
		User beathDeclarationBy = userRepository.findById(deathCreate.getDeathDeclarationByID())
				.orElseThrow(() -> new ResourceNotFoundByIdException("utilisateur inexistant"));
		death.setDeathDeclarationBy(beathDeclarationBy);
		
		Patient patient = patientService.findById(deathCreate.getPatientID());
		if (patient == null) throw new ResourceNotFoundByIdException("Patient inexistant");
		death.setPatient(patient);
		
		BeanUtils.copyProperties(deathCreate,death,"id");
		death.setCode("GMHIS-DTH-246");
		death.setCreatedAt(new Date());
		death.setDeathDeclarationDate(deathCreate.getDeathDeclarationDate());
		death.setCreatedBy(getCurrentUser().getId());
		
		return toPartial(deathRepository.save(death));
	}
	
	public DeathPartial retrieve (UUID deathID) throws ResourceNotFoundByIdException {
		Death death = deathRepository.findById(deathID)
				.orElseThrow(() -> new ResourceNotFoundByIdException("le deces evacuateur est inexistant"));
		return toPartial(death);
	}
	
public ResponseEntity<Map<String, Object>>  search(Map<String, ?> deathSearchFields) {
		
		Map<String, Object> response = new HashMap<>();
		
		int page = (int) deathSearchFields.get("page");
		String[] sort = (String[]) deathSearchFields.get("sort");
		int size = (int) deathSearchFields.get("size");
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
	    Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		Page<Death> pDeaths = null;

		pDeaths = deathRepository.findAll(pageable);
		
		List<Death> deaths = pDeaths.getContent();
		
		List<DeathPartial> death = map(deaths);
		
		response.put("items", death);
		response.put("totalElements", pDeaths.getTotalElements());
		response.put("totalPages", pDeaths.getTotalPages());
		response.put("size", pDeaths.getSize());
		response.put("pageNumber", pDeaths.getNumber());
		response.put("numberOfElements", pDeaths.getNumberOfElements());
		response.put("first", pDeaths.isFirst());
		response.put("last", pDeaths.isLast());
		response.put("empty", pDeaths.isEmpty());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}}
