package com.gmhis_backk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
