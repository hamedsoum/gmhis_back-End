package com.gmhis_backk.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.gmhis_backk.domain.ActCode;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.ActCodeDto;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ActCodeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/actCode")
public class ActCodeController {
	
	@Autowired
	ActCodeService actCodeService;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/list")
	@ApiOperation("liste paginee de tous les codes d'acte dans le systeme")
	public ResponseEntity<Map<String, Object>>getAllActCategory(
			
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {
		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<ActCode> pageActCode;
		
		pageActCode = actCodeService.findAllActCode(paging);
		
		if (StringUtils.isNotBlank(active)) {

			pageActCode = actCodeService.findAllActCodeByActiveAndName(name.trim(), Boolean.parseBoolean(active), paging);

		} else if (StringUtils.isNotBlank(name)) {

			pageActCode = actCodeService.findAllActCodeByName(name.trim(), paging);
		}else {
			pageActCode = actCodeService.findAllActCode(paging);
		}
		
		List<ActCode> actCodeList = pageActCode.getContent();

		
		List<Map<String, Object>> actCodes = this.getMapFromActCodeList(actCodeList);

		response.put("items", actCodes);
		response.put("currentPage", pageActCode.getNumber());
		response.put("totalItems", pageActCode.getTotalElements());
		response.put("totalPages", pageActCode.getTotalPages());
		response.put("size", pageActCode.getSize());
		response.put("first", pageActCode.isFirst());
		response.put("last", pageActCode.isLast());
		response.put("empty", pageActCode.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	protected List<Map<String, Object>> getMapFromActCodeList(List<ActCode> actCodes) {
		List<Map<String, Object>> actCodeList = new ArrayList<>();
		actCodes.stream().forEach(actCodeDto -> {

			Map<String, Object> actGroupsMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(actCodeDto.getCreatedBy()) ? new User()
					: userRepository.findById(actCodeDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(actCodeDto.getUpdatedBy()) ? new User()
					: userRepository.findById(actCodeDto.getUpdatedBy()).orElse(null);
			actGroupsMap.put("id", actCodeDto.getId());
			actGroupsMap.put("name", actCodeDto.getName());
			actGroupsMap.put("active", actCodeDto.getActive());
			actGroupsMap.put("value", actCodeDto.getValue());
			actGroupsMap.put("createdAt", actCodeDto.getCreatedAt());
			actGroupsMap.put("updatedAt", actCodeDto.getUpdatedAt());
			actGroupsMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			actGroupsMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			actGroupsMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			actGroupsMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			actCodeList.add(actGroupsMap);
		});
		return actCodeList;
	}
	
	@PostMapping("/add")
	@ApiOperation("Ajouter un code d'acte")
	public ResponseEntity<ActCode> addActCode(@RequestBody ActCodeDto actCodeDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		ActCode actCode = actCodeService.addActCode(actCodeDto);
		return new ResponseEntity<ActCode>(actCode,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier un code d'acte dans le systeme")
	public ResponseEntity<ActCode>updateGroup(@PathVariable("id") Long id,@RequestBody ActCodeDto actCodeDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		ActCode updateCode = actCodeService.updateActCode(id, actCodeDto);
		return new ResponseEntity<>(updateCode,HttpStatus.OK);
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'un code d'acte ")
	public  ResponseEntity<Optional<ActCode>> getDetail(@PathVariable Long id){
		Optional<ActCode> actCode= actCodeService.getActCodeDetails(id);
		return new ResponseEntity<>(actCode,HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste des ids et noms des codes d'actes actives dans le système")
	@GetMapping("/active_actCode_name")
	public ResponseEntity<List<Map<String, Object>>>  activeActCategoryName() {
		List<Map<String, Object>>  actCodeList = new ArrayList<>();

		actCodeService.findAllActive().forEach(actCodeDto -> {
			Map<String, Object> actCodeMap = new HashMap<>();
			actCodeMap.put("id", actCodeDto.getId());
			actCodeMap.put("name", actCodeDto.getName());
			actCodeList.add(actCodeMap);
		});
		
		return new ResponseEntity<>(actCodeList, HttpStatus.OK);
	}

}
