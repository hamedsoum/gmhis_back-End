package com.gmhis_backk.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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

import com.gmhis_backk.domain.DrugPharmacologicalForm;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.DrugPharmacologicFormDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.DrugPharmacologicalFormService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/drug_pharmacological_form")
public class DrugPharmacalogicalFormController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	DrugPharmacologicalFormService drugPharmacologicalFormService;
	
	@GetMapping("/list")
	@ApiOperation("liste paginee de toutes les forme pharmacologique dans le systeme")
	public ResponseEntity<Map<String, Object>> getAllPharmacological(
			@RequestParam(name = "name", required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort){
		
		Map<String, Object> response = new HashMap<>();
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));

		Page<DrugPharmacologicalForm> pageDrugPharmacologicalForm;
		
		pageDrugPharmacologicalForm = drugPharmacologicalFormService.findForms(paging);
		
		if (StringUtils.isNotBlank(active)) {
			pageDrugPharmacologicalForm = drugPharmacologicalFormService.findByActive(active.trim(), Boolean.parseBoolean(active), paging);
		} else if(StringUtils.isNotBlank(name)) {
			pageDrugPharmacologicalForm = drugPharmacologicalFormService.findFormsContaining(name.trim(), paging);
		}

		List<DrugPharmacologicalForm> pageDrugDciList = pageDrugPharmacologicalForm.getContent();
		
		List<Map<String, Object>> drugTherapeuticClasses= this.getMapFromDrugPharmacologicalFormList(pageDrugDciList);

		response.put("items", drugTherapeuticClasses);
		response.put("currentPage", pageDrugPharmacologicalForm.getNumber());
		response.put("totalItems", pageDrugPharmacologicalForm.getTotalElements());
		response.put("totalPages", pageDrugPharmacologicalForm.getTotalPages());
		response.put("size", pageDrugPharmacologicalForm.getSize());
		response.put("first", pageDrugPharmacologicalForm.isFirst());
		response.put("last", pageDrugPharmacologicalForm.isLast());
		response.put("empty", pageDrugPharmacologicalForm.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	protected List<Map<String, Object>> getMapFromDrugPharmacologicalFormList(List<DrugPharmacologicalForm> drugDcis) {
		List<Map<String, Object>> drugDciList = new ArrayList<>();
		drugDcis.stream().forEach(drugDciDto -> {

			Map<String, Object> drugDcisMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(drugDciDto.getCreatedBy()) ? new User()
					: userRepository.findById(drugDciDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(drugDciDto.getUpdatedBy()) ? new User()
					: userRepository.findById(drugDciDto.getUpdatedBy()).orElse(null);
			drugDcisMap.put("id", drugDciDto.getId());
			drugDcisMap.put("name", drugDciDto.getName());
			drugDcisMap.put("active", drugDciDto.getActive());
			drugDcisMap.put("createdAt", drugDciDto.getCreatedAt());
			drugDcisMap.put("updatedAt", drugDciDto.getUpdatedAt());
			drugDcisMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			drugDcisMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			drugDcisMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			drugDcisMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			drugDciList.add(drugDcisMap);
		});
		return drugDciList;
	}
	
	
	@PostMapping("/add")
	@ApiOperation("Ajouter une classe dans le systeme")
	public ResponseEntity<DrugPharmacologicalForm> addPharmacological(@RequestBody DrugPharmacologicFormDto drugPharmacologicalDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		DrugPharmacologicalForm drugDci = drugPharmacologicalFormService.saveForm(drugPharmacologicalDto);
		return new ResponseEntity<DrugPharmacologicalForm>(drugDci, HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier un dci dans le systeme")
	public ResponseEntity<DrugPharmacologicalForm>updatePharmacological(@PathVariable("id") UUID id,@RequestBody DrugPharmacologicFormDto drugPharmacologicalDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		DrugPharmacologicalForm updateTherapeuticClass = drugPharmacologicalFormService.updateForm(id, drugPharmacologicalDto);
		return new ResponseEntity<>(updateTherapeuticClass,HttpStatus.OK);
	}

	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'un dci")
	public  ResponseEntity<Optional<DrugPharmacologicalForm>> getDetail(@PathVariable UUID id){
		Optional<DrugPharmacologicalForm> drugDci = drugPharmacologicalFormService.findFormById(id);
		return new ResponseEntity<>(drugDci,HttpStatus.OK);
	}
	
	@GetMapping("/active_pharmacological_form_name")
	@ApiOperation(value = "Lister la liste des ids et noms des formes pharmacologiques actives dans le système")
	public ResponseEntity<List<Map<String, Object>>>  activePharmacologicalNameAndId() {
		List<Map<String, Object>>  drugPharmacologicalList = new ArrayList<>();

		drugPharmacologicalFormService.findActiveForms().forEach(drugPharmacologicalDto -> {
			Map<String, Object> phamarcologicalMap = new HashMap<>();
			phamarcologicalMap.put("id", drugPharmacologicalDto.getId());
			phamarcologicalMap.put("name", drugPharmacologicalDto.getName());
			drugPharmacologicalList.add(phamarcologicalMap);
		});
		
		return new ResponseEntity<>(drugPharmacologicalList, HttpStatus.OK);
	}

}
