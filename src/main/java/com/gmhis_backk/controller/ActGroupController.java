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

import com.gmhis_backk.domain.ActGroup;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.ActGroupDto;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ActGroupService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/actGroup")
public class ActGroupController {

	@Autowired
	ActGroupService actGroupService;
	
	@Autowired
	UserRepository userRepository;
	
	
	@GetMapping("/list")
	@ApiOperation("liste paginee de toutes les familles d'acte dans le systeme")
	public ResponseEntity<Map<String, Object>>getAllActCategory(
			
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {
		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<ActGroup> pageActGroup;
		
		pageActGroup = actGroupService.findAllActGroup(paging);
		
		if (StringUtils.isNotBlank(active)) {

			pageActGroup = actGroupService.findAllActGroupByActiveAndName(name.trim(), Boolean.parseBoolean(active), paging);

		} else if (StringUtils.isNotBlank(name)) {

			pageActGroup = actGroupService.findAllActGroupByName(name.trim(), paging);
		}else {
			pageActGroup = actGroupService.findAllActGroup(paging);
		}
		
		List<ActGroup> actGroupList = pageActGroup.getContent();

		
		List<Map<String, Object>> actGroups= this.getMapFromActGroupList(actGroupList);

		response.put("items", actGroups);
		response.put("currentPage", pageActGroup.getNumber());
		response.put("totalItems", pageActGroup.getTotalElements());
		response.put("totalPages", pageActGroup.getTotalPages());
		response.put("size", pageActGroup.getSize());
		response.put("first", pageActGroup.isFirst());
		response.put("last", pageActGroup.isLast());
		response.put("empty", pageActGroup.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	protected List<Map<String, Object>> getMapFromActGroupList(List<ActGroup> actGroups) {
		List<Map<String, Object>> actGroupList = new ArrayList<>();
		actGroups.stream().forEach(actGroupDto -> {

			Map<String, Object> actGroupsMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(actGroupDto.getCreatedBy()) ? new User()
					: userRepository.findById(actGroupDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(actGroupDto.getUpdatedBy()) ? new User()
					: userRepository.findById(actGroupDto.getUpdatedBy()).orElse(null);
			actGroupsMap.put("id", actGroupDto.getId());
			actGroupsMap.put("name", actGroupDto.getName());
			actGroupsMap.put("active", actGroupDto.getActive());
			actGroupsMap.put("createdAt", actGroupDto.getCreatedAt());
			actGroupsMap.put("updatedAt", actGroupDto.getUpdatedAt());
			actGroupsMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			actGroupsMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			actGroupsMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			actGroupsMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			actGroupList.add(actGroupsMap);
		});
		return actGroupList;
	}
	
	
	@PostMapping("/add")
	@ApiOperation("Ajouter une famille d'acte")
	public ResponseEntity<ActGroup> addActGroup(@RequestBody ActGroupDto actGroupDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		ActGroup actGroup = actGroupService.addAActGroup(actGroupDto);
		
		return new ResponseEntity<ActGroup>(actGroup,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier une famille d'acte dans le systeme")
	public ResponseEntity<ActGroup>updateGroup(@PathVariable("id") Long id,@RequestBody ActGroupDto actGroupDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		ActGroup updateGroup = actGroupService.updateActGroup(id, actGroupDto);
		return new ResponseEntity<>(updateGroup,HttpStatus.OK);
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'une famille d'acte ")
	public  ResponseEntity<Optional<ActGroup>> getDetail(@PathVariable Long id){
		Optional<ActGroup> actGroup= actGroupService.getActGroupDetails(id);
		return new ResponseEntity<>(actGroup,HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste des ids et noms des familles d'actes actives dans le système")
	@GetMapping("/active_actGroup_name")
	public ResponseEntity<List<Map<String, Object>>>  activeActCategoryName() {
		List<Map<String, Object>>  actGroupList = new ArrayList<>();

		actGroupService.findAllActive().forEach(actGroupto -> {
			Map<String, Object> actGroupMap = new HashMap<>();
			actGroupMap.put("id", actGroupto.getId());
			actGroupMap.put("name", actGroupto.getName());
			actGroupList.add(actGroupMap);
		});
		
		return new ResponseEntity<>(actGroupList, HttpStatus.OK);
	}
}
