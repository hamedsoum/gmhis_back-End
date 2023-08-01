package com.gmhis_backk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Act;
import com.gmhis_backk.domain.ExamenComplementary;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.domain.ExamenComplementaryCreate;
import com.gmhis_backk.domain.ExamenComplementaryPartial;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.ExamenComplementaryRepository;
import com.gmhis_backk.repository.UserRepository;

@Service
@Transactional
public class ExamenComplementaryService {
	
	@Autowired
	private ActService actService;
	
	@Autowired 
	private ExamenComplementaryRepository examenComplementaryRepository;
	
	@Autowired 
	private UserRepository userRepo;
	
	public User getCurrentUser() {
		return this.userRepo.findUserByUsername(AppUtils.getUsername());
	} 
	
	protected List<Map<String, Object>> map(List<ExamenComplementary> examenComplementaries) {
		List<Map<String, Object>> cashierList = new ArrayList<>();
		
		examenComplementaries.stream().forEach(examenComplementary -> {
			Map<String, Object> billMap = new HashMap<>();
			billMap.put("id", examenComplementary.getId());
			billMap.put("actName", examenComplementary.getAct().getName());
			billMap.put("actID", examenComplementary.getAct().getId());
			billMap.put("examenComplementaryType", examenComplementary.getExamenComplementaryType());
			billMap.put("createdAt", examenComplementary.getCreatedAt());
			billMap.put("active", examenComplementary.getActive());

			cashierList.add(billMap);
		});
		return cashierList;
	}
	
	public List<ExamenComplementary> find(){
		return examenComplementaryRepository.findAll();
	}
	
	public ResponseEntity<Map<String, Object>>  search(Map<String, ?> examenSearch) {
		
		Map<String, Object> response = new HashMap<>();
		
		int page = (int) examenSearch.get("page");
		String[] sort = (String[]) examenSearch.get("sort");
		int size = (int) examenSearch.get("size");
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
	    Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		Page<ExamenComplementary> pExamenComplementaries = null;

		pExamenComplementaries = examenComplementaryRepository.findAll(UUID.fromString(getCurrentUser().getFacilityId()), pageable);
		
		List<ExamenComplementary> examenComplementaries = pExamenComplementaries.getContent();
		
		List<Map<String, Object>> examenComplementary = this.map(examenComplementaries);
		response.put("items", examenComplementary);
		response.put("totalElements", pExamenComplementaries.getTotalElements());
		response.put("totalPages", pExamenComplementaries.getTotalPages());
		response.put("size", pExamenComplementaries.getSize());
		response.put("pageNumber", pExamenComplementaries.getNumber());
		response.put("numberOfElements", pExamenComplementaries.getNumberOfElements());
		response.put("first", pExamenComplementaries.isFirst());
		response.put("last", pExamenComplementaries.isLast());
		response.put("empty", pExamenComplementaries.isEmpty());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}

	public ExamenComplementaryPartial
	retrieve(UUID ExamenComplementarID) throws ResourceNotFoundByIdException {
		
		ExamenComplementaryPartial examenComplementaryPartial = new ExamenComplementaryPartial();
		ExamenComplementary examenComplementaryRecord = examenComplementaryRepository.findById(ExamenComplementarID).orElse(null);
		if (examenComplementaryRecord == null) throw new ResourceNotFoundByIdException("examen Complementaire Inexistant");
		
		examenComplementaryPartial.setId(examenComplementaryRecord.getId());
		examenComplementaryPartial.setName(examenComplementaryRecord.getAct().getName());
		examenComplementaryPartial.setActID(examenComplementaryRecord.getAct().getId());
		examenComplementaryPartial.setActive(examenComplementaryRecord.getActive());
		examenComplementaryPartial.setExamenComplementaryType(examenComplementaryRecord.getExamenComplementaryType());
		 return examenComplementaryPartial;
	}

	public ExamenComplementary update(UUID examenComplementaryID, ExamenComplementaryCreate  examenComplementaryCreate) throws ResourceNotFoundByIdException {
		
		ExamenComplementary examenComplementary = examenComplementaryRepository.findById(examenComplementaryID).orElse(null);
		if (examenComplementary == null) throw new ResourceNotFoundByIdException("examen Complementaire Inexistant");
		
		Act act = actService.findActById(examenComplementaryCreate.getActID()).orElse(null);
		if (act == null) throw new ResourceNotFoundByIdException("Act inexistant");
		
		examenComplementary.setActive(examenComplementaryCreate.getActive());
		examenComplementary.setAct(act);
		examenComplementary.setExamenComplementaryType(examenComplementaryCreate.getExamen());
		examenComplementary.setUpdatedAt(new Date());
		examenComplementary.setUpdatedBy(getCurrentUser().getId());
		return examenComplementaryRepository.save(examenComplementary);
	}

	public ExamenComplementary create (ExamenComplementaryCreate examenComplementaryCreate) throws ResourceNotFoundByIdException {
		ExamenComplementary examenComplementary = new ExamenComplementary();
		
		Act act = actService.findActById(examenComplementaryCreate.getActID()).orElse(null);
		if (act == null) throw new ResourceNotFoundByIdException("Act inexistant");

		examenComplementary.setActive(true);
		examenComplementary.setAct(act);
		examenComplementary.setExamenComplementaryType(examenComplementaryCreate.getExamen());
		examenComplementary.setFacilityID(getCurrentUser().getFacility().getId());
		examenComplementary.setCreatedAt(new Date());
		examenComplementary.setCreatedBy(getCurrentUser().getId());
		return examenComplementaryRepository.save(examenComplementary);
	}
	
}
