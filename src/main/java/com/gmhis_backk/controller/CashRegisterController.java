package com.gmhis_backk.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.gmhis_backk.domain.CashRegister;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.CashRegisterService;

import io.swagger.annotations.ApiOperation;

@RestController()
@RequestMapping("/cashRegister")
public class CashRegisterController {
	 @Autowired
	 CashRegisterService cashRegisterService;
	 
	 @Autowired
	 UserRepository userRepository;
	 
	
		@GetMapping("/list")
		@ApiOperation("liste paginee de toutes les caisses d'acte dans le systeme")
		public ResponseEntity<Map<String, Object>>getAllCashRegister(
				
				@RequestParam(required = false, defaultValue = "") String name,
				@RequestParam(required = false, defaultValue = "") String active,
				@RequestParam(defaultValue = "0") int page,
				@RequestParam(defaultValue = "10") int size,
				@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {
			Map<String, Object> response = new HashMap<>();

			Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
			
			Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
			
			Page<CashRegister> pageCashRegister;
			
			pageCashRegister = cashRegisterService.findAllCashRegister(paging);
			
			if (StringUtils.isNotBlank(active)) {

				pageCashRegister = cashRegisterService.findAllCashRegisterByActiveAndName(name.trim(), Boolean.parseBoolean(active), paging);

			} else if (StringUtils.isNotBlank(name)) {

				pageCashRegister = cashRegisterService.findAllCashRegisterByName(name.trim(), paging);
			}else {
				pageCashRegister = cashRegisterService.findAllCashRegister(paging);
			}
			
			List<CashRegister> cashRegisterList = pageCashRegister.getContent();

			
			List<Map<String, Object>> CashRegisters= this.getMapFromCashRegisterpList(cashRegisterList);

			response.put("items", CashRegisters);
			response.put("currentPage", pageCashRegister.getNumber());
			response.put("totalItems", pageCashRegister.getTotalElements());
			response.put("totalPages", pageCashRegister.getTotalPages());
			response.put("size", pageCashRegister.getSize());
			response.put("first", pageCashRegister.isFirst());
			response.put("last", pageCashRegister.isLast());
			response.put("empty", pageCashRegister.isEmpty());

			return new ResponseEntity<>(response, OK);
		}
		
		
		protected List<Map<String, Object>> getMapFromCashRegisterpList(List<CashRegister> cashRegisters) {
			List<Map<String, Object>> cashRegisterList = new ArrayList<>();
			cashRegisters.stream().forEach(cashRegisterDto -> {

				Map<String, Object> actGroupsMap = new HashMap<>();
				User createdBy = ObjectUtils.isEmpty(cashRegisterDto.getCreatedBy()) ? new User()
						: userRepository.findById(cashRegisterDto.getCreatedBy()).orElse(null);
				User updatedBy = ObjectUtils.isEmpty(cashRegisterDto.getUpdatedBy()) ? new User()
						: userRepository.findById(cashRegisterDto.getUpdatedBy()).orElse(null);
				actGroupsMap.put("id", cashRegisterDto.getId());
				actGroupsMap.put("name", cashRegisterDto.getName());
				actGroupsMap.put("active", cashRegisterDto.getActive());
				actGroupsMap.put("createdAt", cashRegisterDto.getCreatedAt());
				actGroupsMap.put("updatedAt", cashRegisterDto.getUpdatedAt());
				actGroupsMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
				actGroupsMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
				actGroupsMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
				actGroupsMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
				cashRegisterList.add(actGroupsMap);
			});
			return cashRegisterList;
		}
		
		
		@PostMapping("/add")
		@ApiOperation("Ajouter une caisse")
		public ResponseEntity<CashRegister> addCashRegister(@RequestBody DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException,
		ResourceNotFoundByIdException {
			CashRegister cashRegister = cashRegisterService.addCashRegister(defaultNameAndActiveDto);
			
			return new ResponseEntity<CashRegister>(cashRegister,HttpStatus.OK);
		}
		
		@PutMapping("/update/{id}")
		@ApiOperation("Modifier une caisse dans le systeme")
		public ResponseEntity<CashRegister>updateCashRegister(@PathVariable("id") Long id,@RequestBody DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
			CashRegister updateCashRegister = cashRegisterService.updateCashRegister(id, defaultNameAndActiveDto);
			return new ResponseEntity<>(updateCashRegister,HttpStatus.OK);
		}
		
		@GetMapping("/get-detail/{id}")
		@ApiOperation("detail d'une caisse ")
		public  ResponseEntity<Optional<CashRegister>> getDetail(@PathVariable Long id){
			Optional<CashRegister> cashRegister= cashRegisterService.getCashRegisterDetails(id);
			return new ResponseEntity<>(cashRegister,HttpStatus.OK);
		}

}
