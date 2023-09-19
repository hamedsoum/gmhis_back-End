/**
 * 
 */
package com.gmhis_backk.controller;

import java.util.HashMap;
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

import com.gmhis_backk.domain.DeathPartial;
import com.gmhis_backk.domain.deathCreate;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.DeathService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

/**
 * @author Hamed Soumahoro
 *
 */

@Log4j2
@RestController
@RequestMapping("/deaths")
public class DeathController {

	@Autowired 
	private DeathService deathService;
	
	@ApiOperation(value = "update existing death in the system")
	@PutMapping("/{deathID}")
	public DeathPartial update(@PathVariable UUID deathID, @RequestBody deathCreate deathCreate) throws ResourceNotFoundByIdException {
		return deathService.update(deathID, deathCreate);
	}
	
	@ApiOperation(value = "Create a new Death in the system")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DeathPartial create( @RequestBody deathCreate deathCreate) throws ResourceNotFoundByIdException {
		return deathService.create(deathCreate);
	}
	
	@ApiOperation(value = "Retrieve existing Death in the system")
	@GetMapping("/{deathID}")
	public DeathPartial retrieve(@PathVariable UUID deathID) throws ResourceNotFoundByIdException {
		return deathService.retrieve(deathID);
	}
	
	@ApiOperation(value = "find All Death in the system")
	@GetMapping()
	public ResponseEntity<Map<String, Object>>  search(
			@RequestParam(required = false) Long service,
			@RequestParam(defaultValue = "id,desc") String[] sort,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "25") int size) {
	
		Map<String, Object> deathSearchField = new HashMap<>();
		
		log.info("service {}", service);
		deathSearchField.put("service", service);
		deathSearchField.put("sort", sort);
		deathSearchField.put("page", page);
		deathSearchField.put("size", size);

		return deathService.search(deathSearchField);	
	}
}
