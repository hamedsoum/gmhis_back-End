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

import com.gmhis_backk.domain.DrugTherapeuticClass;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.DrugTherapeuticClassDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.DrugTherapeuticClassService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/drug_therapeutic_class")
public class DrugTherapeuticClassController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	DrugTherapeuticClassService drugTherapeuticClassService;
	
	@GetMapping("/list")
	@ApiOperation("liste paginee de toutes les classes theraptic dans le systeme")
	public ResponseEntity<Map<String, Object>> getAllTherapetic(
			@RequestParam(name = "name", required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort){
		
		Map<String, Object> response = new HashMap<>();
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));

		Page<DrugTherapeuticClass> pageDrugTherapeuticClass;
		
		pageDrugTherapeuticClass = drugTherapeuticClassService.findAllDrugTherapeuticClass(paging);
		
		if (StringUtils.isNotBlank(active)) {
			pageDrugTherapeuticClass = drugTherapeuticClassService.findAllDrugTherapeuticClassByActiveAndName(active.trim(), Boolean.parseBoolean(active), paging);
		} else if(StringUtils.isNotBlank(name)) {
			pageDrugTherapeuticClass = drugTherapeuticClassService.findAllDrugTherapeuticClassByName(name.trim(), paging);
		}

		List<DrugTherapeuticClass> pageDrugDciList = pageDrugTherapeuticClass.getContent();
		
		List<Map<String, Object>> drugTherapeuticClasses= this.getMapFromDrugTherapeuticClassList(pageDrugDciList);

		response.put("items", drugTherapeuticClasses);
		response.put("currentPage", pageDrugTherapeuticClass.getNumber());
		response.put("totalItems", pageDrugTherapeuticClass.getTotalElements());
		response.put("totalPages", pageDrugTherapeuticClass.getTotalPages());
		response.put("size", pageDrugTherapeuticClass.getSize());
		response.put("first", pageDrugTherapeuticClass.isFirst());
		response.put("last", pageDrugTherapeuticClass.isLast());
		response.put("empty", pageDrugTherapeuticClass.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	protected List<Map<String, Object>> getMapFromDrugTherapeuticClassList(List<DrugTherapeuticClass> drugDcis) {
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
	public ResponseEntity<DrugTherapeuticClass> addDci(@RequestBody DrugTherapeuticClassDto drugTherapeuticClassDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		DrugTherapeuticClass drugDci = drugTherapeuticClassService.addDrugTherapeuticClass(drugTherapeuticClassDto);
		return new ResponseEntity<DrugTherapeuticClass>(drugDci, HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier un dci dans le systeme")
	public ResponseEntity<DrugTherapeuticClass>updateDci(@PathVariable("id") UUID id,@RequestBody DrugTherapeuticClassDto drugTherapeuticClassDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		DrugTherapeuticClass updateTherapeuticClass = drugTherapeuticClassService.updateDrugTherapeuticClass(id, drugTherapeuticClassDto);
		return new ResponseEntity<>(updateTherapeuticClass,HttpStatus.OK);
	}

	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'un dci")
	public  ResponseEntity<Optional<DrugTherapeuticClass>> getDetail(@PathVariable UUID id){
		Optional<DrugTherapeuticClass> drugDci = drugTherapeuticClassService.getDrugTherapeuticClassDetails(id);
		return new ResponseEntity<>(drugDci,HttpStatus.OK);
	}

}
