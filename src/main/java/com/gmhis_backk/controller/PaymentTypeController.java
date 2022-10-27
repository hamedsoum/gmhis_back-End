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

import com.gmhis_backk.domain.PaymentType;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.PaymentTypeDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.PaymentTypeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/payment_type")
public class PaymentTypeController {
	
	@Autowired
	PaymentTypeService paymentTypeService;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/list")
	@ApiOperation("liste paginee de tous les types de payements dans le systeme")
	public ResponseEntity<Map<String, Object>>getAllActCategory(
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort){
		
		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<PaymentType> pagePayment;
	
	    pagePayment = paymentTypeService.findPaymentTypes(paging);
		
		if (StringUtils.isNotBlank(active)) {

			pagePayment = paymentTypeService.findByActive(name.trim(), Boolean.parseBoolean(active), paging);

		} else if (StringUtils.isNotBlank(name)) {

			pagePayment = paymentTypeService.findPaymentTypesContaining(name.trim(), paging);
		}else {
			pagePayment = paymentTypeService.findPaymentTypes(paging);
		}
		
		List<PaymentType> paymentList = pagePayment.getContent();
		
		List<Map<String, Object>> paymentTypes = this.getMapFromPayementTypeList(paymentList);

		response.put("items", paymentTypes);
		response.put("currentPage", pagePayment.getNumber());
		response.put("totalItems", pagePayment.getTotalElements());
		response.put("totalPages", pagePayment.getTotalPages());
		response.put("size", pagePayment.getSize());
		response.put("first", pagePayment.isFirst());
		response.put("last", pagePayment.isLast());
		response.put("empty", pagePayment.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	protected List<Map<String, Object>> getMapFromPayementTypeList(List<PaymentType> paymentTypes) {
		List<Map<String, Object>> actCodeList = new ArrayList<>();
		paymentTypes.stream().forEach(paymentDto -> {

			Map<String, Object> paymentsMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(paymentDto.getCreatedBy()) ? new User()
					: userRepository.findById(paymentDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(paymentDto.getUpdatedBy()) ? new User()
					: userRepository.findById(paymentDto.getUpdatedBy()).orElse(null);
			paymentsMap.put("id", paymentDto.getId());
			paymentsMap.put("name", paymentDto.getName());
			paymentsMap.put("active", paymentDto.getActive());
			paymentsMap.put("createdAt", paymentDto.getCreatedAt());
			paymentsMap.put("updatedAt", paymentDto.getUpdatedAt());
			paymentsMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			paymentsMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			paymentsMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			paymentsMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			actCodeList.add(paymentsMap);
		});
		return actCodeList;
	}
	
	@ApiOperation(value = "Lister la liste des ids et noms des types de payements actifs dans le système")
	@GetMapping("/active_payment_types_name")
	public ResponseEntity<List<Map<String, Object>>> activePaymentTypeName() {
		List<Map<String, Object>> paymentTypeList = new ArrayList<>();

		paymentTypeService.findActivePaymentTypes().stream().forEach(paymentTypeDto -> {
			Map<String, Object> paymentTypeMap = new HashMap<>();
			paymentTypeMap.put("id", paymentTypeDto.getId());
			paymentTypeMap.put("name", paymentTypeDto.getName());
			paymentTypeList.add(paymentTypeMap);
		});

		return new ResponseEntity<>(paymentTypeList, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	@ApiOperation("Ajouter un type de payement dans le systene")
	public ResponseEntity<PaymentType> addActCode(@RequestBody PaymentTypeDTO paymentTypeDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		PaymentType paymentType = paymentTypeService.createPaymentType(paymentTypeDto);
		return new ResponseEntity<PaymentType>(paymentType,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier un type de payment dans le systeme")
	public ResponseEntity<PaymentType>updateGroup(@PathVariable("id") Long id,@RequestBody PaymentTypeDTO paymentTypeDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		PaymentType updateCode = paymentTypeService.updatePaymentType(id, paymentTypeDto);
		return new ResponseEntity<>(updateCode,HttpStatus.OK);
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'un code d'acte ")
	public  ResponseEntity<Optional<PaymentType>> getDetail(@PathVariable Long id){
		Optional<PaymentType> paymentType= paymentTypeService.findPaymentTypeById(id);
		return new ResponseEntity<>(paymentType,HttpStatus.OK);
	}

}
