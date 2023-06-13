package com.gmhis_backk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
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

import com.gmhis_backk.domain.Admission;
import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.dto.PraticianDto;
import com.gmhis_backk.exception.domain.EmailExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.exception.domain.TelephoneExistException;
import com.gmhis_backk.service.PracticianService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/practician")
public class PracticianController {
	
	@Autowired
	PracticianService practicianService;
	
	@ApiOperation(value = "Lister la liste des ids et noms des praticiens actifs dans le système")
	@GetMapping("/active_practicians_name")
	public ResponseEntity<List<Map<String, Object>>> activePracticianName() {
		List<Map<String, Object>> practicianList = new ArrayList<>();

		practicianService.findActivePracticians().stream().forEach(practicianDto -> {
			Map<String, Object> practicianMap = new HashMap<>();
			practicianMap.put("userId", practicianDto.getId());
			practicianMap.put("userFirstName", practicianDto.getUser().getFirstName());
			practicianMap.put("userLastName", practicianDto.getUser().getLastName());
			practicianList.add(practicianMap);
		});

		return new ResponseEntity<>(practicianList, HttpStatus.OK);
	}
	
	
	@ApiOperation(value="Ajouter un pratician")
	@PostMapping("/add")
	public ResponseEntity<Pratician> savePratician(@Valid @RequestBody() PraticianDto praticianDto) throws ResourceNotFoundByIdException, EmailExistException, TelephoneExistException{
		Pratician pratician = practicianService.savePractician(praticianDto);
		return new ResponseEntity<>(pratician,HttpStatus.CREATED);
	}
	
	@ApiOperation(value="Liste global des praticians")
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> paginatedList(
			@RequestParam(required=false, defaultValue="") String nom,
			@RequestParam(required=false, defaultValue="") String prenoms,
			@RequestParam(required=false,defaultValue="") Integer specility,
			@RequestParam(defaultValue = "id,desc") String[] sort, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "50") int size){
		
		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<Pratician> practicianPage = null;
		
		practicianPage = practicianService.findAllP(paging);
		
		if( ObjectUtils.isNotEmpty(nom) ||  ObjectUtils.isNotEmpty(prenoms) ) {
			//practicianPage = practicianService.findByNomAndPrenoms(nom,prenoms);
		}
	
		response.put("items", practicianPage.getContent());
		response.put("currentPage", practicianPage.getNumber());
		response.put("totalItems", practicianPage.getTotalElements());
		response.put("totalPages", practicianPage.getTotalPages());
		response.put("size", practicianPage.getSize());
		response.put("first", practicianPage.isFirst());
		response.put("last", practicianPage.isLast());
		response.put("empty", practicianPage.isEmpty());
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
		
}
