package com.gmhis_backk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.CashRegisterMovement;
import com.gmhis_backk.dto.CashRegisterMovementDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.CashRegisterMovementService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api/v1/cashRegisterMovement")
public class CashRegisterMovementController {
	
   @Autowired
   CashRegisterMovementService cashRegisterMovementService;
	
	@ApiOperation(value = "Enregister un nouveau mouvement de caisse")
	@PostMapping()
	public ResponseEntity<CashRegisterMovement> registerNewCaMovment(@RequestBody CashRegisterMovementDto caDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException{
		CashRegisterMovement cashRegisterMovement = cashRegisterMovementService.addNewMovement(caDto);
		return new ResponseEntity<CashRegisterMovement>(cashRegisterMovement, HttpStatus.OK);
	}

	@ApiOperation(value="retorune la liste pagine des mouvement de caisse")
	@GetMapping()
	public ResponseEntity<Map<String, Object>> getCaMovements(
			@RequestParam(required = false, defaultValue = "") String prestationNumber,
			@RequestParam(required = false, defaultValue = "") Long cashRegister,
			@RequestParam(required = false, defaultValue = "") Long user,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort
			){
		
		Map<String, Object> response = new HashMap<>();
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<CashRegisterMovement> pageCaM;
		
		if (ObjectUtils.isNotEmpty(cashRegister) && ObjectUtils.isEmpty(prestationNumber)) {
			pageCaM = cashRegisterMovementService.getCaMovementNByCashRegister(cashRegister, paging);
		}else if(ObjectUtils.isNotEmpty(cashRegister) && ObjectUtils.isNotEmpty(prestationNumber)) {
			pageCaM = cashRegisterMovementService.getCaMovementNByCashRegisterAndPrestationNumber(cashRegister,prestationNumber, paging);
		}else if(ObjectUtils.isEmpty(cashRegister) && ObjectUtils.isNotEmpty(prestationNumber)) {
			pageCaM = cashRegisterMovementService.getCaMovementNByPrestationNumber(prestationNumber, paging);
		}else if (ObjectUtils.isNotEmpty(user)) {
			pageCaM = cashRegisterMovementService.getCaMovementByUser(user, paging);
		}
		else {
			pageCaM = cashRegisterMovementService.getCaMovement(paging);
		}
		
		List<CashRegisterMovement> caMList = pageCaM.getContent();
		
		List<Map<String, Object>> crMs = this.getMapFromCrMovement(caMList);
		 
		
		response.put("items", crMs);
		response.put("currentPage", pageCaM.getNumber());
		response.put("totalItems", pageCaM.getTotalElements());
		response.put("totalPages", pageCaM.getTotalPages());
		response.put("size", pageCaM.getSize());
		response.put("first", pageCaM.isFirst());
		response.put("last", pageCaM.isLast());
		response.put("empty", pageCaM.isEmpty());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	private List<Map<String, Object>> getMapFromCrMovement(List<CashRegisterMovement> cashRegisterMovementsList){
		
		List<Map<String, Object>> crMovementList = new ArrayList<>();
		
		cashRegisterMovementsList.stream().forEach(el -> {
			Map<String, Object> crMap = new HashMap<>();
			crMap.put("id", el.getId());
			crMap.put("libelle", el.getLibelle());
			crMap.put("debit", el.getDebit());
			crMap.put("credit", el.getCredit());
			crMap.put("caisse", el.getCashRegister().getName());
			crMap.put("actNumber", el.getPrestationNumber());
			crMap.put("date", el.getDate());
			crMap.put("createdBy", el.getUser().getFirstName()+" "+el.getUser().getLastName());
			crMovementList.add(crMap);
		});
		return crMovementList;
	}
}
