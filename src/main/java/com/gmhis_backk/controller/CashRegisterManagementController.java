package com.gmhis_backk.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.CashRegister;
import com.gmhis_backk.domain.CashRegisterManagement;
import com.gmhis_backk.dto.CashRegisterManagementDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.CashRegisterManagementService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/cashRegisterManagement")
public class CashRegisterManagementController {
	
	@Autowired
	CashRegisterManagementService cashRegisterManagementService ;
	
	@ApiOperation(value = "Creer une nouvelle activite de caisse")
	@PostMapping()
	public ResponseEntity<CashRegisterManagement> newCashRegisterManagement(@RequestBody CashRegisterManagementDto crmDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
				CashRegisterManagement cashRegisterManagement = cashRegisterManagementService.addCashRegisterManagement(crmDto);
				return new ResponseEntity<CashRegisterManagement>(cashRegisterManagement, HttpStatus.OK) ;
	}
	
	@ApiOperation(value = "Modifier une activite de caisse")
	@PutMapping("{id}")
	public ResponseEntity<CashRegisterManagement> edit(@PathVariable("id") UUID id, @RequestBody CashRegisterManagementDto crmDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		CashRegisterManagement updateCashRegisterManagement = cashRegisterManagementService.updateCashRegisterManagement(id, crmDto);
		return new ResponseEntity<CashRegisterManagement>(updateCashRegisterManagement, HttpStatus.OK) ;
	}
	
	@ApiOperation(value = "Retourner les details d'une activite de caisse")
	@GetMapping("{id}")
	public ResponseEntity<Map<String, Object>> getProduct(@PathVariable("id") UUID id){
		Map<String, Object> response = new HashMap<>();
		CashRegisterManagement cashRegisterManagement= cashRegisterManagementService.getCashRegisterManagement(id).orElse(null);
		
		response.put("id",cashRegisterManagement.getId());
		response.put("cashier",cashRegisterManagement.getCashRegister().getName());
		response.put("cashRegister",cashRegisterManagement.getCashier().getFirstName() + " " + cashRegisterManagement.getCashier().getLastName());
		response.put("openingDate",cashRegisterManagement.getCashRegister().getName());
		response.put("state",cashRegisterManagement.getState());
		response.put("openingBalance",cashRegisterManagement.getOpeningBalance());
		return new ResponseEntity<>(response, HttpStatus.OK) ;
	}
	
	
}
