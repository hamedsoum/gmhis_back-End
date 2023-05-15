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

import com.gmhis_backk.domain.Storey;
import com.gmhis_backk.dto.StoreyDto;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.StoreyService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/storey")
public class StoreyController {

	@Autowired
	StoreyService storeyService;
	
	
	@PostMapping("/add")
	@ApiOperation("Ajouter un etage dans le systeme")
	public ResponseEntity<Storey> addStorey(@RequestBody StoreyDto storeyDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		Storey addStorey = storeyService.addStorey(storeyDto);
		return new ResponseEntity<>(addStorey,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier une un etage dans le systeme")
	public ResponseEntity<Storey>updateStorey(@PathVariable("id") Long id,@RequestBody StoreyDto storeyDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		Storey updateStorey = storeyService.updateStorey(id, storeyDto);
		return new ResponseEntity<>(updateStorey,HttpStatus.OK);
	}
	
	@GetMapping("/list")
	@ApiOperation("liste pagine de tous les étages  dans le systeme")
	public ResponseEntity<Map<String, Object>>StoreyList(
			
			@RequestParam(required = false, defaultValue = "") String libelle,
			@RequestParam(required = false, defaultValue = "") Long buildingId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {

		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<Storey> pageStorey;
		
		pageStorey = storeyService.storeyList(libelle, buildingId, paging);
		
		List<Storey> StoreyList = pageStorey.getContent();

		response.put("items", StoreyList);
		response.put("currentPage", pageStorey.getNumber());
		response.put("totalItems", pageStorey.getTotalElements());
		response.put("totalPages", pageStorey.getTotalPages());
		response.put("size", pageStorey.getSize());
		response.put("first", pageStorey.isFirst());
		response.put("last", pageStorey.isLast());
		response.put("empty", pageStorey.isEmpty());
		return new ResponseEntity<>(response, OK);
	}

	@GetMapping("/list-simple")
	@ApiOperation("list simple des étages ")
	public  ResponseEntity<List<Storey> > simpleList(){
		List<Storey> StoreyList = storeyService.findSimpleList();
		return new ResponseEntity<>(StoreyList,HttpStatus.OK);
	}
	
	
	@GetMapping("/detail/{id}")
	@ApiOperation("detail d'un etage ")
	public  ResponseEntity<Storey> getDetail(@PathVariable("id") Long id){
		Storey detail = storeyService.getDetail(id);
		return new ResponseEntity<>(detail,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete/{id}")
	@ApiOperation("Supprimer un etage  ")
	public void deleteStorey(@PathVariable("id") Long id) throws ResourceNotFoundByIdException{
		storeyService.deleteStorey(id);

	}

	
}
