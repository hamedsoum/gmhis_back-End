package com.gmhis_backk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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

import com.gmhis_backk.domain.CashRegisterManagement;
import com.gmhis_backk.domain.Drug;
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
	public ResponseEntity<Map<String, Object>> getCashRegisterManagement(@PathVariable("id") UUID id){
		Map<String, Object> response = new HashMap<>();
		CashRegisterManagement cashRegisterManagement= cashRegisterManagementService.getCashRegisterManagement(id).orElse(null);
		 
		response.put("id",cashRegisterManagement.getId());
		response.put("cashier",cashRegisterManagement.getCashRegister().getName());
		response.put("cashRegister",cashRegisterManagement.getCashier().getFirstName() + " " + cashRegisterManagement.getCashier().getLastName());
		response.put("openingDate",cashRegisterManagement.getCashRegister().getName());
		response.put("state",cashRegisterManagement.getState());
		response.put("openingBalance",cashRegisterManagement.getOpeningBalance());
		response.put("closingDate",cashRegisterManagement.getClosingDate());
		response.put("realClosingBalance",cashRegisterManagement.getRealClosingBalance());
		return new ResponseEntity<>(response, HttpStatus.OK) ;
	}
	
	
	@ApiOperation(value = "Retourner la liste des activite de caisse")
	@GetMapping()
	public ResponseEntity<Map<String, Object>>getCashRegisterManagements(
			@RequestParam(required = false, defaultValue = "") String cashier,
			@RequestParam(required = false, defaultValue = "") String cashRegister,
			@RequestParam(required = false, defaultValue = "") String state,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort){
		
		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));

		Page<CashRegisterManagement> pageCRM ;
		
		if (ObjectUtils.isNotEmpty(cashRegister) && ObjectUtils.isEmpty(cashier) && StringUtils.isEmpty(state)) {
			 pageCRM = cashRegisterManagementService.findAllCashRegistersMangementByCashRegister(Long.parseLong(cashRegister),paging);
		}else if(ObjectUtils.isEmpty(cashRegister) && ObjectUtils.isNotEmpty(cashier) && StringUtils.isEmpty(state)) {
			 pageCRM = cashRegisterManagementService.findAllCashRegistersMangementByCashier(Long.parseLong(cashier),paging);	
		}else if (ObjectUtils.isEmpty(cashRegister) && ObjectUtils.isEmpty(cashier) && StringUtils.isNotEmpty(state)) {
			 pageCRM = cashRegisterManagementService.findAllCashRegistersMangementByState(Boolean.parseBoolean(state),paging);	
		}else if (ObjectUtils.isNotEmpty(cashRegister) && ObjectUtils.isNotEmpty(cashier) && StringUtils.isEmpty(state)) {
			 pageCRM = cashRegisterManagementService.findAllCashRegistersMangementByCashRegisterAndCashier(Long.parseLong(cashRegister),Long.parseLong(cashier),paging);		
		}else if (ObjectUtils.isNotEmpty(cashRegister) && ObjectUtils.isEmpty(cashier) && StringUtils.isNotEmpty(state)) {
			 pageCRM = cashRegisterManagementService.findAllCashRegistersMangementByCashRegisterAndState(Long.parseLong(cashRegister), Boolean.parseBoolean(state),paging);		
		}else if (ObjectUtils.isEmpty(cashRegister) && ObjectUtils.isNotEmpty(cashier) && StringUtils.isNotEmpty(state)) {
			 pageCRM = cashRegisterManagementService.findAllCashRegistersMangementByCashierAndState(Long.parseLong(cashier), Boolean.parseBoolean(state),paging);		
		}else if (ObjectUtils.isNotEmpty(cashRegister) && ObjectUtils.isNotEmpty(cashier) && StringUtils.isNotEmpty(state)) {
			 pageCRM = cashRegisterManagementService.findAllCashRegistersMangementByCashRegisterAndCashierAndState(Long.parseLong(cashRegister),Long.parseLong(cashier), Boolean.parseBoolean(state),paging);		
		}
		else {
			 pageCRM = cashRegisterManagementService.findAllCashRegistersMangement(paging);
		}
		
		List<CashRegisterManagement> crMList = pageCRM.getContent();
		
		List<Map<String, Object>> crMs = this.getMaFromCashRegisterManagementlist(crMList);
		
		response.put("items", crMs);
		response.put("currentPage", pageCRM.getNumber());
		response.put("totalItems", pageCRM.getTotalElements());
		response.put("totalPages", pageCRM.getTotalPages());
		response.put("size", pageCRM.getSize());
		response.put("first", pageCRM.isFirst());
		response.put("last", pageCRM.isLast());
		response.put("empty", pageCRM.isEmpty());
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	
	private List<Map<String, Object>> getMaFromCashRegisterManagementlist(List<CashRegisterManagement> cashRegisterManagements) {
		List<Map<String, Object>> crManagenementList = new ArrayList<>();
		
		cashRegisterManagements.stream().forEach(el -> {
			
			Map<String, Object> crmMap = new HashMap<>();
			crmMap.put("id",el.getId());
			crmMap.put("cashRegisterName",el.getCashRegister().getName());
			crmMap.put("cashRegister",el.getCashRegister().getId());
			crmMap.put("cashierName",el.getCashier().getFirstName() + " " + el.getCashier().getLastName());
			crmMap.put("cashier",el.getCashier().getId());
			crmMap.put("openingDate",el.getOpeningDate());
			crmMap.put("state",el.getState());
			crmMap.put("openingBalance",el.getOpeningBalance());
			crmMap.put("closingBalance",el.getClosingBalance());
			crmMap.put("cashRegisterBalance",el.getCashRegisterBalance());
			crmMap.put("closingDate",el.getClosingDate());
			crmMap.put("realClosingBalance",el.getRealClosingBalance());
			crManagenementList.add(crmMap);
		});
		return crManagenementList;
	}
	
	
	@GetMapping("crM/{cashier}")
	@ApiOperation("detail d'une activite de caisse ")
	public  ResponseEntity<Map<String, Object>> getDetail(@PathVariable Long cashier){
		Map<String, Object> response = new HashMap<>();

		CashRegisterManagement cashRegisterManagement = cashRegisterManagementService.getCashierrManagementByCashierAndStateOpened(cashier);
		response.put("id",cashRegisterManagement.getId());
		response.put("cashRegisterName",cashRegisterManagement.getCashRegister().getName());
		response.put("cashRegister",cashRegisterManagement.getCashRegister().getId());
		response.put("cashierName",cashRegisterManagement.getCashier().getFirstName() + " " + cashRegisterManagement.getCashier().getLastName());
		response.put("cashier",cashRegisterManagement.getCashier().getId());
		response.put("openingDate",cashRegisterManagement.getOpeningDate());
		response.put("state",cashRegisterManagement.getState());
		response.put("openingBalance",cashRegisterManagement.getOpeningBalance());
		response.put("closingBalance",cashRegisterManagement.getClosingBalance());
		response.put("cashRegisterBalance",cashRegisterManagement.getCashRegisterBalance());
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	
	
	
}
