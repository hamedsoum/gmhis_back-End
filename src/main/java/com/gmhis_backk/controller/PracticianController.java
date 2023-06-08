package com.gmhis_backk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.dto.PraticianDto;
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
	public ResponseEntity<Pratician> savePratician(@Valid @RequestBody() PraticianDto praticianDto){
		Pratician pratician = practicianService.savePractician(praticianDto);
		return new ResponseEntity<>(pratician,HttpStatus.CREATED);
	}
	
	
}
