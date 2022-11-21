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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.Drug;
import com.gmhis_backk.domain.Facility;
import com.gmhis_backk.dto.DrugDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.DrugService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/drug")
public class DrugController {

	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	DrugService drugService;
	
	@PostMapping("/add")
	@ApiOperation("/Ajouter un medicament")
	public ResponseEntity<Drug>addDrugTtpe(@RequestBody DrugDto dDto) throws ResourceNameAlreadyExistException,ResourceNotFoundByIdException{
		System.out.print(dDto.getName());
		Drug facilityType = drugService.saveDrug(dDto);
		return new ResponseEntity<Drug>(facilityType, HttpStatus.OK);
	} 
	
	@PutMapping("/update/{id}")
	@ApiOperation("/Modifier un medicament")
	public ResponseEntity<Drug> updateDrug(@RequestBody DrugDto dDto, @PathVariable("id") UUID id) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException{
		Drug facilityType = drugService.updateDrug(dDto, id);
		return new ResponseEntity<Drug>(facilityType, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste paginee de toutes les medicamentsdans le système")
	@GetMapping("/p_list")
	public ResponseEntity<Map<String, Object>> paginatedList(
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false) String active, @RequestParam(defaultValue = "id,desc") String[] sort, 
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));

		
       Page<Drug> pFacilities = null;
		
		if (ObjectUtils.isEmpty(active) && ObjectUtils.isEmpty(name)) {
			pFacilities = drugService.findDrugs(pageable);
		} else if(ObjectUtils.isNotEmpty(active)){
			pFacilities = drugService.findByActive(name, Boolean.parseBoolean(active), pageable);
		}else if (ObjectUtils.isNotEmpty(name)) {
			pFacilities= drugService.findDrugsContaining(name,pageable);
		} 
		
		List<Drug> lFacilities = pFacilities.getContent();
		
			
		List<Map<String, Object>> facilities = this.getMapFromDrugList(lFacilities);
		response.put("items", facilities);
		response.put("totalElements", pFacilities.getTotalElements());
		response.put("totalPages", pFacilities.getTotalPages());
		response.put("size", pFacilities.getSize());
		response.put("pageNumber", pFacilities.getNumber());
		response.put("numberOfElements", pFacilities.getNumberOfElements());
		response.put("first", pFacilities.isFirst());
		response.put("last", pFacilities.isLast());
		response.put("empty", pFacilities.isEmpty());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	protected List<Map<String, Object>> getMapFromDrugList(List<Drug> facilitiesTypes) {
		List<Map<String, Object>> drugList = new ArrayList<>();
		facilitiesTypes.stream().forEach(drugDto -> {
			Map<String, Object> drugMap = new HashMap<>();
			drugMap.put("id", drugDto.getId());
			drugMap.put("name", drugDto.getName());
			drugMap.put("active", drugDto.getActive());
			drugList.add(drugMap);
		});
		return drugList;
	}
	
	@GetMapping("/active_drugs_name")
	@ApiOperation(value = "Lister la liste des ids et noms des medicaments actives dans le système")
	public ResponseEntity<List<Map<String, Object>>>  activeDrugName() {
		List<Map<String, Object>>  drugList = new ArrayList<>();

		drugService.findActiveDrugsType().forEach(drugDto -> {
			Map<String, Object> drugMap = new HashMap<>();
			drugMap.put("id", drugDto.getId());
			drugMap.put("name", drugDto.getName());
			drugList.add(drugMap);
		});
		
		return new ResponseEntity<>(drugList, HttpStatus.OK);
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'un medicament ")
	public  ResponseEntity<Map<String, Object>> getDetail(@PathVariable UUID id){
		Map<String, Object> response = new HashMap<>();

		Drug drug = drugService.findDrugById(id).orElse(null);
		response.put("id", drug.getId());
		response.put("facilityName", drug.getName());
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
