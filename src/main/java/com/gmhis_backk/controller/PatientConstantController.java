package com.gmhis_backk.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Act;
import com.gmhis_backk.domain.Admission;
import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.domain.PatientConstant;
import com.gmhis_backk.domain.PatientConstantType;
import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.PatientConstantDTO;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.PatientConstantService;
import com.gmhis_backk.service.PatientConstantTypeService;
import com.gmhis_backk.service.PatientService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@RestController
@RequestMapping("/patient_constant")
public class PatientConstantController  {

	@Autowired
	private PatientConstantService constService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired PatientService pService;
	
	@Autowired
	PatientConstantTypeService patientConstantTypeService;
	
	private Patient patient= null;

	@ApiOperation(value = "Ajouter une constante ")
	@PostMapping("/add")
	public ResponseEntity<String> addConstant(@RequestBody List<PatientConstantDTO>  constantDto) {
		constantDto.forEach(constantDTO -> {
			PatientConstant constant = new PatientConstant();
			constant.setObservation(constantDTO.getObservation());
			constant.setValue(constantDTO.getValue());
			constant.setPatient(pService.findById(constantDTO.getPatient()));
			PatientConstantType patientConstant = patientConstantTypeService.findById(constantDTO.getConstant()).orElse(null);
			constant.setConstant(patientConstant);
			constant.setTakenAt(new Date());
			constant.setTakenBy(this.getCurrentUserId().getId());
			constService.save(constant);
		});
		return new ResponseEntity<>( HttpStatus.OK);
	}

	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	@ApiOperation(value = "Lister les constantes")
	@GetMapping("/list")
	public ResponseEntity<List<Map<String, Object>>> list() {
		List<Map<String, Object>> constList = this.getMapFromConstList(constService.findAll());
		return new ResponseEntity<>(constList, HttpStatus.OK);
	}

	@ApiOperation(value = "retourne la liste paginee des constantes")
	@GetMapping("/p_list")
	public ResponseEntity<Map<String, Object>> paginatedList(
			@RequestParam(required = true) Long patientId,
			@RequestParam(required = false) String date,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) {

		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		Page<PatientConstant> pConstantes = null;
		if (ObjectUtils.isNotEmpty(date)) {
			
			Calendar fDate = Calendar.getInstance();
			Calendar tDate = Calendar.getInstance();
			String[] d= date.split("-");
			fDate.set(Integer.parseInt(d[0]), Integer.parseInt(d[1]) - 1 , Integer.parseInt(d[2]), 0, 0);
			tDate.set(Integer.parseInt(d[0]), Integer.parseInt(d[1]) - 1 , Integer.parseInt(d[2]), 23, 59);
			Date date1 = fDate.getTime();
			Date date2 = tDate.getTime();
			
			pConstantes = constService.findPatientConstantByDate(patientId, date1, date2, pageable);
		} else {
			pConstantes = constService.findPatientConstant(patientId, pageable);
		}

		List<PatientConstant> lConstantes = pConstantes.getContent();
		

		List<Map<String, Object>> roles = this.getMapFromConstList(lConstantes);
		response.put("items", roles);
		response.put("currentPage", pConstantes.getNumber());
		response.put("totalItems", pConstantes.getTotalElements());
		response.put("totalPages", pConstantes.getTotalPages());
		response.put("size", pConstantes.getSize());
		response.put("first", pConstantes.isFirst());
		response.put("last", pConstantes.isLast());
		response.put("empty", pConstantes.isEmpty());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	protected List<Map<String, Object>> getMapFromConstList(List<PatientConstant> constantes) {
		List<Map<String, Object>> constList = new ArrayList<>();
		
		constantes.stream().forEach(constant -> {
			
			Map<String, Object> constsMap = new HashMap<>();
			constsMap.put("id", constant.getId());
			constsMap.put("value", constant.getValue());
			constsMap.put("constant", constant.getConstant().getName());
			constsMap.put("observation", constant.getObservation());
			constsMap.put("takenAt", constant.getTakenAt());
			constsMap.put("takenByLogin", constant.getTakenBy());
			constsMap.put("takenByFirstName", constant.getTakenBy());
			constsMap.put("takenByLastName",constant.getTakenBy());

			constList.add(constsMap);
		});
		return constList;
	}

	@ApiOperation(value = "Retourne les details d'une constante")
	@GetMapping("/detail/{id}")
	public PatientConstant detail(@PathVariable Long id) {
		return constService.findById(id);
	}
	
	@ApiOperation(value = "Retourne la liste des constante d'un patient par date")
	@GetMapping("/patientConstantListBydate")
	public ResponseEntity<Map<String, Object>>  patientConstantListByTakenDate(
			@RequestParam(required = true) Long patientId,
			@RequestParam(required = false) String takenAt
			) throws ParseException {
		Map<String, Object> response = new HashMap<>();
		List<PatientConstant> lConstantes = null;

		System.out.print(takenAt);
		lConstantes =  constService.findPatientConstantByDate(patientId, takenAt);
		List<Map<String, Object>> constByDate = this.getMapFromConstList(lConstantes);
		response.put("items", constByDate);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getPatientConstantNumber/{patientId}")
	@ApiOperation("nombre de prise de constante d'un patient ")
	public  ResponseEntity<Map<String, Object>> getPatientConstantNumber(@PathVariable Long patientId){
		Map<String, Object> response = new HashMap<>();
		List<PatientConstant> pConstantes =  constService.findPatientConstant(patientId);
	 constService.findPatientConstant(patientId);
		response.put("PatientConstantNumber",pConstantes.size());
		return new ResponseEntity<>(response, HttpStatus.OK);	}
	
}
