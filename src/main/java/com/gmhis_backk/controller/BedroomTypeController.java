package com.gmhis_backk.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.BedroomType;
import com.gmhis_backk.dto.BedroomTypeDto;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.BedroomTypeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/bedroom-type")
public class BedroomTypeController {

	@Autowired
	BedroomTypeService bedroomTypeService;
	
	
	@PostMapping("/add")
	@ApiOperation("Ajouter un type de chambre dans le systeme")
	public ResponseEntity<BedroomType> addBedroomType(@RequestBody BedroomTypeDto bedroomTypeDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		BedroomType addBedroomType = bedroomTypeService.addBedroomType(bedroomTypeDto);
		return new ResponseEntity<>(addBedroomType,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier un type de chambre dans le systeme")
	public ResponseEntity<BedroomType>updateBedroomType(@PathVariable("id") Long id,@RequestBody BedroomTypeDto bedroomTypeDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		BedroomType updateBedroomType = bedroomTypeService.updateBedroomType(id, bedroomTypeDto);
		return new ResponseEntity<>(updateBedroomType,HttpStatus.OK);
	}
	
	@GetMapping("/list")
	@ApiOperation("liste paginée de tous les types de chambre  dans le systeme")
	public ResponseEntity<Map<String, Object>>buildingList(
			
			@RequestParam(required = false, defaultValue = "") String libelle,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {

		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<BedroomType> pageBedroomType;
		
		pageBedroomType = bedroomTypeService.bedroomTypeList(libelle, paging);
		
		List<BedroomType> bedTypeList = pageBedroomType.getContent();

		response.put("items", bedTypeList);
		response.put("currentPage", pageBedroomType.getNumber());
		response.put("totalItems", pageBedroomType.getTotalElements());
		response.put("totalPages", pageBedroomType.getTotalPages());
		response.put("size", pageBedroomType.getSize());
		response.put("first", pageBedroomType.isFirst());
		response.put("last", pageBedroomType.isLast());
		response.put("empty", pageBedroomType.isEmpty());
		return new ResponseEntity<>(response, OK);
	}

	@GetMapping("/list-simple")
	@ApiOperation("list simple des types de chambre ")
	public  ResponseEntity<List<BedroomType> > simpleList(){
		List<BedroomType> bedroomTypeList = bedroomTypeService.bedroomTypeSimpleList();
		return new ResponseEntity<>(bedroomTypeList,HttpStatus.OK);
	}
	
	
	@GetMapping("/detail/{id}")
	@ApiOperation("detail d'un type de chambre ")
	public  ResponseEntity<BedroomType> getDetail(@PathVariable("id") Long id){
		BedroomType detail = bedroomTypeService.getDetail(id);
		return new ResponseEntity<>(detail,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete/{id}")
	@ApiOperation("Supprimer un type de chambre  ")
	public void deleteBedroomType(@PathVariable("id") Long id) throws ResourceNotFoundByIdException{
		bedroomTypeService.deleteBedroomType(id);

	}

	
}
