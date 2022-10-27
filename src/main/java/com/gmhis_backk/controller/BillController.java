package com.gmhis_backk.controller;

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
import com.gmhis_backk.domain.Admission;
import com.gmhis_backk.domain.Bill;
import com.gmhis_backk.domain.CashRegister;
import com.gmhis_backk.domain.Convention;
import com.gmhis_backk.domain.ConventionHasAct;
import com.gmhis_backk.domain.ConventionHasActCode;
import com.gmhis_backk.domain.Insured;
import com.gmhis_backk.domain.Patient;
import com.gmhis_backk.domain.Payment;
import com.gmhis_backk.domain.PaymentType;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.domain.UserHasCashRegister;
import com.gmhis_backk.dto.AdmisionHasActDTO;
import com.gmhis_backk.dto.BillDTO;
import com.gmhis_backk.dto.PaymentDTO;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ActCodeService;
import com.gmhis_backk.service.ActService;
import com.gmhis_backk.service.AdmissionService;
import com.gmhis_backk.service.BillService;
import com.gmhis_backk.service.CashRegisterService;
import com.gmhis_backk.service.ConventionService;
import com.gmhis_backk.service.InsuredService;
import com.gmhis_backk.service.PaymentTypeService;
import com.gmhis_backk.service.UserHasCashRegisterService;
import com.gmhis_backk.service.UserService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@RestController
@RequestMapping("/bill")
//public class BillController extends BaseController {

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
	private UserHasCashRegisterService userHasCashRegisterService;

	@Autowired
	private PaymentTypeService paymentTypeService;
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	private UserService praticianService;

	private Admission admission = null;
	private Convention convention = null;
	private Bill bill = null;
	private Insured insured = null;
	private int actCosts = 0;
	private int patientPart = 0;
	private int partTakenCareOf = 0;
	private int discount = 0;
	private int totalAmount = 0;

	@ApiOperation(value = "Ajouter une facture ")
	@PostMapping("/add")
	public ResponseEntity<Bill> addBill(@RequestBody BillDTO billDto) throws ResourceNotFoundByIdException {

		admission = admissionService.findAdmissionById(billDto.getAdmission()).orElse(null);
			if (admission == null) {
				throw new ResourceNotFoundByIdException("L'admission n'existe pas en base !");
			}	
	

		if (ObjectUtils.isNotEmpty(billDto.getConvention())) {
			Convention convention = conventionService.findConventionById(billDto.getConvention()).orElseGet(null);
			if (convention == null) {
				throw new ResourceNotFoundByIdException("la convention n'existe pas en base !");
			}
		
			int costWithConvention = this.getActWithConvention(billDto.getActs(), convention);
			this.patientPart = costWithConvention;

		} else {

			int costWhithoutConvention = this.getActCostWhithoutConvention(billDto.getActs());
			this.patientPart = costWhithoutConvention;

		}

		if (ObjectUtils.isNotEmpty(billDto.getInsured())) {
			this.insured = insuredService.findInsuredById(billDto.getInsured()).orElse(null);
					
		if (this.insured == null) {
			throw new ResourceNotFoundByIdException("L'assuré n'existe pas en base !");
		}

			int cost = this.getActCostWhithoutConvention(billDto.getActs());
			this.patientPart = this.getPatientPart(cost, insured);
			this.partTakenCareOf = this.getPartTakenCareOf(cost, insured);

		}

		if (billDto.getDiscountInCfa() != 0) {
			this.discount = billDto.getDiscountInCfa();
			this.patientPart = this.patientPart - this.discount;
		}

		if (billDto.getDiscountInPercentage() != 0) {
			int cost = this.getActCostWhithoutConvention(billDto.getActs());
			this.discount = cost * billDto.getDiscountInPercentage() / 100;
			this.patientPart = this.patientPart - this.discount;
		}

		this.totalAmount = this.patientPart + this.partTakenCareOf + this.discount;

		String billNumber = this.getBillNumber();
		bill = new Bill();
//		bill.setAccountNumber(billDto.getAccountNumber());
		bill.setAdmission(admission);
		bill.setBillNumber(billNumber);
		bill.setBillStatus("R");
		bill.setBillType(billDto.getBillType());
		if (this.convention != null)
			bill.setConvention(convention);
		bill.setDiscountInCfa(billDto.getDiscountInCfa());
		bill.setDiscountInPercentage(billDto.getDiscountInPercentage());
		if (this.insured != null)
			bill.setInsured(insured);
		bill.setPartTakenCareOf(this.partTakenCareOf);
		bill.setPatientPart(this.patientPart);
		bill.setPatientType(billDto.getPatientType());
		bill.setTotalAmount(this.totalAmount);
		bill.setCreatedAt(new Date());
		bill.setCreatedBy(this.getCurrentUserId().getId());
		bill = billService.saveBill(bill);
        
		billDto.getActs().forEach(admissionHasAct -> {
			System.out.print(admissionHasAct.getPratician());

			Admission admission = admissionService.findAdmissionById(admissionHasAct.getAdmission()).orElse(null);
			Act act = actService.findActById(admissionHasAct.getAct()).orElse(null);
			User practician  = userRepository.findById( admissionHasAct.getPratician()).orElse(null);
			int actCost = 0;
			if( admission != null && act != null && practician != null) {
				if (ObjectUtils.isEmpty(this.convention)) {
					actCost = this.getOneActCostWhithoutConvention(admissionHasAct.getAct());
				} else {
					actCost = this.getOneActWithConvention(admissionHasAct.getAct(), convention);
				}
			};
			admissionService.addActToAdmission(admissionHasAct, actCost, bill);
		});
		admissionService.setAdmissionStatusToBilled(admission.getId());
		return new ResponseEntity<Bill>(bill,HttpStatus.OK);

	}
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@ApiOperation(value = "Calculer le cout de la facture ")
	@PostMapping("/get_cost")
	public Object getBillCost(@RequestBody BillDTO billDto) throws ResourceNotFoundByIdException {

		if (billDto.getConvention() != null) {
			this.convention = conventionService.findConventionById(billDto.getConvention()).orElse(null);					
			if (this.convention == null) {
				throw new ResourceNotFoundByIdException("La convention n'existe pas en base !");
			}

			int costWithConvention = this.getActWithConvention(billDto.getActs(), convention);
			this.patientPart = costWithConvention;

		} else {

			int costWhithoutConvention = this.getActCostWhithoutConvention(billDto.getActs());
			this.patientPart = costWhithoutConvention;

		}

		if (billDto.getInsured() != null) {
			this.insured = insuredService.findInsuredById(billDto.getInsured()).orElse(null);
			if (this.insured == null) {
				throw new ResourceNotFoundByIdException("La convention n'existe pas en base !");
			}	
		
			int cost = this.getActCostWhithoutConvention(billDto.getActs());
			this.patientPart = this.getPatientPart(cost, insured);
			this.partTakenCareOf = this.getPartTakenCareOf(cost, insured);

		}

		if (billDto.getDiscountInCfa() != 0) {
			this.discount = billDto.getDiscountInCfa();
			this.patientPart = this.patientPart - this.discount;
		}

		if (billDto.getDiscountInPercentage() != 0) {
			int cost = this.getActCostWhithoutConvention(billDto.getActs());
			this.discount = cost * billDto.getDiscountInPercentage() / 100;
			this.patientPart = this.patientPart - this.discount;
		}

		this.totalAmount = this.patientPart + this.partTakenCareOf - this.discount;

		Map<String, Object> billCostMap = new HashMap<>();
		billCostMap.put("patientPart", this.patientPart);
		billCostMap.put("partTakenCareOf", this.partTakenCareOf);
		billCostMap.put("totalAmount", this.totalAmount);

		return billCostMap;
	}

	/**
	 * get the cost of acts if no convention
	 * 
	 * @param acts
	 * @return
	 */
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
	
	/**
	 * get the cost of one act if no convention
	 * 
	 * @param act
	 * @return
	 */
	public int getOneActCostWhithoutConvention(Long id) {

		int cost = 0;
		Act act = null;
		act = this.actService.findActById(id).orElse(null);

			if (act != null) {
				cost = act.getCoefficient() * act.getActCode().getValue();
			}
		
		return cost;
	}

	/**
	 * get the cost of acts if convention
	 * 
	 * @param admissionActs
	 * @param convention
	 * @return
	 */
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
			ConventionHasActCode conventionActCode = this.actCodeService.findActCodeByConventionAndAct(convention,
					act.getActCode());

			if (conventionAct != null) {
				int ac = conventionAct.getCoefficient() * conventionAct.getAct().getActCode().getValue();
				this.actCosts = this.actCosts + ac;
			} else if (conventionActCode != null) {
				int ac = act.getCoefficient() * conventionActCode.getValue();
				this.actCosts = this.actCosts + ac;
			} else {

				int ac = act.getCoefficient() * act.getActCode().getValue();
				this.actCosts = this.actCosts + ac;
			}
		});

		return this.actCosts;
	}

	/**
	 * 
	 * @param admissionActs
	 * @param convention
	 * @return
	 */
	public int getOneActWithConvention(Long id, Convention convention) {
		int cost = 0;
		Act act = null;
		act = this.actService.findActById(id).orElse(null);

			if (act != null) {
				cost = act.getCoefficient() * act.getActCode().getValue();
			}
		
			ConventionHasAct conventionAct = this.actService.findActByConventionAndAct(convention, act);
			ConventionHasActCode conventionActCode = this.actCodeService.findActCodeByConventionAndAct(convention,
					act.getActCode());

			if (conventionAct != null) {
				cost = conventionAct.getCoefficient() * conventionAct.getAct().getActCode().getValue();
				
			} else if (conventionActCode != null) {
               cost = act.getCoefficient() * conventionActCode.getValue();
			} else {
				cost = act.getCoefficient() * act.getActCode().getValue();
			}
		

		return cost;
	}

	/**
	 * get the patient part of the bill
	 * 
	 * @param actCosts
	 * @param insured
	 * @return partTakenCareOf int
	 */
	public int getPartTakenCareOf(int actCosts, Insured insured) {

		int partTakenCareOf = (actCosts * insured.getCoverage()) / 100;

		return partTakenCareOf;
	}

	/**
	 * get the part of the bill taken of care par the insurance
	 * 
	 * @param actCosts
	 * @param insured
	 * @return patientPart int
	 */
	public int getPatientPart(int actCosts, Insured insured) {

		int patientPart = (actCosts * (100 - insured.getCoverage())) / 100;

		return patientPart;
	}

	/*
	 * generate a bill number
	 */
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

		if (lBillYearandMonth.equals(year + month)) {
			number = Integer.parseInt(lBillNb) + 1;
		} else {
			number = number + 1;
		}

		return "FA" + year + month + String.format("%04d", number);

	}
	
	@ApiOperation(value = " retour le out d'un act")
	@GetMapping("/get-act-cost")
	public int getActCost(
			@RequestParam(required = true) Long actId,
			@RequestParam(required = false ) Long conventionId ) {
		
		int cost = 0;
		
		if(ObjectUtils.isEmpty(conventionId)) {
		   cost = this.getOneActCostWhithoutConvention(actId);
		} else {
			Convention convention= this.conventionService.findConventionById(conventionId).orElse(null);
			if(convention != null)  cost = this.getOneActWithConvention(actId, convention) ;
		}
		
		return cost;
	}

	@ApiOperation(value = "Lister la liste paginee de toutes les admission dans le système par status (R: register, C: collected)")
	@GetMapping("/p_list")
	public ResponseEntity<Map<String, Object>> paginatedList(
			@RequestParam(required = false, defaultValue = "") String billNumber,
			@RequestParam(required = false, defaultValue = "") String admissionNumber,
			@RequestParam(required = false, defaultValue = "") String firstName,
			@RequestParam(required = false, defaultValue = "") String lastName,
			@RequestParam(required = false, defaultValue = "") String patientExternalId,
			@RequestParam(required = false, defaultValue = "") String cellPhone,
			@RequestParam(required = false, defaultValue = "") String cnamNumber,
			@RequestParam(required = false, defaultValue = "") String idCardNumber,
			@RequestParam(required = false) Long convention,
			@RequestParam(required = false) Long insurance,
			@RequestParam(required = false) Long subscriber,
			@RequestParam(required = false, defaultValue = "") String fromDate,
			@RequestParam(required = false, defaultValue = "") String toDate,
			@RequestParam(required = true, defaultValue = "") String billStatus,
			@RequestParam(defaultValue = "id,desc") String[] sort, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Map<String, Object> response = new HashMap<>();
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		Page<Bill> pBills = null;

		Calendar cDate1 = Calendar.getInstance();
		Calendar cDate2 = Calendar.getInstance();

		if (ObjectUtils.isEmpty(fromDate)) {
			cDate1.set(1970, 0, 1);
		} else {
			String[] fd = fromDate.split("/");
			cDate1.set(Integer.parseInt(fd[2]), Integer.parseInt(fd[1]) - 1, Integer.parseInt(fd[0]), 0, 0);
		}

		if (ObjectUtils.isNotEmpty(toDate)) {
			String[] td = toDate.split("/");
			cDate2.set(Integer.parseInt(td[2]), Integer.parseInt(td[1]) - 1, Integer.parseInt(td[0]), 23, 59);
		}

		Date date1 = cDate1.getTime();
		Date date2 = cDate2.getTime();

		pBills = billService.findBills(billStatus, pageable);

		if (ObjectUtils.isNotEmpty(billNumber)) {
			pBills = billService.findBillsByBillNumber(billNumber, billStatus, pageable);
		}

		if (ObjectUtils.isNotEmpty(admissionNumber)) {
			pBills = billService.findBillsByAdmissionNumber(admissionNumber, billStatus, pageable);
		}

		if (ObjectUtils.isNotEmpty(firstName) || ObjectUtils.isNotEmpty(lastName)) {
			pBills = billService.findBillsByPatientName(firstName, lastName, billStatus, pageable);
		}

		if (ObjectUtils.isNotEmpty(patientExternalId)) {
			pBills = billService.findBillsByPatientExternalId(patientExternalId, billStatus, pageable);
		}

		if (ObjectUtils.isNotEmpty(cellPhone)) {
			pBills = billService.findBillsByCellPhone(cellPhone, billStatus, pageable);
		}

		if (ObjectUtils.isNotEmpty(cnamNumber)) {
			pBills = billService.findBillsByCnamNumber(cnamNumber, billStatus, pageable);
		}

		if (ObjectUtils.isNotEmpty(idCardNumber)) {
			pBills = billService.findBillsByIdCardNumber(idCardNumber, billStatus, pageable);
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

		if (ObjectUtils.isNotEmpty(fromDate) || ObjectUtils.isNotEmpty(toDate)) {
			pBills = billService.findBillsByDate(date1, date2, billStatus, pageable);
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

	protected List<Map<String, Object>> getMapFromBillList(List<Bill> bills) {
		List<Map<String, Object>> billList = new ArrayList<>();
		bills.stream().forEach(billDto -> {
			Map<String, Object> billsMap = new HashMap<>();
			User createdBy = ObjectUtils.isEmpty(billDto.getCreatedBy()) ? new User()
					: userRepository.findById(billDto.getCreatedBy()).orElse(null);
			User updatedBy = ObjectUtils.isEmpty(billDto.getLastUpdatedBy()) ? new User()
					: userRepository.findById(billDto.getLastUpdatedBy()).orElse(null);
			billsMap.put("id", billDto.getId());
			billsMap.put("billNumber", billDto.getBillNumber());
			billsMap.put("billStatus", billDto.getBillStatus());
			billsMap.put("patient", billDto.getAdmission().getPatient());
			billsMap.put("billDate", billDto.getCreatedAt());
			billsMap.put("accountNumber", billDto.getAccountNumber());
			billsMap.put("admission", billDto.getAdmission());
			billsMap.put("admissionAct", billDto.getAdmission().getAct());
			
			if(ObjectUtils.isNotEmpty(billDto.getActs())) {
				
				List<Map<String, Object>> actList = new ArrayList<>();

				billDto.getActs().stream().forEach(act -> {
					Map<String, Object> actsMap = new HashMap<>();
					actsMap.put("act", act.getAct());
					actsMap.put("practician", act.getPractician());
					actsMap.put("actCost", act.getActCost());	
					actList.add(actsMap);
				});
					
			 billsMap.put("billActs", actList); 	
			 
			} else {
				billsMap.put("billActs", null);
			}
			
			billsMap.put("billStatus", billDto.getBillStatus());
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
			billsMap.put("createdAt", billDto.getCreatedAt());
			billsMap.put("updatedAt", billDto.getLastUpdatedAt());
			billsMap.put("createdByFirstName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getFirstName());
			billsMap.put("createdByLastName", ObjectUtils.isEmpty(createdBy) ? "--" : createdBy.getLastName());
			billsMap.put("UpdatedByFirstName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getFirstName());
			billsMap.put("UpdatedByLastName", ObjectUtils.isEmpty(updatedBy) ? "--" : updatedBy.getLastName());
			billList.add(billsMap);
		});
		return billList;
	}

	@ApiOperation(value = "Retourne les details d'une facture specifique")
	@GetMapping("/detail/{id}")
	public Object detail(@PathVariable Long id) {
		bill = new Bill();

		bill = billService.findBillById(id).orElseGet(() -> {
			return null;
		});

		UserHasCashRegister userHascashRegister = userHasCashRegisterService.findCashierByUser(this.getCurrentUserId().getId());

		Map<String, Object> billMap = new HashMap<>();

		billMap.put("id", bill.getId());
		billMap.put("billNumber", bill.getBillNumber());
		billMap.put("patientId", bill.getAdmission().getPatient().getId());
		billMap.put("patientExternalId", bill.getAdmission().getPatient().getPatientExternalId());
		billMap.put("patientFirstName", bill.getAdmission().getPatient().getFirstName());
		billMap.put("patientLastName", bill.getAdmission().getPatient().getLastName());
		billMap.put("patientMaidenName", bill.getAdmission().getPatient().getMaidenName());
		billMap.put("billDate", bill.getCreatedAt());
		billMap.put("accountNumber", bill.getAccountNumber());
		billMap.put("admissionNumber", bill.getAdmission().getAdmissionNumber());
		billMap.put("admissionId", bill.getAdmission().getId());
		billMap.put("serviceId", bill.getAdmission().getService().getId());
		billMap.put("serviceName", bill.getAdmission().getService().getName());
		billMap.put("billStatus", bill.getBillStatus());
		billMap.put("billType", bill.getBillType());
		billMap.put("discountInCfa", bill.getDiscountInCfa());
		billMap.put("discountInPercentage", bill.getDiscountInPercentage());
		if (userHascashRegister != null) {
			billMap.put("cashRegister", userHascashRegister.getCashRegister().getId());
		} else {
			billMap.put("cashRegister", null);
		}

		if (bill.getConvention() != null) {
			billMap.put("conventionId", bill.getConvention().getId());
			billMap.put("convention", bill.getConvention().getName());

		} else {
			billMap.put("conventionId", null);
			billMap.put("convention", null);
		}

		if (bill.getInsured() != null) {
			billMap.put("coverage", bill.getInsured().getCoverage());
			billMap.put("insuranceName", bill.getInsured().getInsurance().getName());
			billMap.put("insuranceId", bill.getInsured().getInsurance().getId());
			billMap.put("subscriberId", bill.getInsured().getInsuranceSuscriber().getId());
			billMap.put("subscriberName", bill.getInsured().getInsuranceSuscriber().getName());
		} else {
			billMap.put("coverage", 0);
			billMap.put("insuranceName", null);
			billMap.put("insuranceId", null);
			billMap.put("subscriberId", null);
			billMap.put("subscriberName", null);
		}

		billMap.put("partTakenCareOf", bill.getPartTakenCareOf());
		billMap.put("patientPart", bill.getPatientPart());
		billMap.put("patientType", bill.getPatientType());
		billMap.put("totalAmount", bill.getTotalAmount());
		return billMap;
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

	@ApiOperation(value = "Modifie une facture dans le systeme")
	@PutMapping("/update/{id}")
	public Bill updateBill(@RequestBody BillDTO billDto, @PathVariable Long id) throws ResourceNotFoundByIdException {

		admission = admissionService.findAdmissionById(billDto.getAdmission()).orElse(null);
			if (admission == null) {
				throw new ResourceNotFoundByIdException("L'admission n'existe pas en base !");
			}	
	

		if (billDto.getConvention() != null) {
			this.convention = conventionService.findConventionById(billDto.getConvention()).orElse(null);
					
			if (this.convention == null) {
				throw new ResourceNotFoundByIdException("La convention n'existe pas en base !");
			}

			int costWithConvention = this.getActWithConvention(billDto.getActs(), convention);
			this.patientPart = costWithConvention;

		} else {

			int costWhithoutConvention = this.getActCostWhithoutConvention(billDto.getActs());
			this.patientPart = costWhithoutConvention;

		}

		if (billDto.getInsured() != null) {
			this.insured = insuredService.findInsuredById(billDto.getInsured()).orElse(null);
			if (this.insured == null) {
				throw new ResourceNotFoundByIdException("La convention n'existe pas en base !");
			}		
					
			int cost = this.getActCostWhithoutConvention(billDto.getActs());
			this.patientPart = this.getPatientPart(cost, insured);
			this.partTakenCareOf = this.getPartTakenCareOf(cost, insured);

		}

		if (billDto.getDiscountInCfa() != 0) {
			this.discount = billDto.getDiscountInCfa();
			this.patientPart = this.patientPart - this.discount;
		}

		if (billDto.getDiscountInPercentage() != 0) {
			int cost = this.getActCostWhithoutConvention(billDto.getActs());
			this.discount = cost * billDto.getDiscountInPercentage() / 100;
			this.patientPart = this.patientPart - this.discount;
		}

		this.totalAmount = this.patientPart + this.partTakenCareOf + this.discount;
		
		Bill updateBill = billService.findBillById(id).orElse(null);
		if (updateBill == null) {
			 throw new ResourceNotFoundByIdException("Aucune facture trouvée pour l'identifiant");

		}else {
			
			if (updateBill.getBillStatus().compareToIgnoreCase("B") == 0) {
				throw new ResourceNotFoundByIdException("Modification impossible car la facture à été encaisser !");
			}else {
				billService.removeBillActs(updateBill);

				bill = new Bill();
//				updateBill.setAccountNumber(billDto.getAccountNumber());
				updateBill.setAdmission(admission);
//				updateBill.setBillStatus(billDto.getBillStatus());
				updateBill.setBillType(billDto.getBillType());
				if (this.convention != null)
					updateBill.setConvention(convention);
				updateBill.setDiscountInCfa(billDto.getDiscountInCfa());
				updateBill.setDiscountInPercentage(billDto.getDiscountInPercentage());
				if (this.insured != null)
					bill.setInsured(insured);
				updateBill.setPartTakenCareOf(this.partTakenCareOf);
				updateBill.setPatientPart(this.patientPart);
				updateBill.setPatientType(billDto.getPatientType());
				updateBill.setTotalAmount(this.totalAmount);
				updateBill.setLastUpdatedAt(new Date());
				updateBill.setLastUpdatedBy(this.getCurrentUserId().getId());
				bill = billService.saveBill(updateBill);
				
				billDto.getActs().forEach(admissionHasAct -> {
					Admission admission = admissionService.findAdmissionById(admissionHasAct.getAdmission()).orElse(null);
					
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

	@ApiOperation(value = "Encaisser une facture ")
	@PostMapping("/collect")
	public ResponseEntity<String> collectBill(@RequestBody PaymentDTO paymentDto) throws ResourceNotFoundByIdException {

		bill = billService.findBillById(paymentDto.getBill()).orElse(bill);
		
		if (bill == null) {
			throw new ResourceNotFoundByIdException("La facture n'existe pas en base !");
		}
	

		if (bill.getBillStatus().compareToIgnoreCase("B") == 0) {
			throw new ResourceNotFoundByIdException("La facture dejà encaisser !");
		}

			CashRegister cashRegister = this.cashRegisterService.findCashRegisterById(paymentDto.getCashRegister()).orElse(null);
				
			if (cashRegister == null) {
				throw new ResourceNotFoundByIdException("La caisse n'existe pas en base !");
			}
			

			PaymentType paymentType = this.paymentTypeService.findPaymentTypeById(paymentDto.getPaymentType()).orElse(null);
					
			if (paymentType == null) {
				throw new ResourceNotFoundByIdException("Le type de payment n'existe pas en base !");
			}
					

			Payment payment = new Payment();
			payment.setAmount(bill.getPatientPart());
			payment.setBill(bill);
			payment.setCashRegister(cashRegister);
			payment.setCreatedAt(new Date());
			payment.setCreatedBy(this.getCurrentUserId().getId());
			payment.setPaiementDate(new Date());
			payment.setPaymentType(paymentType);
			payment = billService.savePayment(payment);

			// set the bill collected status
			bill.setBillStatus("C");
			billService.saveBill(bill);

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Liste des facture non encaisseés d'une admission ")
	@GetMapping("/non-collected/{admission_id}")
	public List<Map<String, Object>> fillBillByAdmission (@PathVariable("admission_id") Long admission_id) throws ResourceNotFoundByIdException {
		  admission =  this.admissionService.findAdmissionById(admission_id).orElse(null);
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
					billsMap.put("patientMaidenName", patient.getMaidenName());
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
						actsMap.put("practicianFirstName", act.getPractician().getFirstName());
						actsMap.put("practicianLastName", act.getPractician().getLastName());
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

}
