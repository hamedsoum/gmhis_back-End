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

import com.gmhis_backk.domain.Act;
import com.gmhis_backk.domain.ActCategory;
import com.gmhis_backk.domain.Bill;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.ActDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ActCategoryService;
import com.gmhis_backk.service.ActService;
import com.gmhis_backk.service.BillService;

import io.swagger.annotations.ApiOperation;



@RestController
@RequestMapping("/act")
public class ActController {
	@Autowired
	ActService actService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ActCategoryService actCategoryService;
	
	@Autowired
	BillService billService;
	
	@GetMapping("/list")
	@ApiOperation("liste paginee de tous les codes d'acte dans le systeme")
	public ResponseEntity<Map<String, Object>> getAllAct(
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String active,
			@RequestParam(required = false, defaultValue = "") String category,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort){
		Map<String, Object> response = new HashMap<>();
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<Act> pageAct;

		pageAct = actService.findActs(paging);

		if (StringUtils.isNotBlank(active)) {

			pageAct = actService.findByActive(name.trim(), Boolean.parseBoolean(active), Long.parseLong(category), paging);

		} else if (StringUtils.isNotBlank(name)) {

			pageAct = actService.findActsContaining(name.trim(), paging);
		}
		else if(StringUtils.isNotBlank(category)) {
			pageAct = actService.findActiveActsByCategory(name.trim(), Long.parseLong(category), paging);
		}
		else {
			pageAct = actService.findActs(paging);
		}
		
		List<Act> actList = pageAct.getContent();
		List<Map<String, Object>> acts = this.getMapFromActList(actList);

		response.put("items", acts);
		response.put("currentPage", pageAct.getNumber());
		response.put("totalItems", pageAct.getTotalElements());
		response.put("totalPages", pageAct.getTotalPages());
		response.put("size", pageAct.getSize());
		response.put("first", pageAct.isFirst());
		response.put("last", pageAct.isLast());
		response.put("empty", pageAct.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	
	
	protected List<Map<String, Object>> getMapFromActList(List<Act> acts) {
		List<Map<String, Object>> actList = new ArrayList<>();
		acts.stream().forEach(actDto -> {
			Map<String, Object> actsMap = new HashMap<>();

			actsMap.put("id", actDto.getId());
			actsMap.put("name", actDto.getName());
			actsMap.put("codification", actDto.getCodification());
			actsMap.put("coefficient", actDto.getCoefficient());
			actsMap.put("amount", actDto.getActCode().getValue() * actDto.getCoefficient());
			if (ObjectUtils.isNotEmpty(actDto.getActCategory())) {
				actsMap.put("actCategory", actDto.getActCategory().getName());
			}
			actsMap.put("actCode", actDto.getActCode().getName());
			actsMap.put("actGroup", actDto.getActGroup().getName());
			actsMap.put("active", actDto.getActive());
			actList.add(actsMap);
		});
		return actList;
	}
	
	@PostMapping("/add")
	@ApiOperation("Ajouter un acte")
	public ResponseEntity<Act> addActCode(@RequestBody ActDTO actDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		Act act = actService.addAct(actDto);
		return new ResponseEntity<Act>(act,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@ApiOperation("Modifier un acte dans le systeme")
	public ResponseEntity<Act>updateGroup(@PathVariable("id") Long id,@RequestBody ActDTO actDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException{
		Act updateact = actService.updateAct(id, actDto);
		updateact.getActCategory().getId();
		return new ResponseEntity<>(updateact,HttpStatus.OK);
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'un acte ")
	public  ResponseEntity<Map<String, Object>> getDetail(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();

		Act act= actService.findActById(id).orElse(null);
		response.put("id", act.getId());
		response.put("name", act.getName());
		response.put("codification", act.getCodification());
		response.put("coefficient", act.getCoefficient());
		response.put("amount", act.getActCode().getValue() * act.getCoefficient());
		if (ObjectUtils.isNotEmpty(act.getActCategory())) {
			response.put("actCategory", act.getActCategory().getId());
		}
		response.put("actCode", act.getActCode().getId());
		response.put("actGroup", act.getActGroup().getId());
		response.put("active", act.getActive());
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste des ids et noms des actes actifs dans le système")
	@GetMapping("/active_acts_name")
	public ResponseEntity<List<Map<String, Object>>> activeActName() {
		List<Map<String, Object>> actList = new ArrayList<>();

		actService.findActiveActs().stream().forEach(actDto -> {
			Map<String, Object> actMap = new HashMap<>();
			actMap.put("id", actDto.getId());
			actMap.put("name", actDto.getName());
			actList.add(actMap);
		});

		return new ResponseEntity<>(actList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste des ids et noms des actes actifs par category dans le système")
	@GetMapping("/active_acts_name_by_Category/{categoryId}")
	public ResponseEntity<List<Map<String, Object>>> activeActNameAndIdByCategory(@PathVariable Long categoryId) throws ResourceNotFoundByIdException {
		System.out.print(categoryId);
		ActCategory actCategory = actCategoryService.getActCategoryDetails(categoryId).orElse(null);
			if (actCategory == null) {
				throw new ResourceNotFoundByIdException("aucune specialite d'acte trouvé pour l'identifiant" );
			}
		List<Map<String, Object>> actList = new ArrayList<>();

		actService.findActiveNamesAndIdsActsByCategory(categoryId).stream().forEach(actDto -> {
			Map<String, Object> actMap = new HashMap<>();
			actMap.put("id", actDto.getId());
			actMap.put("name", actDto.getName());
			actList.add(actMap);
		});

		return new ResponseEntity<>(actList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retourne la liste des actes d'une facture")
	@GetMapping("/find-by-bill/{bill_id}")
	public ResponseEntity<List<Map<String, Object>>> findActsByBill( @PathVariable Long bill_id) {
		  System.out.print(bill_id);

		Bill bill = billService.findBillById(bill_id).orElse(null);
	  List<Map<String, Object>> actList = new ArrayList<>();
	  
	  if(bill != null ) {

			actService.findActsByBill(bill.getId()).stream().forEach(actDto -> {
				Map<String, Object> actMap = new HashMap<>();
				actMap.put("id", actDto.getId());
				actMap.put("practicianFirstName", actDto.getPractician().getFirstName());
				actMap.put("practicianLastName", actDto.getPractician().getLastName());
				actMap.put("practicianName", actDto.getPractician().getId());
				actMap.put("bill", actDto.getBill().getId());
				actMap.put("admission", actDto.getAdmission().getId());
				actMap.put("act", actDto.getAct().getId());
				actMap.put("actName", actDto.getAct().getName());
				actMap.put("actCost", actDto.getAct().getActCode().getValue() * actDto.getAct().getCoefficient());
				actList.add(actMap);
			});
	  }
	  
		return new ResponseEntity<>(actList, HttpStatus.OK);

	}

}

