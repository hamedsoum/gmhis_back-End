package com.gmhis_backk.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

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

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Convention;
import com.gmhis_backk.domain.ConventionHasAct;
import com.gmhis_backk.domain.ConventionHasActCode;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.ConventionDTO;
import com.gmhis_backk.dto.ConventionHasActCodeDTO;
import com.gmhis_backk.dto.ConventionHasActDTO;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ActCodeService;
import com.gmhis_backk.service.ActService;
import com.gmhis_backk.service.ConventionService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/convention")
public class ConventionController {
	
	@Autowired
	ConventionService conventionService;
	
	@Autowired
	private ActService actService;

	@Autowired
	private ActCodeService actCodeService;
	
	@Autowired 
	UserRepository userRepository;
	
	private Convention convention = null;

	private ConventionHasActCode conventionActCode = null;
	
	private ConventionHasAct conventionAct = null;

	
	@GetMapping("/list")
	@ApiOperation("liste paginee de toutes les conventions  dans le systeme")
	public ResponseEntity<Map<String, Object>>getAllConvention(
			
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ApplicationErrorException {
		Map<String, Object> response = new HashMap<>();

		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<Convention> pageConvention;
		
		pageConvention = conventionService.findConventions(paging);
		
		if (StringUtils.isNotBlank(active)) {

			pageConvention = conventionService.findByActive(name.trim(), Boolean.parseBoolean(active), paging);

		} else if (StringUtils.isNotBlank(name)) {

			pageConvention = conventionService.findConventionsContaining(name.trim(), paging);
		}else {
			pageConvention = conventionService.findConventions(paging);
		}
		
		List<Convention> conventionList = pageConvention.getContent();

		
		List<Map<String, Object>> conventions= this.getMapFromConventionList(conventionList);

		response.put("items", conventions);
		response.put("currentPage", pageConvention.getNumber());
		response.put("totalItems", pageConvention.getTotalElements());
		response.put("totalPages", pageConvention.getTotalPages());
		response.put("size", pageConvention.getSize());
		response.put("first", pageConvention.isFirst());
		response.put("last", pageConvention.isLast());
		response.put("empty", pageConvention.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	protected List<Map<String, Object>> getMapFromConventionList(List<Convention> convnetions) {
		List<Map<String, Object>> conventionList = new ArrayList<>();
		convnetions.stream().forEach(convnetionDto -> {

			Map<String, Object> convnetionsMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(convnetionDto.getCreatedBy()) ? new User()
					: userRepository.findById(convnetionDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(convnetionDto.getUpdatedBy()) ? new User()
					: userRepository.findById(convnetionDto.getUpdatedBy()).orElse(null);
			convnetionsMap.put("id", convnetionDto.getId());
			convnetionsMap.put("name", convnetionDto.getName());
			convnetionsMap.put("active", convnetionDto.getActive());
			convnetionsMap.put("createdAt", convnetionDto.getCreatedAt());
			convnetionsMap.put("updatedAt", convnetionDto.getUpdatedAt());
			convnetionsMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			convnetionsMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			convnetionsMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			convnetionsMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			conventionList.add(convnetionsMap);
		});
		return conventionList;
	}
	
	@PostMapping("/add")
	@ApiOperation("Ajouter une convnetion")
	@Transactional
	public ResponseEntity<String> addConvnetion(@RequestBody ConventionDTO conventionDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		convention = conventionService.findConventionByName(conventionDto.getName());
		if (convention != null) {
			throw new ResourceNameAlreadyExistException("Le nom de la convention existe déjà "); 
		}	
		convention = new Convention();
		convention.setName(conventionDto.getName());
		convention.setActive(conventionDto.getActive());
		convention.setCreatedAt(new Date());
		convention.setCreatedBy(this.getCurrentUserId().getId());
		convention = conventionService.saveConvention(convention);
		//add acts Codes to convention
		if (ObjectUtils.isNotEmpty(conventionDto.getActCodes())) {
			this.addActCodesToConvention(conventionDto.getActCodes());
		};
		
		//add acts to convention
		if (ObjectUtils.isNotEmpty(conventionDto.getActs())) {
			this.addActsToConvention(conventionDto.getActs());
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier une convnetion dans le systeme")
	@Transactional

	public Convention updateConvention(@PathVariable("id") Long id,@RequestBody ConventionDTO conventionDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		conventionService.findConventionById(id).ifPresent(updateConvention -> {
			convention = new Convention();
			updateConvention.setName(conventionDto.getName());
			updateConvention.setActive(conventionDto.getActive());
			updateConvention.setUpdatedAt(new Date());
			updateConvention.setUpdatedBy(this.getCurrentUserId().getId());
			conventionService.removeAllActCodes(updateConvention);
			conventionService.removeAllActs(updateConvention);
			try {
				convention = conventionService.saveConvention(updateConvention);
			} catch (ResourceNameAlreadyExistException | ResourceNotFoundByIdException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//add acts Codes to convention
			if (ObjectUtils.isNotEmpty(conventionDto.getActCodes())) {
				this.addActCodesToConvention(conventionDto.getActCodes());
			};
			
			//add acts to convention
			if (ObjectUtils.isNotEmpty(conventionDto.getActs())) {
				this.addActsToConvention(conventionDto.getActs());
			}
		});

		return convention;
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'une convnetion ")
	public  ResponseEntity<Optional<Convention>> getDetail(@PathVariable Long id){
		Optional<Convention> convention = conventionService.findConventionById(id);
		return new ResponseEntity<>(convention,HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste des ids et noms des conventions actifs dans le système")
	@GetMapping("/active_convention_name_and_Id")
	public ResponseEntity<List<Map<String, Object>>> activeActName() {

		List<Map<String, Object>> conventionList = new ArrayList<>();

		conventionService.findActiveConventions().stream().forEach(conventionDto -> {
			Map<String, Object> conventionMap = new HashMap<>();
			conventionMap.put("id", conventionDto.getId());
			conventionMap.put("name", conventionDto.getName());
			conventionList.add(conventionMap);
		});

		return new ResponseEntity<>(conventionList, HttpStatus.OK);
	}
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	@ApiOperation(value = "Ajouter des codes d'actes a une convention")
	@PostMapping("/add_act_codes")
	public ResponseEntity<String> addActCodesToConvention(@RequestBody List<ConventionHasActCodeDTO> actCodes){
		actCodes.forEach(ac -> {
			if(ac.getActCode() != null && ac.getValue() != 0) {
			actCodeService.getActCodeDetails(ac.getActCode()).ifPresent(actCode -> {
				conventionActCode = new ConventionHasActCode();
				conventionActCode.setActCode(actCode);
				conventionActCode.setConvention(convention);
				conventionActCode.setValue(ac.getValue());
				conventionActCode.setActive("Y");
				conventionActCode.setCreatedAt(new Date());
				conventionActCode.setCreatedBy(this.getCurrentUserId().getId());
				conventionService.addActCodeToConvention(conventionActCode);
			});
			};
		});
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Ajouter des actes a une convention")
	@PostMapping("/add_acts")
	public ResponseEntity<String> addActsToConvention(@RequestBody List<ConventionHasActDTO> acts){
		acts.forEach(a -> {
			if(a.getAct() != null && a.getCoefficient() != 0) {
				
			actService.findActById(a.getAct()).ifPresent(act -> {
				conventionAct = new ConventionHasAct();
				conventionAct.setAct(act);
				conventionAct.setConvention(convention);
				conventionAct.setCoefficient(a.getCoefficient());
				conventionAct.setActive("Y");
				conventionAct.setCreatedAt(new Date());
				conventionAct.setCreatedBy(this.getCurrentUserId().getId());
				conventionService.addActToConvention(conventionAct);
			});
			};
		});

		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
