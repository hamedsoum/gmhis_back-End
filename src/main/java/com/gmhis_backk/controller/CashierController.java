/**
 * 
 */
package com.gmhis_backk.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gmhis_backk.domain.Cashier;
import com.gmhis_backk.domain.CashierCreate;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.CashierService;

import io.swagger.annotations.ApiOperation;

/**
 * @author  Hamed Soumahoro
 *
 */

@Log4j2
@RestController
@RequestMapping("/cashiers")
public class CashierController {
	
	@Autowired
	private  CashierService cashierService;
	
	@ApiOperation(value = "update existing Cashier in the system")
	@PutMapping("/{cashierID}")
	public Cashier update(@PathVariable UUID cashierID, @RequestBody CashierCreate cashierCreate) throws ResourceNotFoundByIdException {
		return cashierService.update(cashierID,cashierCreate);
	}
	
	@ApiOperation(value = "Create a new Cashier in the system")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cashier create(@RequestBody CashierCreate cashierCreate) throws ResourceNotFoundByIdException {
		return cashierService.Create(cashierCreate);
	}
	
	@ApiOperation(value = "Retrieve existing Cashier in the system")
	@GetMapping("/{cashierID}")
	public Cashier retrieve(@PathVariable UUID cashierID) throws ResourceNotFoundByIdException {
		return cashierService.retrieve(cashierID);
	}
	
	@ApiOperation(value = "find All Cashiers in the system")
	@GetMapping()
	public ResponseEntity<Map<String, Object>>  search(
			@RequestParam(required = false) Boolean active,
			@RequestParam(defaultValue = "id,desc") String[] sort,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "25") int size) {

		log.info("cashier list");
		HashMap<String, Object> cashierSearch = new HashMap<>();
		
		cashierSearch.put("active", active);
		cashierSearch.put("sort", sort);
		cashierSearch.put("page", page);
		cashierSearch.put("size", size);

		return cashierService.search(cashierSearch);	
	}
	
}
