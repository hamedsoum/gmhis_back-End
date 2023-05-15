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

import com.gmhis_backk.domain.Bedroom;
import com.gmhis_backk.dto.BedroomDto;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.BedroomService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/bedroom")
public class BedroomController {

	@Autowired
	BedroomService bedroomService;
	
	
	@PostMapping("/add")
	@ApiOperation("Ajouter une chambre dans le systeme")
	public ResponseEntity<Bedroom> addBedroom(@RequestBody BedroomDto bedroomDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		Bedroom addBedroom = bedroomService.addBedroom(bedroomDto);
		return new ResponseEntity<>(addBedroom,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier une chambre dans le systeme")
	public ResponseEntity<Bedroom>updateBedroom(@PathVariable("id") Long id,@RequestBody BedroomDto bedroomDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		Bedroom updateBedroom = bedroomService.updateBedroom(id, bedroomDto);
		return new ResponseEntity<>(updateBedroom,HttpStatus.OK);
	}
	
	@GetMapping("/list")
	@ApiOperation("liste paginée de toutes les chambres  dans le systeme")
	public ResponseEntity<Map<String, Object>>bedroomList(
			
			@RequestParam(required = false, defaultValue = "") String libelle,
			@RequestParam(required = false, defaultValue = "") Long storeyId,
			@RequestParam(required = false, defaultValue = "") Long type,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {

		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<Bedroom> pageBedroom;
		
		pageBedroom = bedroomService.bedroomList(libelle, storeyId, type, paging);
		
		List<Bedroom> bedroomList = pageBedroom.getContent();

		response.put("items", bedroomList);
		response.put("currentPage", pageBedroom.getNumber());
		response.put("totalItems", pageBedroom.getTotalElements());
		response.put("totalPages", pageBedroom.getTotalPages());
		response.put("size", pageBedroom.getSize());
		response.put("first", pageBedroom.isFirst());
		response.put("last", pageBedroom.isLast());
		response.put("empty", pageBedroom.isEmpty());
		return new ResponseEntity<>(response, OK);
	}

	@GetMapping("/list-simple")
	@ApiOperation("list simple des chambres ")
	public  ResponseEntity<List<Bedroom>> simpleList(){
		List<Bedroom> bedroomList = bedroomService.bedroomSimpleList();
		return new ResponseEntity<>(bedroomList,HttpStatus.OK);
	}
	
	
	@GetMapping("/detail/{id}")
	@ApiOperation("detail d'une chambre ")
	public  ResponseEntity<Bedroom> getDetail(@PathVariable("id") Long id){
		Bedroom detail = bedroomService.getDetail(id);
		return new ResponseEntity<>(detail,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete/{id}")
	@ApiOperation("Supprimer une chambre  ")
	public void deleteBedroom(@PathVariable("id") Long id) throws ResourceNotFoundByIdException{
		bedroomService.deleteBedroom(id);

	}

	
}
