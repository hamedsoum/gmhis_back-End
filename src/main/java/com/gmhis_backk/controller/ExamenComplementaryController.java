package com.gmhis_backk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.ExamenComplementary;
import com.gmhis_backk.domain.ExamenComplementaryCreate;
import com.gmhis_backk.domain.ExamenComplementaryPartial;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.ExamenComplementaryService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("examen-complementaries")
public class ExamenComplementaryController {
	
	@Autowired
	private ExamenComplementaryService examenComplementaryServie;

	
	@ApiOperation(value = "Find Examens Complementaries in the system")
	@GetMapping("/find")
	public List<Map<String, Object>> find() {
		return examenComplementaryServie.find();
	}
	
	@ApiOperation(value = "Search All Examens Complementaries in the system")
	@GetMapping()
	public ResponseEntity<Map<String, Object>>  search(
			@RequestParam(required = false) Boolean active,
			@RequestParam(defaultValue = "id,desc") String[] sort,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "25") int size) {
	
		Map<String, Object> examenSearch = new HashMap<>();
		
		examenSearch.put("active", active);
		examenSearch.put("sort", sort);
		examenSearch.put("page", page);
		examenSearch.put("size", size);

		return examenComplementaryServie.search(examenSearch);	
	}
	
	@ApiOperation(value = "Retrieve existing Examen Complementary")
	@GetMapping("/{examenComplementaryID}")
	public ExamenComplementaryPartial retrieve(@PathVariable UUID examenComplementaryID) throws ResourceNotFoundByIdException {
		return examenComplementaryServie.retrieve(examenComplementaryID);
	}
	
	@ApiOperation(value = "update existing Examen Complementary")
	@PutMapping("/{examenComplementaryID}")
	public ExamenComplementary update(@PathVariable UUID examenComplementaryID, @RequestBody ExamenComplementaryCreate examenComplementaryCreate) throws ResourceNotFoundByIdException {
		return examenComplementaryServie.update(examenComplementaryID,examenComplementaryCreate);
	}
	
	@ApiOperation(value = "Create new Examen Complementary")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	ExamenComplementary create(@RequestBody ExamenComplementaryCreate examenComplementaryCreate) throws ResourceNotFoundByIdException {
		return examenComplementaryServie.create(examenComplementaryCreate);
	}
}
