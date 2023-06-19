package com.gmhis_backk.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.gmhis_backk.domain.ActCategory;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.ActCategoryDto;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ActCategoryService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/actCategory")
public class ActCategoryController {

	@Autowired
	ActCategoryService actCategoryService;
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/add")
	@ApiOperation("Ajouter une categorie")
	public ResponseEntity<ActCategory> addActCategory(@RequestBody ActCategoryDto actCategoryDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		ActCategory actCategory = actCategoryService.addActCategory(actCategoryDto);
		
		return new ResponseEntity<ActCategory>(actCategory,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier une categorie dans le systeme")
	public ResponseEntity<ActCategory>updateCategory(@PathVariable("id") Long id,@RequestBody ActCategoryDto actCategoryDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		ActCategory updateActCategory = actCategoryService.updateActCategory(id, actCategoryDto);
		return new ResponseEntity<>(updateActCategory,HttpStatus.OK);
	}
	
	@GetMapping("/list")
	@ApiOperation("liste pagine de toutes les categories d'acte dans le systeme")
	public ResponseEntity<Map<String, Object>>getAllActCategory(
			
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {
		System.out.println(name);
		System.out.println(active);
		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<ActCategory> pageActCategory;
		
		pageActCategory = actCategoryService.findAllActCategory(paging);
		
		if (StringUtils.isNotBlank(active)) {

			pageActCategory = actCategoryService.findAllActCategoryByActiveAndName(name.trim(), Boolean.parseBoolean(active), paging);

		} else if (StringUtils.isNotBlank(name)) {

			pageActCategory = actCategoryService.findAllActCategoryByName(name.trim(), paging);
		}else {
			pageActCategory = actCategoryService.findAllActCategory(paging);
		}
		
		List<ActCategory> actcategoryList = pageActCategory.getContent();

		
		List<Map<String, Object>> actCategories = this.getMapFromActCategoryList(actcategoryList);

		response.put("items", actCategories);
		response.put("currentPage", pageActCategory.getNumber());
		response.put("totalItems", pageActCategory.getTotalElements());
		response.put("totalPages", pageActCategory.getTotalPages());
		response.put("size", pageActCategory.getSize());
		response.put("first", pageActCategory.isFirst());
		response.put("last", pageActCategory.isLast());
		response.put("empty", pageActCategory.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	

	protected List<Map<String, Object>> getMapFromActCategoryList(List<ActCategory> actCategories) {
		List<Map<String, Object>> actCategoryList = new ArrayList<>();
		actCategories.stream().forEach(actCategoryDto -> {

			Map<String, Object> actCategoriesMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(actCategoryDto.getCreatedBy()) ? new User()
					: userRepository.findById(actCategoryDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(actCategoryDto.getUpdatedBy()) ? new User()
					: userRepository.findById(actCategoryDto.getUpdatedBy()).orElse(null);
			actCategoriesMap.put("id", actCategoryDto.getId());
			actCategoriesMap.put("name", actCategoryDto.getName());
			actCategoriesMap.put("active", actCategoryDto.getActive());
			actCategoriesMap.put("createdAt", actCategoryDto.getCreatedAt());
			actCategoriesMap.put("updatedAt", actCategoryDto.getUpdatedAt());
			actCategoriesMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			actCategoriesMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			actCategoriesMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			actCategoriesMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			actCategoryList.add(actCategoriesMap);
		});
		return actCategoryList;
	}
	
	@GetMapping("/list-simple")
	@ApiOperation("list simple des categories d'actes ")
	public  ResponseEntity<List<ActCategory> > simpleList(){
		List<ActCategory> ActCategoryList = actCategoryService.findAllActCategories();
		return new ResponseEntity<>(ActCategoryList,HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste des ids et noms des catégories d'actes actives dans le système")
	@GetMapping("/active_categries_name")
	public ResponseEntity<List<Map<String, Object>>>  activeActCategoryName() {
		List<Map<String, Object>>  actCategoryList = new ArrayList<>();

		actCategoryService.findAllActive().forEach(actDto -> {
			Map<String, Object> actMap = new HashMap<>();
			actMap.put("id", actDto.getId());
			actMap.put("name", actDto.getName());
			actCategoryList.add(actMap);
		});
		
		return new ResponseEntity<>(actCategoryList, HttpStatus.OK);
	}
}
