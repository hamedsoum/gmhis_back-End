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

import com.gmhis_backk.domain.InsuranceSuscriber;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.InsuranceSubscriberDto;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.InsuranceSuscriberService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/insuranceSubscriber")
public class InsuranceSubscriberController {
	
	@Autowired
	InsuranceSuscriberService insuranceSubscriberService;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/list")
	@ApiOperation("liste paginee de toutes les etablissement garant dans le systeme")
	public ResponseEntity<Map<String, Object>>getAllInsuranceSubscriber(
			
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {
		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<InsuranceSuscriber> pageInsuranceSuscriber;
		
		pageInsuranceSuscriber = insuranceSubscriberService.findAllInsuranceSuscriber(paging);
		
		if (StringUtils.isNotBlank(active)) {

			pageInsuranceSuscriber = insuranceSubscriberService.findAllInsuranceSuscriberByActiveAndName(name.trim(), Boolean.parseBoolean(active), paging);

		} else if (StringUtils.isNotBlank(name)) {

			pageInsuranceSuscriber = insuranceSubscriberService.findAllInsuranceSuscriberByName(name.trim(), paging);
		}else {
			pageInsuranceSuscriber = insuranceSubscriberService.findAllInsuranceSuscriber(paging);
		}
		
		List<InsuranceSuscriber> InsuranceSubscriberList = pageInsuranceSuscriber.getContent();

		
		List<Map<String, Object>> insuranceSubscribers= this.getMapFromInsuranceSubscriberList(InsuranceSubscriberList);

		response.put("items", insuranceSubscribers);
		response.put("currentPage", pageInsuranceSuscriber.getNumber());
		response.put("totalItems", pageInsuranceSuscriber.getTotalElements());
		response.put("totalPages", pageInsuranceSuscriber.getTotalPages());
		response.put("size", pageInsuranceSuscriber.getSize());
		response.put("first", pageInsuranceSuscriber.isFirst());
		response.put("last", pageInsuranceSuscriber.isLast());
		response.put("empty", pageInsuranceSuscriber.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	protected List<Map<String, Object>> getMapFromInsuranceSubscriberList(List<InsuranceSuscriber> ainsuranceSubscribers) {
		List<Map<String, Object>> insuranceSubscriberList = new ArrayList<>();
		ainsuranceSubscribers.stream().forEach(insuranceSubscriberDto -> {

			Map<String, Object> insuranceSubscribersMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(insuranceSubscriberDto.getCreatedBy()) ? new User()
					: userRepository.findById(insuranceSubscriberDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(insuranceSubscriberDto.getUpdatedBy()) ? new User()
					: userRepository.findById(insuranceSubscriberDto.getUpdatedBy()).orElse(null);
			insuranceSubscribersMap.put("id", insuranceSubscriberDto.getId());
			insuranceSubscribersMap.put("name", insuranceSubscriberDto.getName());
			insuranceSubscribersMap.put("active", insuranceSubscriberDto.getActive());
			insuranceSubscribersMap.put("createdAt", insuranceSubscriberDto.getCreatedAt());
			insuranceSubscribersMap.put("updatedAt", insuranceSubscriberDto.getUpdatedAt());
			insuranceSubscribersMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			insuranceSubscribersMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			insuranceSubscribersMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			insuranceSubscribersMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			insuranceSubscriberList.add(insuranceSubscribersMap);
		});
		return insuranceSubscriberList;
	}
	
	
	@PostMapping("/add")
	@ApiOperation("Ajouter un etablissement")
	public ResponseEntity<InsuranceSuscriber> addInsuranceSubscriber(@RequestBody InsuranceSubscriberDto insuranceSubscriberDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		InsuranceSuscriber insuranceSuscriber = insuranceSubscriberService.addInsuranceSuscriber(insuranceSubscriberDto);
		
		return new ResponseEntity<InsuranceSuscriber>(insuranceSuscriber,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier un etablissement dans le systeme")
	public ResponseEntity<InsuranceSuscriber>updateInsuranceSubscriber(@PathVariable("id") Long id,@RequestBody InsuranceSubscriberDto insuranceSubscriberDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		InsuranceSuscriber updateInsuranceSubscriber = insuranceSubscriberService.updateInsuranceSuscriber(id, insuranceSubscriberDto);
		return new ResponseEntity<>(updateInsuranceSubscriber,HttpStatus.OK);
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'un etablissement ")
	public  ResponseEntity<Optional<InsuranceSuscriber>> getDetail(@PathVariable Long id){
		Optional<InsuranceSuscriber> insuranceSubscriber= insuranceSubscriberService.getInsuranceSuscriberDetails(id);
		return new ResponseEntity<>(insuranceSubscriber,HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste des ids et noms des etablissements souscripteur actifs dans le système")
	@GetMapping("/active_subscribers_name")
	public ResponseEntity<List<Map<String, Object>>> activeInsuranceSubscriberName() {
		List<Map<String, Object>> subscriberList = new ArrayList<>();

		insuranceSubscriberService.findAllActiveInsuranceSuscribers().stream().forEach(subscriber -> {
			Map<String, Object> subscriberMap = new HashMap<>();
			subscriberMap.put("id", subscriber.getId());
			subscriberMap.put("name", subscriber.getName());
			subscriberList.add(subscriberMap);
		});

		return new ResponseEntity<>(subscriberList, HttpStatus.OK);
	}
}
