package com.gmhis_backk.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.CashRegister;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.domain.UserHasCashRegister;
import com.gmhis_backk.dto.UserHasCashRegisterDTO;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.CashRegisterService;
import com.gmhis_backk.service.UserHasCashRegisterService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@RestController
@RequestMapping("/cashier")
public class UserHasCashRegisterController {

	@Autowired
	private UserHasCashRegisterService cashierService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CashRegisterService cashRegisterService;
	
	@Autowired
	private UserHasCashRegisterService userHasCashRegisterService;

	private UserHasCashRegister cashier = null;
	private User user = null;
	private CashRegister cashRegister = null;

	@ApiOperation(value = "Ajouter un caissier ")
	@PostMapping("/add")
	public ResponseEntity<String> addUserHasCashRegister(@RequestBody UserHasCashRegisterDTO cashierDto) throws ResourceNotFoundByIdException {

		UserHasCashRegister cashierWithSameCashRegister = userHasCashRegisterService.findByUserAndCashRegister(cashierDto.getUser(), cashierDto.getCashRegister());
		
		if (ObjectUtils.isNotEmpty(cashierWithSameCashRegister) ) {
			throw new ResourceNotFoundByIdException(
					"Cette caisse est dejà assignée au caissier !");
		}
		
		
		user = userRepository.getOne(cashierDto.getUser());
		
		if (user == null) {
			throw new ResourceNotFoundByIdException(
					"Le caissier n'existe pas en base !");
		}
	
		
		cashRegister = cashRegisterService.findCashRegisterById(cashierDto.getCashRegister()).orElse(null);
		
		if (cashRegister == null) {
			throw new ResourceNotFoundByIdException(
					"La caissier n'existe pas en base !");
		}
		
       cashier = cashierService.findCashierByUser(cashierDto.getUser());
		
		if (ObjectUtils.isNotEmpty(cashier)) {
			
			if(cashierDto.getActive() == false) {
				userHasCashRegisterService.desactivateAllByUser(cashierDto.getUser());
			}
		}
		
		
		cashier = new UserHasCashRegister();
		cashier.setUser(user);
		cashier.setCashRegister(cashRegister);
		cashier.setActive(cashierDto.getActive());
		cashier.setCreatedAt(new Date());
		cashier.setCreatedBy(this.getCurrentUserId().getId());
		cashier = cashierService.saveCashier(cashier);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	@ApiOperation(value = "Lister la liste de tous les caissiers dans le système")
	@GetMapping("/list")
	public ResponseEntity<List<Map<String, Object>>> list() {
		List<Map<String, Object>> cashiersList = this.getMapFromUserHasCashRegisterList(cashierService.findCashiers());
		return new ResponseEntity<>(cashiersList, HttpStatus.OK);
	}

	@ApiOperation(value = "Lister la liste paginee de tous les caissiers dans le système")
	@GetMapping("/p_list")
	public ResponseEntity<Map<String, Object>> paginatedList(
			@RequestParam(required = false) String firstName,
			@RequestParam(required = false) String lastName,
			@RequestParam(required = false) String phoneContact,
			@RequestParam(required = false) Long cashRegister,
			@RequestParam(required = false) String active,
			@RequestParam(defaultValue = "id,desc") String[] sort,
			@RequestParam(defaultValue = "0") int page,	@RequestParam(defaultValue = "10") int size) {

		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<UserHasCashRegister> pUserHasCashRegisters = null;
		
		if (ObjectUtils.isEmpty(active) && ObjectUtils.isEmpty(cashRegister)) {
			pUserHasCashRegisters = cashierService.findCashiers(firstName, lastName, phoneContact, pageable);
		} if (ObjectUtils.isEmpty(active) && ObjectUtils.isNotEmpty(cashRegister)) {
			pUserHasCashRegisters = cashierService.findByCashRegister(firstName, lastName, phoneContact, cashRegister, pageable);
		}if (ObjectUtils.isNotEmpty(active) && ObjectUtils.isNotEmpty(cashRegister)) {
			pUserHasCashRegisters = cashierService.findCashierByAllFilters(firstName, lastName, phoneContact, active, cashRegister, pageable);
		}if (ObjectUtils.isNotEmpty(active) && ObjectUtils.isEmpty(cashRegister)) {
			pUserHasCashRegisters = cashierService.findByActive(firstName, lastName, phoneContact, active, pageable);
		}
		
		List<UserHasCashRegister> lUserHasCashRegisters = pUserHasCashRegisters.getContent();

		
		List<Map<String, Object>> cashiers = this.getMapFromUserHasCashRegisterList(lUserHasCashRegisters);
		response.put("items", cashiers);
		response.put("pageNumber", pUserHasCashRegisters.getNumber());
		response.put("totalItems", pUserHasCashRegisters.getTotalElements());
		response.put("totalPages", pUserHasCashRegisters.getTotalPages());
		response.put("size", pUserHasCashRegisters.getSize());
		response.put("first", pUserHasCashRegisters.isFirst());
		response.put("last", pUserHasCashRegisters.isLast());
		response.put("empty", pUserHasCashRegisters.isEmpty());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Lister la liste de tous les caissiers actifs dans le système")
	@GetMapping("/active_list")
	public ResponseEntity<List<Map<String, Object>>> activeList() {
		List<Map<String, Object>> cashiersList = this.getMapFromUserHasCashRegisterList(cashierService.findActiveCashiers());
		return new ResponseEntity<>(cashiersList, HttpStatus.OK);
	}

	protected List<Map<String, Object>> getMapFromUserHasCashRegisterList(List<UserHasCashRegister> cashiers) {
		List<Map<String, Object>> cashierList = new ArrayList<>();
		cashiers.stream().forEach(cashierDto -> {
			Map<String, Object> cashiersMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(cashierDto.getCreatedBy()) ? new User()
					: userRepository.findById(cashierDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(cashierDto.getUpdatedBy()) ? new User()
					: userRepository.findById(cashierDto.getUpdatedBy()).orElse(null);
			cashiersMap.put("id", cashierDto.getId());
			cashiersMap.put("userFirstName", cashierDto.getUser().getFirstName());
			cashiersMap.put("userLastName", cashierDto.getUser().getLastName());
			cashiersMap.put("userPhoneContact", cashierDto.getUser().getTel());
			cashiersMap.put("cashRegister", cashierDto.getCashRegister().getName());
			cashiersMap.put("active", cashierDto.getActive());
			cashiersMap.put("createdAt", cashierDto.getCreatedAt());
			cashiersMap.put("updatedAt", cashierDto.getUpdatedAt());
			cashiersMap.put("createdByLogin", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLogin());
			cashiersMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			cashiersMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			cashiersMap.put("UpdatedByLogin", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLogin());
			cashiersMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			cashiersMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			cashierList.add(cashiersMap);
		});
		return cashierList;
	}
	
	protected Map<String, Object> getMapFromOneUserHasCashRegister(UserHasCashRegister cashier) {
		
		Map<String, Object> cashierMap = new HashMap<>();
		User createdBy = ObjectUtils.isEmpty(cashier.getCreatedBy()) ? new User()
				: userRepository.findById(cashier.getCreatedBy()).orElse(null);
		User updatedBy = ObjectUtils.isEmpty(cashier.getUpdatedBy()) ? new User()
				: userRepository.findById(cashier.getUpdatedBy()).orElse(null);
		cashierMap.put("id", cashier.getId());
		cashierMap.put("userId", cashier.getUser().getId());
		cashierMap.put("userFirstName", cashier.getUser().getFirstName());
		cashierMap.put("userLastName", cashier.getUser().getLastName());
		cashierMap.put("cashRegisterId", cashier.getCashRegister().getId());
		cashierMap.put("active", cashier.getActive());
		cashierMap.put("createdAt", cashier.getCreatedAt());
		cashierMap.put("updatedAt", cashier.getUpdatedAt());
		cashierMap.put("createdByLogin", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLogin());
		cashierMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
		cashierMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
		cashierMap.put("UpdatedByLogin", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLogin());
		cashierMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
		cashierMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());

		return cashierMap;
	}

	@ApiOperation(value = "Lister la liste des ids et noms des caissiers actifs dans le système")
	@GetMapping("/active_cashiers_name")
	public ResponseEntity<List<Map<String, Object>>> activeUserHasCashRegisterName() {
		List<Map<String, Object>> cashierList = new ArrayList<>();

		cashierService.findActiveCashiers().stream().forEach(cashierDto -> {
			Map<String, Object> cashierMap = new HashMap<>();
			cashierMap.put("userId", cashierDto.getUser().getId());
			cashierMap.put("userFirstName", cashierDto.getUser().getFirstName());
			cashierMap.put("userLastName", cashierDto.getUser().getLastName());
			cashierList.add(cashierMap);
		});

		return new ResponseEntity<>(cashierList, HttpStatus.OK);
	}

	@ApiOperation(value = "Retourne les details d'un caissier specifique")
	@GetMapping("/detail/{id}")
	public Object detail(@PathVariable Long id) {
		cashier = new UserHasCashRegister();
		cashier = cashierService.findCashierById(id).orElseGet(() -> {
			return null;
		});
		
		Map<String, Object> cashierMap = this.getMapFromOneUserHasCashRegister(cashier);
		return new ResponseEntity<>(cashierMap, HttpStatus.OK);
	}


	@ApiOperation(value = "desactiver un caissier")
	@GetMapping("/disable/{id}")
	public Object disable(@PathVariable Long id) {
		cashier = new UserHasCashRegister();
		cashier = cashierService.findCashierById(id).orElseGet(() -> {
			return null;
		});
		cashier.setActive(false);
		cashier.setUpdatedAt(new Date());
		cashier.setUpdatedBy(this.getCurrentUserId().getId());
		cashier = cashierService.saveCashier(cashier);
		
		Map<String, Object> cashierMap = this.getMapFromOneUserHasCashRegister(cashier);
		return new ResponseEntity<>(cashierMap, HttpStatus.OK);
	}

	@ApiOperation(value = "Activer un caissier")
	@GetMapping("/enable/{id}")
	public Object enable(@PathVariable Long id) {
		cashier = new UserHasCashRegister();
		cashier = cashierService.findCashierById(id).orElseGet(() -> {
			return null;
		});
		cashier.setActive(true);
		cashier.setUpdatedAt(new Date());
		cashier.setUpdatedBy(this.getCurrentUserId().getId());
		cashier = cashierService.saveCashier(cashier);
		
		Map<String, Object> cashierMap = this.getMapFromOneUserHasCashRegister(cashier);
		return new ResponseEntity<>(cashierMap, HttpStatus.OK);		
	}

	@ApiOperation(value = "Modifie un caissier dans le systeme")
	@PutMapping("/update/{id}")
	public Object updateUserHasCashRegister(@RequestBody UserHasCashRegisterDTO cashierDto, @PathVariable Long id) throws ResourceNotFoundByIdException {
		
		user = userRepository.findById(cashierDto.getUser()).orElse(null);
		
		if (user == null) {
			throw new ResourceNotFoundByIdException(
					"Le caissier n'existe pas en base !");
		}
		
		
		
		cashRegister = cashRegisterService.findCashRegisterById(cashierDto.getCashRegister()).orElse(null);
		if (cashRegister == null) {
			throw new ResourceNotFoundByIdException(
					"La caissier n'existe pas en base !");
		}
		

		
		
		cashierService.findCashierById(id).ifPresent(updateUserHasCashRegister -> {
			cashier = new UserHasCashRegister();
			updateUserHasCashRegister.setUser(user);
			updateUserHasCashRegister.setCashRegister(cashRegister);
			updateUserHasCashRegister.setActive(cashierDto.getActive());
			updateUserHasCashRegister.setUpdatedAt(new Date());
			updateUserHasCashRegister.setUpdatedBy(this.getCurrentUserId().getId());
			cashier = cashierService.saveCashier(updateUserHasCashRegister);
		});
		
		Map<String, Object> room = this.getMapFromOneUserHasCashRegister(cashier);
		return new ResponseEntity<>(room, HttpStatus.OK);
		
	}

}
