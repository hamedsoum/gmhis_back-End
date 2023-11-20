package com.gmhis_backk.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Act;
import com.gmhis_backk.domain.admission.Admission;
import com.gmhis_backk.domain.Bill;
import com.gmhis_backk.domain.BillHasInsured;
import com.gmhis_backk.domain.CashRegister;
import com.gmhis_backk.domain.CashRegisterManagement;
import com.gmhis_backk.domain.Convention;
import com.gmhis_backk.domain.ConventionHasAct;
import com.gmhis_backk.domain.ConventionHasActCode;
import com.gmhis_backk.domain.Files;
import com.gmhis_backk.domain.Insurance;
import com.gmhis_backk.domain.Insured;
import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.domain.Payment;
import com.gmhis_backk.domain.PaymentType;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.AdmisionHasActDTO;
import com.gmhis_backk.dto.BillDTO;
import com.gmhis_backk.dto.CashRegisterManagementDto;
import com.gmhis_backk.dto.CashRegisterMovementDto;
import com.gmhis_backk.dto.PaymentDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.BillHasInsuredRepository;
import com.gmhis_backk.repository.FileDbRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ActCodeService;
import com.gmhis_backk.service.ActService;
import com.gmhis_backk.service.AdmissionService;
import com.gmhis_backk.service.BillHasInsuredService;
import com.gmhis_backk.service.BillService;
import com.gmhis_backk.service.CashRegisterManagementService;
import com.gmhis_backk.service.CashRegisterMovementService;
import com.gmhis_backk.service.CashRegisterService;
import com.gmhis_backk.service.ConventionService;
import com.gmhis_backk.service.InsuranceService;
import com.gmhis_backk.service.InsuredService;
import com.gmhis_backk.service.PaymentTypeService;
import com.gmhis_backk.service.PracticianService;
import com.gmhis_backk.service.UserService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@RestController
@RequestMapping("/bill")
//public class BillController extends BaseController {

@Log4j2
public class BillController {
	@Autowired
	private BillService billService;

	@Autowired
	private AdmissionService admissionService;

	@Autowired
	private ConventionService conventionService;

	@Autowired
	private InsuredService insuredService;

	@Autowired
	private ActService actService;

	@Autowired
	private ActCodeService actCodeService;

	@Autowired
	private CashRegisterService cashRegisterService;

	@Autowired
	private PaymentTypeService paymentTypeService;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private UserService praticianService;
	
	@Autowired 
	private BillHasInsuredRepository billHasInsuredRepository;
	
	@Autowired
	private PracticianService practicianService;
	
	@Autowired
	private InsuranceService insuranceService;
	
	@Autowired
	private BillHasInsuredService billHasInsuredService;
	
	@Autowired 
	private CashRegisterMovementService cashRegisterMovementService;
	
	@Autowired
	private CashRegisterManagementService cashRegisterManagementService;
	
	
	 @Autowired
     private FileDbRepository fileRepository;

	private Admission admission = null;
	private Convention convention = null;
	private Bill bill = null;
	private Insured insured = null;
	private int actCosts = 0;

	@ApiOperation(value = "Ajouter une facture ")
	@PostMapping("/add")
	@Transactional
	public ResponseEntity<Bill> addBill(@RequestBody BillDTO billDto) throws ResourceNotFoundByIdException, ParseException {
//
		admission = admissionService.retrieve(billDto.getAdmission()).orElse(null);
			if (admission == null) {
				throw new ResourceNotFoundByIdException("L'admission n'existe pas en base !");
			}	
		String billNumber = this.getBillNumber();
		bill = new Bill();
		bill.setAdmission(admission);
		bill.setBillNumber(billNumber);
		bill.setBillStatus("R");
		bill.setBillType(billDto.getBillType());
		bill.setToFinalize(billDto.getToFinalize());
		bill.setPartTakenCareOf(billDto.getPartTakenCareOf());
		bill.setPatientPart(billDto.getPatientPart());
		bill.setPatientType(billDto.getPatientType());
		bill.setTotalAmount(billDto.getPatientPart() + billDto.getPartTakenCareOf());
		bill.setCreatedAt(new Date());
		bill.setCreatedBy(this.getCurrentUserId().getId());
		bill = billService.saveBill(bill);

		if(billDto.getInsuredList() != null) {
			billDto.getInsuredList().forEach(billHasInsuredDto -> {
				Admission admission = admissionService.retrieve(billHasInsuredDto.getAdmission()).orElse(null);
				Insured Insured = insuredService.findInsuredById(billHasInsuredDto.getInsured()).orElse(null);
				Insurance insurance = insuranceService.findInsuranceById(billHasInsuredDto.getInsurrance()).orElse(null);
				BillHasInsured billHasInsured = new BillHasInsured();
				billHasInsured.setAdmission(admission);
				billHasInsured.setInsured(Insured);
				billHasInsured.setInsuredCoverage(billHasInsuredDto.getInsuredCoverage());
				billHasInsured.setInsuredPart(billHasInsuredDto.getInsuredPart());
				billHasInsured.setBill(bill);
				billHasInsured.setInsurance(insurance);
				billHasInsured.setCreatedAt(new Date());
				billHasInsured.setCreatedBy(this.getCurrentUserId().getId());

				billHasInsuredRepository.save(billHasInsured);

			});
		}

        
		billDto.getActs().forEach(admissionHasAct -> {
			 practicianService.findPracticianById(admissionHasAct.getPratician()).orElse(null);
			int actCost = 0;
			actCost = this.getOneActCostWhithoutConvention(admissionHasAct.getAct());
			admissionService.addActToAdmission(admissionHasAct, actCost, bill);
		});
		admissionService.setAdmissionStatusToBilled(admission.getId());
		return new ResponseEntity<Bill>(bill,HttpStatus.OK);

	}
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}


	
	public int getActCostWhithoutConvention(List<AdmisionHasActDTO> admissionActs) {

		this.actCosts = 0;

		ArrayList<Act> acts = new ArrayList<Act>();

		admissionActs.forEach(admissionAct -> {
			Act act = this.actService.findActById(admissionAct.getAct()).orElse(null);

			if (ObjectUtils.isNotEmpty(act.getId())) {
				acts.add(act);
			}
		});

		acts.forEach(act -> {

			if (act != null) {
				this.actCosts = this.actCosts + (act.getCoefficient() * act.getActCode().getValue());
			}
		});

		return this.actCosts;
	}
	

	public int getOneActCostWhithoutConvention(Long id) {

		int cost = 0;
		Act act = null;
		act = this.actService.findActById(id).orElse(null);

			if (act != null) {
				cost = act.getCoefficient() * act.getActCode().getValue();
			}
		
		return cost;
	}

	
	public int getActWithConvention(List<AdmisionHasActDTO> admissionActs, Convention convention) {

		this.actCosts = 0;

		ArrayList<Act> acts = new ArrayList<Act>();

		admissionActs.forEach(admissionAct -> {
			Act act = this.actService.findActById(admissionAct.getAct()).orElse(null);

			if (ObjectUtils.isNotEmpty(act.getId()))
				acts.add(act);
		});

		acts.forEach(act -> {

			ConventionHasAct conventionAct = this.actService.findActByConventionAndAct(convention, act);
			
			if (conventionAct != null) {
				int ac = conventionAct.getCoefficient() * conventionAct.getAct().getActCode().getValue();
				this.actCosts = this.actCosts + ac;
			} 

		});

		return this.actCosts;
	}

	
	public int getOneActWithConvention(Long id, Convention convention) {
		int cost = 0;
		Act act = null;
		act = this.actService.findActById(id).orElse(null);

			if (act != null) cost = act.getCoefficient() * act.getActCode().getValue();
			
		
			ConventionHasAct conventionAct = this.actService.findActByConventionAndAct(convention, act);
			ConventionHasActCode conventionActCode = this.actCodeService.findActCodeByConventionAndAct(convention,act.getActCode());

			if (conventionAct != null) {
				cost = conventionAct.getCoefficient() * conventionAct.getAct().getActCode().getValue();
				
			} else if (conventionActCode != null) {
               cost = act.getCoefficient() * conventionActCode.getValue();
			} else {
				cost = act.getCoefficient() * act.getActCode().getValue();
			}
		

		return cost;
	}


	public int getPartTakenCareOf(int actCosts, Insured insured) {

		int partTakenCareOf = (actCosts * insured.getCoverage()) / 100;

		return partTakenCareOf;
	}

	
	public int getPatientPart(int actCosts, Insured insured) {

		int patientPart = (actCosts * (100 - insured.getCoverage())) / 100;

		return patientPart;
	}

	
	public String getBillNumber() {

		Bill lBill = billService.findLastBill();
		Calendar calendar = Calendar.getInstance();
		String month = String.format("%02d", calendar.get(Calendar.MONTH) + 1);
		String year = String.format("%02d", calendar.get(Calendar.YEAR) % 100);
		String lBillYearandMonth = "";
		String lBillNb = "";
		int number = 0;

		if (lBill == null) {
			lBillYearandMonth = year + month;
			lBillNb = "0000";
		} else {
			String an = lBill.getBillNumber().substring(2);
			lBillYearandMonth = an.substring(0, 4);
			lBillNb = an.substring(4);
		}

		if (lBillYearandMonth.equals(year + month)) number = Integer.parseInt(lBillNb) + 1;
		 else number = number + 1;
		

		return "FA" + year + month + String.format("%04d", number);

	}
	
	@ApiOperation(value = " retourne le cout d'un act")
	@GetMapping("/get-act-cost")
	public int getActCost(
			@RequestParam(required = true) Long actId,
			@RequestParam(required = false ) Long conventionId ) {
		
		int cost = 0;
		 cost = this.getOneActCostWhithoutConvention(actId);
		   
		return cost;
	}

	@ApiOperation(value = "Lister la liste paginee de toutes les admission par status (R: register, C: collected)")
	@GetMapping("/p_list")
	public ResponseEntity<Map<String, Object>> paginatedList(
			@RequestParam(required = false, defaultValue = "") String billNumber,
			@RequestParam(required = false, defaultValue = "") String admissionNumber,
			@RequestParam(required = false, defaultValue = "") String firstName,
			@RequestParam(required = false, defaultValue = "") String lastName,
			@RequestParam(required = false) Long convention,
			@RequestParam(required = false) Long insurance,
			@RequestParam(required = false) Long subscriber,

			@RequestParam(required = true, defaultValue = "") String billStatus,
			@RequestParam(defaultValue = "id,desc") String[] sort, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "50") int size) {
		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		Page<Bill> pBills = null;
		
		pBills = billService.findBills(billStatus,this.getCurrentUserId().getFacilityId(), pageable);

		if (ObjectUtils.isNotEmpty(billNumber)) {
			pBills = billService.findBillsByBillNumber(billNumber.trim(), billStatus, pageable);
		}

		if (ObjectUtils.isNotEmpty(admissionNumber)) {
			pBills = billService.findBillsByAdmissionNumber(admissionNumber.trim(), billStatus, pageable);
		}

		if (ObjectUtils.isNotEmpty(firstName) || ObjectUtils.isNotEmpty(lastName)) {
			pBills = billService.findBillsByPatientName(firstName.trim(), lastName.trim(), billStatus,this.getCurrentUserId().getFacilityId(), pageable);
		}

		if (ObjectUtils.isNotEmpty(insurance)) {
			pBills = billService.findBillsByInsurance(insurance, billStatus, pageable);
		}

		if (ObjectUtils.isNotEmpty(subscriber)) {
			pBills = billService.findBillsBySubscriber(subscriber, billStatus, pageable);
		}

		if (ObjectUtils.isNotEmpty(convention)) {
			pBills = billService.findBillsByConvention(convention, billStatus, pageable);
		}


		List<Bill> lBills = pBills.getContent();

		

		List<Map<String, Object>> bill = this.getMapFromBillList(lBills);
		response.put("items", bill);
		response.put("totalElements", pBills.getTotalElements());
		response.put("totalPages", pBills.getTotalPages());
		response.put("size", pBills.getSize());
		response.put("pageNumber", pBills.getNumber());
		response.put("numberOfElements", pBills.getNumberOfElements());
		response.put("first", pBills.isFirst());
		response.put("last", pBills.isLast());
		response.put("empty", pBills.isEmpty());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "liste paginee de toutes les facture du centre de sante vis a vis les practicians ")
	@GetMapping("/facilityInvoicesPractician")
	public ResponseEntity<Map<String,Object>>facilityInvoicesPractician(
			@RequestParam(required = false, defaultValue = "") String date,
			@RequestParam(required = true, defaultValue = "") String billStatus,
			@RequestParam(defaultValue = "id,desc") String[] sort,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "25") int size
			) throws ParseException{
		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
	    Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		Page<Bill> pBills = null;

		pBills = billService.facilityInvoicesByPractician(billStatus,this.getCurrentUserId().getFacilityId(), pageable);
		
		
		List<Bill> lBills = pBills.getContent();
		List<Map<String, Object>> bill = this.getMapFromFacilityInvoicesPracticianList(lBills);
		response.put("items", bill);
		response.put("totalElements", pBills.getTotalElements());
		response.put("totalPages", pBills.getTotalPages());
		response.put("size", pBills.getSize());
		response.put("pageNumber", pBills.getNumber());
		response.put("numberOfElements", pBills.getNumberOfElements());
		response.put("first", pBills.isFirst());
		response.put("last", pBills.isLast());
		response.put("empty", pBills.isEmpty());

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	

	protected List<Map<String, Object>> getMapFromFacilityInvoicesPracticianList(List<Bill> bills) {
		List<Map<String, Object>> billList = new ArrayList<>();
		bills.stream().forEach(bill -> {
			Map<String, Object> billMap = new HashMap<>();
			billMap.put("date", bill.getCreatedAt());
			billMap.put("invoiceNumber", bill.getBillNumber());
			billMap.put("invoiceNumber", bill.getBillNumber());
			//TODO : practician from examination, 
			if(bill.getAdmission().getPractician() != null) {
				billMap.put("userID", bill.getAdmission().getPractician().getUser().getId());
				billMap.put("practicianName", bill.getAdmission().getPractician().getUser().getFirstName() + " " + bill.getAdmission().getPractician().getUser().getLastName());
			} 
			billMap.put("patientNumber", bill.getAdmission().getPatient().getPatientExternalId() );
			billMap.put("patientNumber", bill.getAdmission().getPatient().getPatientExternalId() );
			billMap.put("patientAmount", bill.getPatientPart());
			billMap.put("totalAmount", bill.getTotalAmount());
			billMap.put("totalAmount", bill.getTotalAmount());
			billMap.put("billStatus", bill.getBillStatus());
			billList.add(billMap);
		});
		return billList;
	}

	@ApiOperation(value = "liste paginee de toutes les factures des assurances dans le système par Id de l'assurance")
	@GetMapping("/BillHasInsure_p_list")
	public ResponseEntity<Map<String, Object>> BillHasInsuredpaginatedList(
	    @RequestParam(defaultValue = "id,desc") String[] sort, @RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "50") int size) throws ParseException {
		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
//
		Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		Page<BillHasInsured> pBills;
//
		pBills = billHasInsuredService.findBillsHasInsured(pageable);
		
		List<BillHasInsured> lBills = pBills.getContent();
//
		List<Map<String, Object>> bill = this.getMapFromBillHasInsuredList(lBills);
		response.put("items", bill);
		response.put("totalElements", pBills.getTotalElements());
		response.put("totalPages", pBills.getTotalPages());
		response.put("size", pBills.getSize());
		response.put("pageNumber", pBills.getNumber());
		response.put("numberOfElements", pBills.getNumberOfElements());
		response.put("first", pBills.isFirst());
		response.put("last", pBills.isLast());
		response.put("empty", pBills.isEmpty());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	protected List<Map<String, Object>> getMapFromBillHasInsuredList(List<BillHasInsured> billHasInsureds) {
		List<Map<String, Object>> billList = new ArrayList<>();
		billHasInsureds.stream().forEach(billHasInsuredDto -> {
			Map<String, Object> billsMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(billHasInsuredDto.getCreatedBy()) ? new User()
					: userRepository.findById(billHasInsuredDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(billHasInsuredDto.getUpdatedBy()) ? new User()
					: userRepository.findById(billHasInsuredDto.getUpdatedBy()).orElse(null);
			billsMap.put("id", billHasInsuredDto.getId());
			billsMap.put("billNumber", billHasInsuredDto.getBill().getBillNumber());
			billsMap.put("billDate", billHasInsuredDto.getBill().getCreatedAt());
			billsMap.put("insurance", billHasInsuredDto.getInsurance().getName());
			billsMap.put("insuranceID", billHasInsuredDto.getInsurance().getId());
			billsMap.put("patientInsuranceNumber", billHasInsuredDto.getInsured().getCardNumber());
			billsMap.put("insurancePart", billHasInsuredDto.getInsuredPart());
			billsMap.put("patientPart", billHasInsuredDto.getBill().getPatientPart());
			billsMap.put("insuranceCoverage", billHasInsuredDto.getInsuredCoverage());
			billsMap.put("billTotalAmount", billHasInsuredDto.getBill().getTotalAmount());
			billsMap.put("admissionNumber", billHasInsuredDto.getAdmission().getAdmissionNumber());
			billsMap.put("patientNumber", billHasInsuredDto.getAdmission().getPatient().getPatientExternalId());
			billsMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			billsMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			billsMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			billsMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			billList.add(billsMap);
		});
		return billList;
	}

	protected List<Map<String, Object>> getMapFromBillList(List<Bill> bills) {
		List<Map<String, Object>> billList = new ArrayList<>();
		bills.forEach(billDto -> {
			Map<String, Object> billsMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(billDto.getCreatedBy()) ? new User()
					: userRepository.findById(billDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(billDto.getLastUpdatedBy()) ? new User()
					: userRepository.findById(billDto.getLastUpdatedBy()).orElse(null);
			billsMap.put("id", billDto.getId());
			billsMap.put("toFinalize", billDto.getToFinalize());
			billsMap.put("billNumber", billDto.getBillNumber());
			billsMap.put("billStatus", billDto.getBillStatus());
			billsMap.put("patient", billDto.getAdmission().getPatient());
			billsMap.put("billDate", billDto.getCreatedAt());
			billsMap.put("accountNumber", billDto.getAccountNumber());
		if(billDto.getAdmission().getPractician() != null) billsMap.put("practicianName", billDto.getAdmission().getPractician().getNom() + " " + billDto.getAdmission().getPractician().getPrenoms());

			billsMap.put("admission", billDto.getAdmission());
			billsMap.put("admissionActName", billDto.getAdmission().getAct().getName());

			if(ObjectUtils.isNotEmpty(billDto.getActs())) {

				List<Map<String, Object>> actList = new ArrayList<>();

				billDto.getActs().forEach(act -> {
					Map<String, Object> actsMap = new HashMap<>();
					actsMap.put("id", act.getAct().getId());
					actsMap.put("act", act.getAct().getName());
					actsMap.put("actCost", act.getActCost());
					actsMap.put("actGroup", act.getAct());
				if(act.getPractician() != null)	actsMap.put("practician", act.getPractician().getNom());
					actList.add(actsMap);
				});

			 billsMap.put("billActs", actList);
			}
			billsMap.put("billType", billDto.getBillType());
			billsMap.put("discountInCfa", billDto.getDiscountInCfa());
			billsMap.put("discountInPercentage", billDto.getDiscountInPercentage());
			if (billDto.getConvention() != null) billsMap.put("convention", billDto.getConvention());
			billsMap.put("insured", billDto.getInsured());

			if (billDto.getInsured() != null) {
				billsMap.put("insurance", billDto.getInsured().getInsurance());
				billsMap.put("subscriber", billDto.getInsured().getInsuranceSuscriber());
			} else {
				billsMap.put("insurance", null);
				billsMap.put("subscriber", null);
			}

			billsMap.put("partTakenCareOf", billDto.getPartTakenCareOf());
			billsMap.put("patientPart", billDto.getPatientPart());
			billsMap.put("patientType", billDto.getPatientType());
			billsMap.put("totalAmount", billDto.getTotalAmount());
			billsMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			billsMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			billsMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			billsMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			billList.add(billsMap);
		});
		return billList;
	}

	@ApiOperation(value = "Retourne les details d'une facture")
	@GetMapping("/detail/{billID}")
	public Object retrieve(@PathVariable Long billID) throws IOException, ResourceNotFoundByIdException {
		return billService.billRecord(billID);
	}

	@ApiOperation(value = "Supprimer une facture")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundByIdException {

		bill = billService.findBillById(id).orElseGet(() -> {
			return null;
		});

		if (bill != null) {
			if (bill.getBillStatus().compareToIgnoreCase("R") == 0) {
				billService.removeBillActs(bill);
				billService.deleteById(bill.getId());
			} else {
				throw new ResourceNotFoundByIdException(
						"Impossible de supprimer cette facture car elle a dejà été payée	");
			}
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Modifie une facture")
	@PutMapping("/update/{id}")
	public Bill updateBill(@RequestBody BillDTO billDto, @PathVariable Long id) throws ResourceNotFoundByIdException {

		admission = admissionService.retrieve(billDto.getAdmission()).orElse(null);
			if (admission == null) {
				throw new ResourceNotFoundByIdException("L'admission n'existe pas en base !");
			}

		if (billDto.getInsured() != null) {
			this.insured = insuredService.findInsuredById(billDto.getInsured()).orElse(null);
			if (this.insured == null) {
				throw new ResourceNotFoundByIdException("La convention n'existe pas en base !");
			}

		}

		Bill updateBill = billService.findBillById(id).orElse(null);
		if (updateBill == null) {
			 throw new ResourceNotFoundByIdException("Aucune facture trouvée pour l'identifiant");

		}else {
			
			if (updateBill.getBillStatus().compareToIgnoreCase("B") == 0) {
				throw new ResourceNotFoundByIdException("Modification impossible car la facture à été encaisser !");
			}else {
				billService.removeBillActs(updateBill);

				bill = new Bill();
				updateBill.setAdmission(admission);
				updateBill.setBillType(billDto.getBillType());
				if (this.convention != null)
					updateBill.setConvention(convention);
				updateBill.setDiscountInCfa(billDto.getDiscountInCfa());
				updateBill.setDiscountInPercentage(billDto.getDiscountInPercentage());
				if (this.insured != null)
					bill.setInsured(insured);
				updateBill.setPartTakenCareOf(billDto.getPartTakenCareOf());
				updateBill.setPatientPart(billDto.getPatientPart());
				updateBill.setPatientType(billDto.getPatientType());
				updateBill.setTotalAmount(billDto.getTotal());
				updateBill.setLastUpdatedAt(new Date());
				updateBill.setLastUpdatedBy(this.getCurrentUserId().getId());
				updateBill.setToFinalize(billDto.getToFinalize());

				bill = billService.saveBill(updateBill);
				
				billDto.getActs().forEach(admissionHasAct -> {
					Admission admission = admissionService.retrieve(admissionHasAct.getAdmission()).orElse(null);
					
					Act act = actService.findActById(admissionHasAct.getAct()).orElse(null);
					
				    User practician  = praticianService.findUserById( admissionHasAct.getPratician());
					
					if( admission != null && act != null && practician != null) {
					   int actCost = 0;
						if (this.convention == null) {
							actCost = this.getOneActCostWhithoutConvention(admissionHasAct.getAct());
						} else {
							actCost = this.getOneActWithConvention(admissionHasAct.getAct(), convention);
						}
				      admissionService.addActToAdmission(admissionHasAct, actCost, bill);
					};
				});
			}
		
		}
		
		return bill;
	}

	
	private Payment setPaymentData(Payment payment, PaymentType paymentType, PaymentDTO paymentDto, CashRegister cashRegister) {
		payment.setAmount(bill.getPatientPart());
		payment.setBill(bill);
		payment.setCashRegister(cashRegister);
		payment.setCreatedAt(new Date());
		payment.setCreatedBy(this.getCurrentUserId().getId());
		payment.setPaiementDate(new Date());
		payment.setPaymentType(paymentType);
		payment.setAmountReceived(paymentDto.getAmountReceived());
		payment.setAmountReturned(paymentDto.getAmountReturned());

		return payment;
	}
	@ApiOperation(value = "Encaisser une facture ")
	@PostMapping("/collect")
	public ResponseEntity<String> collectBill(@RequestBody PaymentDTO paymentDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		
		bill = billService.findBillById(paymentDto.getBill()).orElse(bill);
		if (bill == null) throw new ResourceNotFoundByIdException("La facture n'existe pas en base !");

		if (bill.getBillStatus().compareToIgnoreCase("B") == 0) throw new ResourceNotFoundByIdException("La facture dejà encaisser !");

		CashRegister cashRegister = this.cashRegisterService.findCashRegisterById(paymentDto.getCashRegister()).orElse(null);
		if (cashRegister == null) throw new ResourceNotFoundByIdException("La caisse n'existe pas en base !");

		PaymentType paymentType = this.paymentTypeService.findPaymentTypeById(paymentDto.getPaymentType()).orElse(null);
		if (paymentType == null) throw new ResourceNotFoundByIdException("Le type de payment n'existe pas en base !");
					
        Payment payment = new Payment();
		this.setPaymentData(payment, paymentType, paymentDto, cashRegister);
			
		payment = billService.savePayment(this.setPaymentData(payment, paymentType, paymentDto, cashRegister));
		CashRegisterManagement cashRegisterManagement  = cashRegisterManagementService.getCashierManagementByCashierAndStateOpened(this.getCurrentUserId().getId());
		if (cashRegisterManagement == null) {
		throw new ResourceNotFoundByIdException(
						"Vous n'etes pas autorisé a encaiser une facture !  \n veuillez démander à l'administrateur d'ouvrir une caisse à votre nom.");
			}else {
				CashRegisterManagementDto cashRegisterManagementDto = new CashRegisterManagementDto();	
				//Revoir ce code
				cashRegisterManagementDto.setOpeningBalance(cashRegisterManagement.getOpeningBalance());
				cashRegisterManagementDto.setCashRegisterBalance(cashRegisterManagement.getCashRegisterBalance() + payment.getAmount());
				cashRegisterManagementDto.setCashier(this.getCurrentUserId().getId());
				cashRegisterManagementDto.setCashRegister(payment.getCashRegister().getId());
				cashRegisterManagementService.updateCashRegisterManagement(cashRegisterManagement.getId(), cashRegisterManagementDto);
				// set the bill collected status
				bill.setBillStatus("C");
				billService.saveBill(bill);
				CashRegisterMovementDto cashRegisterMovementDto = new CashRegisterMovementDto();
				if (paymentDto.getAmountReturned() != 0 ) {
					CashRegisterMovementDto cashRegisterForAmountReturnedMovementDto = new CashRegisterMovementDto();
					cashRegisterForAmountReturnedMovementDto.setCashRegister(payment.getCashRegister().getId());
					cashRegisterForAmountReturnedMovementDto.setDebit(paymentDto.getAmountReturned());
					cashRegisterForAmountReturnedMovementDto.setDate(payment.getCreatedAt());
					cashRegisterForAmountReturnedMovementDto.setPrestationNumber(payment.getBill().getBillNumber());
					cashRegisterForAmountReturnedMovementDto.setLibelle("Monnaie Réglement  " + payment.getBill().getAdmission().getAct().getName() +" - " + payment.getBill().getAdmission().getSpeciality().getName());
					cashRegisterForAmountReturnedMovementDto.setUserId(this.getCurrentUserId().getId());
					cashRegisterMovementService.addNewMovement(cashRegisterForAmountReturnedMovementDto);
				}
				cashRegisterMovementDto.setCashRegister(payment.getCashRegister().getId());
				cashRegisterMovementDto.setCredit(payment.getAmount());
				cashRegisterMovementDto.setDate(payment.getCreatedAt());
				cashRegisterMovementDto.setPrestationNumber(payment.getBill().getBillNumber());
				cashRegisterMovementDto.setLibelle("Réglement  " + payment.getBill().getAdmission().getAct().getName() + " -" + payment.getBill().getAdmission().getSpeciality().getName());
				cashRegisterMovementDto.setUserId(this.getCurrentUserId().getId());
				cashRegisterMovementService.addNewMovement(cashRegisterMovementDto);
				
			}
				
		

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Liste des facture non encaisseés d'une admission ")
	@GetMapping("/non-collected/{admission_id}")
	public List<Map<String, Object>> fillBillByAdmission (@PathVariable("admission_id") Long admission_id) throws ResourceNotFoundByIdException {
		  admission =  this.admissionService.retrieve(admission_id).orElse(null);
			if (admission == null) {
				 throw new ResourceNotFoundByIdException(
							"L'admision n'existe pas en base !");
			}	  
	
		  
		  List<Bill>  bills = null;
		  
		  List<Map<String, Object>> bList = new ArrayList<>();
		  bills = this.billService.findNomCollectedBillByAdmission(admission_id);
		  bills.forEach(bill -> {	  
			  Map<String, Object> billsMap = new HashMap<>();
					
					Patient  patient= bill.getAdmission().getPatient();
					Insured insured = bill.getInsured();
					Convention convention = bill.getConvention();
					billsMap.put("billNumber", bill.getBillNumber());
					billsMap.put("patientExternalId", patient.getPatientExternalId());
					billsMap.put("patientFirstName",patient.getFirstName());
					billsMap.put("patientLastName", patient.getLastName());
					billsMap.put("patientContact", patient.getCellPhone1());
					billsMap.put("clientType", bill.getPatientType());
					billsMap.put("billDate", bill.getCreatedAt());
					if(insured != null ) {
						billsMap.put("insuranceSuscriber", insured.getInsuranceSuscriber().getName());
						billsMap.put("insurance", insured.getInsurance().getName());
						billsMap.put("insuranceCardNumber", insured.getCardNumber());
					} else {
						billsMap.put("insuranceSuscriber", null);
						billsMap.put("insurance", null);
						billsMap.put("insuranceCardNumber", null);
					}
					
					if(convention != null) {
						billsMap.put("convention", bill.getConvention().getName());
					} else {
						billsMap.put("convention", null);
					}
//					
					List<Map<String, Object>> actList = new ArrayList<>();

					bill.getActs().stream().forEach(act -> {
						Map<String, Object> actsMap = new HashMap<>();
						actsMap.put("name", act.getAct().getName());
						actsMap.put("actCost", act.getActCost());	
						actList.add(actsMap);
					});
					
					billsMap.put("acts", actList);
					billsMap.put("partTakenOfCare", (ObjectUtils.isNotEmpty(bill.getPartTakenCareOf()) ? bill.getPartTakenCareOf():0) );
					billsMap.put("patientPart", (ObjectUtils.isNotEmpty(bill.getPatientPart()) ? bill.getPatientPart():0));
					if (bill.getDiscountInCfa() != 0) billsMap.put("discount", (ObjectUtils.isNotEmpty(bill.getDiscountInCfa()) ? bill.getDiscountInCfa():0));
					if (bill.getDiscountInPercentage() != 0) billsMap.put("discount", (ObjectUtils.isNotEmpty(bill.getDiscountInCfa()) ? bill.getDiscountInPercentage():0 ));
					billsMap.put("totalAmount", bill.getTotalAmount());
					bList.add(billsMap);
				
		  });
		  return bList;
	}
	
	
	public String facilityLogoInBase64(UUID facilityID) throws IOException {	
			List<Files> fileList = fileRepository.findFIleByFacilityId(facilityID.toString());
			Files file = fileList.get(0);
			var imgFile = new FileSystemResource(Paths.get(file.getLocation()));
		    byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
		    String encodedString = Base64.getEncoder().encodeToString(bytes);
		    String basse64 = "data:"+file.getType()+";base64," + encodedString ;	
		    return basse64;
	}


}
