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

import com.gmhis_backk.domain.DrugDci;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.DrugDciDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.DrugDciService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/drugDci")
public class DrugDciController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	DrugDciService drugDciService;
	
	@GetMapping("/list")
	@ApiOperation("liste paginee de tous les drugDCI dans le systeme")
	public ResponseEntity<Map<String, Object>> getAllDrugDci(
			@RequestParam(name = "name", required = false, defaultValue = "") String drugName,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort){
		
		Map<String, Object> response = new HashMap<>();
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));

		Page<DrugDci> pageDrugDci;
		
		pageDrugDci = drugDciService.findAllDrugDci(paging);
		
		if (StringUtils.isNotBlank(active)) {
			pageDrugDci = drugDciService.findAllDrugDciByActiveAndName(active.trim(), Boolean.parseBoolean(active), paging);
		} else if(StringUtils.isNotBlank(drugName)) {
			pageDrugDci = drugDciService.findAllDrugDciByName(drugName.trim(), paging);
		}

		List<DrugDci> pageDrugDciList = pageDrugDci.getContent();
		
		List<Map<String, Object>> drugDcis= this.getMapFromDrugDciList(pageDrugDciList);

		response.put("items", drugDcis);
		response.put("currentPage", pageDrugDci.getNumber());
		response.put("totalItems", pageDrugDci.getTotalElements());
		response.put("totalPages", pageDrugDci.getTotalPages());
		response.put("size", pageDrugDci.getSize());
		response.put("first", pageDrugDci.isFirst());
		response.put("last", pageDrugDci.isLast());
		response.put("empty", pageDrugDci.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	protected List<Map<String, Object>> getMapFromDrugDciList(List<DrugDci> drugDcis) {
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
	@ApiOperation("Ajouter un DCI dans le systeme")
	public ResponseEntity<DrugDci> addDci(@RequestBody DrugDciDto dciDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		DrugDci drugDci = drugDciService.addDrugDci(dciDto);
		return new ResponseEntity<DrugDci>(drugDci, HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier un dci dans le systeme")
	public ResponseEntity<DrugDci>updateDci(@PathVariable("id") UUID id,@RequestBody DrugDciDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		DrugDci updateDci = drugDciService.updateDrugDci(id, defaultNameAndActiveDto);
		return new ResponseEntity<>(updateDci,HttpStatus.OK);
	}

	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'un dci")
	public  ResponseEntity<Optional<DrugDci>> getDetail(@PathVariable UUID id){
		Optional<DrugDci> drugDci = drugDciService.getDrugDciDetails(id);
		return new ResponseEntity<>(drugDci,HttpStatus.OK);
	}

	@GetMapping("/active_dci_name")
	@ApiOperation(value = "Lister la liste des ids et noms des dci actives dans le système")
	public ResponseEntity<List<Map<String, Object>>>  activePharmacologicalNameAndId() {
		List<Map<String, Object>>  dciList = new ArrayList<>();

		drugDciService.findAllActiveDrugDci().forEach(dciDto -> {
			Map<String, Object> dciMap = new HashMap<>();
			dciMap.put("id", dciDto.getId());
			dciMap.put("name", dciDto.getName());
			dciList.add(dciMap);
		});
		
		return new ResponseEntity<>(dciList, HttpStatus.OK);
	}
}
