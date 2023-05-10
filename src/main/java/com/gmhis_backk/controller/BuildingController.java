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

import com.gmhis_backk.domain.Building;
import com.gmhis_backk.dto.BuildingDto;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.BuildingService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/building")
public class BuildingController {

	@Autowired
	BuildingService buildingService;
	
	
	@PostMapping("/add")
	@ApiOperation("Ajouter un batiment dans le systeme")
	public ResponseEntity<Building> addBuilding(@RequestBody BuildingDto buildingDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		Building addBuilding = buildingService.addBuilding(buildingDto);
		return new ResponseEntity<>(addBuilding,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier une un batiment dans le systeme")
	public ResponseEntity<Building>updateBuilding(@PathVariable("id") Long id,@RequestBody BuildingDto buildingDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		Building updateBuilding = buildingService.updateBuilding(id, buildingDto);
		return new ResponseEntity<>(updateBuilding,HttpStatus.OK);
	}
	
	@GetMapping("/list")
	@ApiOperation("liste pagine de tous les batiments  dans le systeme")
	public ResponseEntity<Map<String, Object>>buildingList(
			
			@RequestParam(required = false, defaultValue = "") String libelle,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {

		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<Building> pageBuilding;
		
		pageBuilding = buildingService.buildingList(libelle, paging);
		
		List<Building> buildingList = pageBuilding.getContent();

		response.put("items", buildingList);
		response.put("currentPage", pageBuilding.getNumber());
		response.put("totalItems", pageBuilding.getTotalElements());
		response.put("totalPages", pageBuilding.getTotalPages());
		response.put("size", pageBuilding.getSize());
		response.put("first", pageBuilding.isFirst());
		response.put("last", pageBuilding.isLast());
		response.put("empty", pageBuilding.isEmpty());
		return new ResponseEntity<>(response, OK);
	}

	@GetMapping("/list-simple")
	@ApiOperation("list simple des batiments d'actes ")
	public  ResponseEntity<List<Building> > simpleList(){
		List<Building> buildingList = buildingService.buildingSimpleList();
		return new ResponseEntity<>(buildingList,HttpStatus.OK);
	}
	
	
	@GetMapping("/detail/{id}")
	@ApiOperation("list simple des batiments d'actes ")
	public  ResponseEntity<Building> getDetail(@PathVariable("id") Long id){
		Building detail = buildingService.getDetail(id);
		return new ResponseEntity<>(detail,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete/{id}")
	@ApiOperation("Supprimer un batiment  ")
	public void deleteBuilding(@PathVariable("id") Long id) throws ResourceNotFoundByIdException{
		 buildingService.deleteBuilding(id);

	}

	
}
