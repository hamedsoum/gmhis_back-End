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
import com.gmhis_backk.domain.Insurance;
import com.gmhis_backk.domain.InsuranceSuscriber;
import com.gmhis_backk.domain.Insured;
import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.InsuredDTO;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.InsuranceService;
import com.gmhis_backk.service.InsuranceSuscriberService;
import com.gmhis_backk.service.InsuredService;

import io.swagger.annotations.ApiOperation;







@RestController
@RequestMapping("/insured")
public class InsuredController {
	
	@Autowired
	private InsuredService insuredService;
	
	@Autowired
	private InsuranceService insuranceService;
	
	@Autowired
	private InsuranceSuscriberService subscriberService;
	
	@Autowired
	UserRepository userRepository;

	
//	@Autowired
//	private PatientService patientService;

	private Insured  insured= null;
	private Insurance  insurance= null;
	private InsuranceSuscriber  insuranceSubscriber= null;
	private Patient patient= null;
	
	protected Long getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername()).getId();
	}
	
	@ApiOperation(value = "Ajouter un assuré ")
	@PostMapping("/add")
	public ResponseEntity<String> addInsured(@RequestBody InsuredDTO insuredDto) throws ResourceNotFoundByIdException  {
		
		insurance = insuranceService.findInsuranceById(insuredDto.getInsurance()).orElse(null);
		
		if (insurance == null) {
			throw new ResourceNotFoundByIdException(
					"L'assurance n'existe pas en base !");
		}
			
		insured= insuredService.findInsuredByCardNumber(insuredDto.getCardNumber());
		if (ObjectUtils.isNotEmpty(insured)) {
			throw new ResourceNotFoundByIdException(
					"La carte d'assurance N° '" + insuredDto.getCardNumber() + "'  à dejà été enregistrer !");
		}
		
		
		
		insuranceSubscriber = subscriberService.getInsuranceSuscriberDetails(insuredDto.getInsuranceSuscriber()).orElse(null);
			
		if (insuranceSubscriber == null) {
			throw new ResourceNotFoundByIdException(
					"Le souscripteur n'existe pas en base !");
		};
	
//		patient = patientService.findById(insuredDto.getPatient());
		
		
		insured= new Insured();
		insured.setActive(insuredDto.getActive());
		insured.setCardNumber(insuredDto.getCardNumber());
		insured.setCoverage(insuredDto.getCoverage());
		insured.setInsurance(insurance);
		insured.setInsuranceSuscriber(insuranceSubscriber);
		insured.setIsPrincipalInsured(insuredDto.getIsPrincipalInsured());
		insured.setPatient(patient);
		insured.setPrincipalInsuredAffiliation(insuredDto.getPrincipalInsuredAffiliation());
		insured.setPrincipalInsuredContact(insuredDto.getPrincipalInsuredContact());
		insured.setPrincipalInsuredName(insuredDto.getPrincipalInsuredName());
		insured.setDeleted("N");
		insured.setCreatedAt(new Date());
		insured.setCreatedBy(this.getCurrentUserId());
		insured= insuredService.saveInsured(insured);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste de tous les assurés  dans le système")
	@GetMapping("/list")
	public ResponseEntity<List<Map<String, Object>>> list() {
		List<Map<String, Object>> insuredList = this.getMapFromInsuredList(insuredService.findInsureds());
		return new ResponseEntity<>(insuredList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste des assurance d'un dans le système")
	@GetMapping("/list_by_patient/{patientId}")
	public ResponseEntity<List<Map<String, Object>>> listByPatient(@PathVariable Long patientId) {
		List<Map<String, Object>> insuredList = this.getMapFromInsuredList(insuredService.findInsuredByPatient(patientId));
		return new ResponseEntity<>(insuredList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste paginee de tous les assurés  dans le système")
	@GetMapping("/p_list")
	public ResponseEntity<Map<String, Object>> paginatedList(
			@RequestParam(required = false, defaultValue = "") String cardNumber,
			@RequestParam(required = false) String active, @RequestParam(defaultValue = "id,desc") String[] sort,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		Map<String, Object> response = new HashMap<>();
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));

		
        Page<Insured> pInsureds = null;
		
			pInsureds = insuredService.findInsureds(pageable);
		
		List<Insured> lInsureds = pInsureds.getContent();
		
		
		
		List<Map<String, Object>> insureds = this.getMapFromInsuredList(lInsureds);
		response.put("content", insureds);
		response.put("totalElements", pInsureds.getTotalElements());
		response.put("totalPages", pInsureds.getTotalPages());
		response.put("size", pInsureds.getSize());
		response.put("pageNumber", pInsureds.getNumber());
		response.put("numberOfElements", pInsureds.getNumberOfElements());
		response.put("first", pInsureds.isFirst());
		response.put("last", pInsureds.isLast());
		response.put("empty", pInsureds.isEmpty());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister la liste de tous les assurés  actifs dans le système")
	@GetMapping("/active_list")
	public ResponseEntity<List<Map<String, Object>>> activeList() {
		List<Map<String, Object>> insuredList = this.getMapFromInsuredList(insuredService.findActiveInsureds());
		return new ResponseEntity<>(insuredList, HttpStatus.OK);
	}
	
	
	protected List<Map<String, Object>> getMapFromInsuredList(List<Insured> insureds) {
		List<Map<String, Object>> insuredList = new ArrayList<>();
		insureds.stream().forEach(insured -> {
			Map<String, Object> insuredsMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(insured.getCreatedBy()) ? new User()
					: userRepository.findById(insured.getCreatedBy()).orElse(null);;
			User updatedBy = ObjectUtils.isEmpty(insured.getUpdatedBy()) ? new User()
					: userRepository.findById(insured.getUpdatedBy()).orElse(null);
			
			insuredsMap.put("id", insured.getId());
			insuredsMap.put("cardNumber", insured.getCardNumber());
			insuredsMap.put("active", insured.getActive());
			insuredsMap.put("coverage", insured.getCoverage());
			insuredsMap.put("insuranceId", insured.getInsurance().getId());
			insuredsMap.put("insuranceName", insured.getInsurance().getName());
			insuredsMap.put("subscriberId", insured.getInsuranceSuscriber().getId());
			insuredsMap.put("subscriberName", insured.getInsuranceSuscriber().getName());
			insuredsMap.put("isPrincipalInsured", insured.getIsPrincipalInsured());
			insuredsMap.put("patientId", insured.getPatient().getId());
			insuredsMap.put("patientFirstName", insured.getPatient().getFirstName());
			insuredsMap.put("patientMaidenName", insured.getPatient().getMaidenName());
			insuredsMap.put("patientLastName", insured.getPatient().getLastName());
			insuredsMap.put("principalInsuredAffiliation", insured.getPrincipalInsuredAffiliation());
			insuredsMap.put("principalInsuredContact", insured.getPrincipalInsuredContact());
			insuredsMap.put("principalInsuredName", insured.getPrincipalInsuredName());
			insuredsMap.put("deleted", insured.getDeleted());
			insuredsMap.put("createdAt", insured.getCreatedAt());
			insuredsMap.put("updatedAt", insured.getUpdatedAt());
			insuredsMap.put("createdByLogin", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLogin());
			insuredsMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			insuredsMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			insuredsMap.put("UpdatedByLogin", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLogin());
			insuredsMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			insuredsMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			insuredList.add(insuredsMap);
		});
		return insuredList;
	}
	
	protected Map<String, Object> getMapFromOneInsured(Insured insured) {
		Map<String, Object> insuredMap = new HashMap<>();
		User createdBy = ObjectUtils.isEmpty(insured.getCreatedBy()) ? new User()
				: userRepository.findById(insured.getCreatedBy()).orElse(null);
		User updatedBy = ObjectUtils.isEmpty(insured.getUpdatedBy()) ? new User()
				: userRepository.findById(insured.getUpdatedBy()).orElse(null);
		insuredMap.put("id", insured.getId());
		insuredMap.put("cardNumber", insured.getCardNumber());
		insuredMap.put("active", insured.getActive());
		insuredMap.put("coverage", insured.getCoverage());
		insuredMap.put("insuranceId", insured.getInsurance().getId());
		insuredMap.put("insuranceName", insured.getInsurance().getName());
		insuredMap.put("subscriberId", insured.getInsuranceSuscriber().getId());
		insuredMap.put("subscriberName", insured.getInsuranceSuscriber().getName());
		insuredMap.put("isPrincipalInsured", insured.getIsPrincipalInsured());
		insuredMap.put("patientId", insured.getPatient().getId());
		insuredMap.put("patientFirstName", insured.getPatient().getFirstName());
		insuredMap.put("patientMaidenName", insured.getPatient().getMaidenName());
		insuredMap.put("patientLastName", insured.getPatient().getLastName());
		insuredMap.put("principalInsuredAffiliation", insured.getPrincipalInsuredAffiliation());
		insuredMap.put("principalInsuredContact", insured.getPrincipalInsuredContact());
		insuredMap.put("principalInsuredName", insured.getPrincipalInsuredName());
		insuredMap.put("deleted", insured.getDeleted());
		insuredMap.put("createdAt", insured.getCreatedAt());
		insuredMap.put("updatedAt", insured.getUpdatedAt());
		insuredMap.put("createdByLogin", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLogin());
		insuredMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
		insuredMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
		insuredMap.put("UpdatedByLogin", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLogin());
		insuredMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
		insuredMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());

		return insuredMap;
	}
	
	@ApiOperation(value = "Lister la liste des ids et noms des assurés actifs dans le système")
	@GetMapping("/active_insureds_name")
	public ResponseEntity<List<Map<String, Object>>> activeInsuredName() {
		List<Map<String, Object>> insuredList = new ArrayList<>();

		insuredService.findActiveInsureds().stream().forEach(insured -> {
			Map<String, Object> insuredMap = new HashMap<>();
			insuredMap.put("id", insured.getId());
			insuredMap.put("patientFirstName", insured.getPatient().getFirstName());
			insuredMap.put("patientMaidenName", insured.getPatient().getMaidenName());
			insuredMap.put("patientLastName", insured.getPatient().getLastName());
			insuredList.add(insuredMap);
		});

		return new ResponseEntity<>(insuredList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retourne les details d'un assuré specifique")
	@GetMapping("/detail/{id}")
	public Object detail(@PathVariable Long id) {
		insured= new Insured();
		insured= insuredService.findInsuredById(id).orElseGet(() -> {
			return null;
		});
		Map<String, Object> insuredMap = this.getMapFromOneInsured(insured);
		return new ResponseEntity<>(insuredMap, HttpStatus.OK);
	}

	@ApiOperation(value = "desactiver un assuré")
	@GetMapping("/disable/{id}")
	public Insured disable(@PathVariable Long id) {
		insured= new Insured();
		insured= insuredService.findInsuredById(id).orElseGet(() -> {
			return null;
		});
		insured.setActive("N");
		insured.setUpdatedAt(new Date());
		insured.setUpdatedBy(this.getCurrentUserId());
		insured= insuredService.saveInsured(insured);
		return insured;
	}

	@ApiOperation(value = "Activer un assuré")
	@GetMapping("/enable/{id}")
	public Insured enable(@PathVariable Long id) {
		insured= new Insured();
		insured= insuredService.findInsuredById(id).orElseGet(() -> {
			return null;
		});
		insured.setActive("Y");
		insured.setUpdatedAt(new Date());
		insured.setUpdatedBy(this.getCurrentUserId());
		insured= insuredService.saveInsured(insured);
		return insured;
	}
	
	@ApiOperation(value = "Modifie un assuré dans le systeme")
	@PutMapping("/update/{id}")
	public Insured update(@RequestBody InsuredDTO insuredDto, @PathVariable Long id) throws ResourceNotFoundByIdException {
		insuredService.findInsuredById(id).ifPresent(updateInsured-> {
			insured= new Insured();
			
			insurance = insuranceService.findInsuranceById(insuredDto.getInsurance()).orElse(null);

			// a voir 
//			if (insurance == null) {
//				throw new ResourceNotFoundByIdException(
//						"L'assurance n'existe pas en base !");
//			}
//			insuranceSubscriber = subscriberService.getInsuranceSuscriberDetails(insuredDto.getInsuranceSuscriber()).orElse(null);
//						
//			if (insuranceSubscriber == null) {
//				throw new ResourceNotFoundByIdException(
//						"Le souscripteur n'existe pas en base !");
//			};
//			patient = patientService.findById(insuredDto.getPatient());
			
			insured= new Insured();
			updateInsured.setActive(insuredDto.getActive());
			updateInsured.setCardNumber(insuredDto.getCardNumber());
			updateInsured.setCoverage(insuredDto.getCoverage());
			updateInsured.setInsurance(insurance);
			updateInsured.setInsuranceSuscriber(insuranceSubscriber);
			updateInsured.setIsPrincipalInsured(insuredDto.getIsPrincipalInsured());
			updateInsured.setPatient(patient);
			updateInsured.setPrincipalInsuredAffiliation(insuredDto.getPrincipalInsuredAffiliation());
			updateInsured.setPrincipalInsuredContact(insuredDto.getPrincipalInsuredContact());
			updateInsured.setPrincipalInsuredName(insuredDto.getPrincipalInsuredName());
			updateInsured.setDeleted("N");
			updateInsured.setCreatedAt(new Date());
			updateInsured.setCreatedBy(this.getCurrentUserId());
			updateInsured.setUpdatedAt(new Date());
			updateInsured.setUpdatedBy(this.getCurrentUserId());
			insured= insuredService.saveInsured(updateInsured);
		});
		return insured;
	}


}
