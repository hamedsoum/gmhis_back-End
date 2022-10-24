package com.gmhis_backk.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.gmhis_backk.domain.Service;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.ServiceDTO;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ServiceService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/service")
public class ServiceController {
	
	@Autowired
	ServiceService serviceService;
	
	@Autowired
	UserRepository userRepository;
	
	

	@ApiOperation(value = "Lister la liste de tous les services dans le système")
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> ServiceList(
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException{
		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));

	Page<Service> pageService;
		
		pageService = serviceService.findServices(paging);
		
		if (StringUtils.isNotBlank(active)) {

			pageService = serviceService.findByActive(name.trim(), Boolean.parseBoolean(active), paging);

		} else if (StringUtils.isNotBlank(name)) {

			pageService = serviceService.findServicesContaining(name.trim(), paging);
		}else {
			pageService = serviceService.findServices(paging);
		}
		
		List<Service> serviceList = pageService.getContent();

		
		List<Map<String, Object>> actCodes = this.getMapFromServiceList(serviceList);

		response.put("items", actCodes);
		response.put("currentPage", pageService.getNumber());
		response.put("totalItems", pageService.getTotalElements());
		response.put("totalPages", pageService.getTotalPages());
		response.put("size", pageService.getSize());
		response.put("first", pageService.isFirst());
		response.put("last", pageService.isLast());
		response.put("empty", pageService.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	protected List<Map<String, Object>> getMapFromServiceList(List<Service> services) {
		List<Map<String, Object>> serviceList = new ArrayList<>();
		services.stream().forEach(serviceDto -> {

			Map<String, Object> serviceMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(serviceDto.getCreatedBy()) ? new User()
					: userRepository.findById(serviceDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(serviceDto.getUpdatedBy()) ? new User()
					: userRepository.findById(serviceDto.getUpdatedBy()).orElse(null);
			serviceMap.put("id", serviceDto.getId());
			serviceMap.put("name", serviceDto.getName());
			serviceMap.put("active", serviceDto.getActive());
			serviceMap.put("waitingRoom", serviceDto.getWaitingRoom().getName());
			serviceMap.put("facility", serviceDto.getFacility().getName());
			serviceMap.put("createdAt", serviceDto.getCreatedAt());
			serviceMap.put("updatedAt", serviceDto.getUpdatedAt());
			serviceMap.put("createdByLogin", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLogin());
			serviceMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			serviceMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			serviceMap.put("UpdatedByLogin", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLogin());
			serviceMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			serviceMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			serviceList.add(serviceMap);
		});
		return serviceList;
	}
	
	
	@PostMapping("/add")
	@ApiOperation("Ajouter un service d'acte")
	public ResponseEntity<Service> addActCode(@RequestBody ServiceDTO serviceDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		Service service = serviceService.saveService(serviceDto);
		return new ResponseEntity<Service>(service,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier un service d'acte dans le systeme")
	public ResponseEntity<Service>updateGroup(@PathVariable("id") Long id,@RequestBody ServiceDTO serviceDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		Service updateCode = serviceService.updateService(id, serviceDto);
		return new ResponseEntity<>(updateCode,HttpStatus.OK);
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'un service ")
	public  ResponseEntity<Service> getDetail(@PathVariable Long id){
		Service service= serviceService.findServiceById(id);
		return new ResponseEntity<>(service,HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste des ids et noms des services actives dans le système")
	@GetMapping("/active_service_name_and_id")
	public ResponseEntity<List<Map<String, Object>>>  activeActCategoryName() {
		List<Map<String, Object>>  ServiceList = new ArrayList<>();

		serviceService.findActiveServices().forEach(actCodeDto -> {
			Map<String, Object> serviceMap = new HashMap<>();
			serviceMap.put("id", actCodeDto.getId());
			serviceMap.put("name", actCodeDto.getName());
			ServiceList.add(serviceMap);
		});
		
		return new ResponseEntity<>(ServiceList, HttpStatus.OK);
	}
}
