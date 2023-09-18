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

import com.gmhis_backk.domain.Evacuation;
import com.gmhis_backk.domain.EvacuationCreate;
import com.gmhis_backk.domain.EvacuationPartial;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.EvacuationService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

/**
 * @author  Hamed Soumahoro
 *
 */

@Log4j2
@RestController
@RequestMapping("/evacuations")
public class EvacuationController {

	@Autowired
	private  EvacuationService evacuationService;
	
	@ApiOperation(value = "update existing Evacuation in the system")
	@PutMapping("/{evacuationID}")
	public EvacuationPartial update(@PathVariable UUID evacuationID, @RequestBody EvacuationCreate evacuationCreate) throws ResourceNotFoundByIdException {
		return evacuationService.toPartial(evacuationService.update(evacuationID,evacuationCreate));
	}
	
	@ApiOperation(value = "Create a new Examination in the system")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EvacuationPartial evacuation(@RequestBody EvacuationCreate evacuationCreate) throws ResourceNotFoundByIdException {
		log.info("clinical Controller {} ", evacuationCreate.getClinicalInformation());
		return evacuationService.toPartial(evacuationService.Create(evacuationCreate));
	}
	
	@ApiOperation(value = "Retrieve existing Examination in the system")
	@GetMapping("/{evacuationID}")
	public EvacuationPartial retrieve(@PathVariable UUID evacuationID) throws ResourceNotFoundByIdException {
		return evacuationService.retrieve(evacuationID);
	}
	
	@ApiOperation(value = "find All Examination in the system")
	@GetMapping()
	public ResponseEntity<Map<String, Object>>  search(
			@RequestParam(required = false) Long service,
			@RequestParam(defaultValue = "id,desc") String[] sort,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "25") int size) {
	
		Map<String, Object> evacuationSearch = new HashMap<>();
		
		log.info("service {}", service);
		evacuationSearch.put("service", service);
		evacuationSearch.put("sort", sort);
		evacuationSearch.put("page", page);
		evacuationSearch.put("size", size);

		return evacuationService.search(evacuationSearch);	
	}
}
