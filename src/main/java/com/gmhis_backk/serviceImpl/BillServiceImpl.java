package com.gmhis_backk.serviceImpl;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Bill;
import com.gmhis_backk.domain.BillHasInsured;
import com.gmhis_backk.domain.Files;
import com.gmhis_backk.domain.Payment;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.domain.UserHasCashRegister;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.BillRepository;
import com.gmhis_backk.repository.FileDbRepository;
import com.gmhis_backk.repository.PaymentRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.BillHasInsuredService;
import com.gmhis_backk.service.BillService;
import com.gmhis_backk.service.UserHasCashRegisterService;

import lombok.extern.log4j.Log4j2;



/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
@Log4j2
public class BillServiceImpl implements BillService{

	@Autowired
	private BillRepository repo;
	
	@Autowired
	PaymentRepository paymentRepo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserHasCashRegisterService userHasCashRegisterService;
	
	@Autowired
	private BillHasInsuredService billHasInsuredService;
	
	 @Autowired
     private FileDbRepository fileRepository;
	
	public String facilityLogoInBase64(UUID facilityID) throws IOException {	
		List<Files> fileList = fileRepository.findFIleByFacilityId(facilityID.toString());
		Files file = fileList.get(0);
		var imgFile = new FileSystemResource(Paths.get(file.getLocation()));
	    byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
	    String encodedString = Base64.getEncoder().encodeToString(bytes);
	    String basse64 = "data:"+file.getType()+";base64," + encodedString ;	
	    return basse64;
}
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	protected User getCurrentUser() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	@Override
	public Bill saveBill(Bill b) {
		return repo.save(b);
	}

	@Override
	public Optional<Bill> findBillById(Long id){
		return repo.findById(id);
		
	}
	
	public Object billRecord(Long billID) throws ResourceNotFoundByIdException {
		
		Bill bill = repo.findById(billID).orElse(null);
		if( bill == null ) throw new ResourceNotFoundByIdException("facture inexistant");

		Map<String, Object> billMap = new HashMap<>();

		UserHasCashRegister userHascashRegister = userHasCashRegisterService.findCashierByUser(this.getCurrentUserId().getId());
		
		List<BillHasInsured> insurances = billHasInsuredService.findBillsHasInsuredByBillID(bill.getId());
		
		if(insurances.size() != 0 ) {
			  List<Map<String, Object>> billInsurances = new ArrayList<>();

			  insurances.forEach(insurance -> {
				Map<String, Object> insuranceMap = new HashMap<>();
				insuranceMap.put("insuranceName", insurance.getInsurance().getName());
				insuranceMap.put("coverage", insurance.getInsuredCoverage());
				insuranceMap.put("insurancePart", insurance.getInsuredPart());
				billInsurances.add(insuranceMap);
			});
				billMap.put("insurances", billInsurances);
		}
		
		billMap.put("billID", bill.getId());
		billMap.put("billNumber", bill.getBillNumber());
		billMap.put("patientNumber", bill.getAdmission().getPatient().getPatientExternalId());
		billMap.put("patientFirstName", bill.getAdmission().getPatient().getFirstName());
		billMap.put("patientLastName", bill.getAdmission().getPatient().getLastName());
		billMap.put("patientAge", bill.getAdmission().getPatient().getAge());
		billMap.put("patientHeight", bill.getAdmission().getPatient().getHeight());
		billMap.put("patientWeight", bill.getAdmission().getPatient().getHeight());
		billMap.put("billDate", bill.getCreatedAt());
		billMap.put("accountNumber", bill.getAccountNumber());
		billMap.put("admissionNumber", bill.getAdmission().getAdmissionNumber());
		billMap.put("facilityName", bill.getAdmission().getFacility().getName());
		billMap.put("facilityLogo", bill.getAdmission().getFacility().getLogo());
		billMap.put("admissionStartDate", bill.getAdmission().getAdmissionStartDate());
		billMap.put("admissionEndDate", bill.getAdmission().getAdmissionEndDate());
		billMap.put("serviceName", bill.getAdmission().getSpeciality().getName());
		billMap.put("billStatus", bill.getBillStatus());
		billMap.put("billType", bill.getBillType());
		billMap.put("discountInCfa", bill.getDiscountInCfa());
		billMap.put("discountInPercentage", bill.getDiscountInPercentage());
		
		if (userHascashRegister != null) billMap.put("cashRegister", userHascashRegister.getCashRegister().getName());
		
		if (bill.getConvention() != null) billMap.put("convention", bill.getConvention().getName());

		billMap.put("partTakenCareOf", bill.getPartTakenCareOf());
		billMap.put("patientPart", bill.getPatientPart());
		billMap.put("patientType", bill.getPatientType());
		billMap.put("totalAmount", bill.getTotalAmount());
		return billMap;
	}

	@Override
	public Bill findLastBill() {
		return repo.findLastBill();
	}
	
	@Override
	public List<Bill> findBills(){
		return repo.findAll();
	}
	
	@Override
	public List<Bill > findBillByAdmissionId(Long admissionId){
		return repo.findBillByAdmissionId(admissionId);
	}

	@Override
	public Page<Bill> findBills(String billStatus,String facilityId, Pageable pageable){
		return repo.findBills(billStatus,facilityId, pageable);
	}
	
	@Override
	public Page<Bill> findAdmissionWithExamination(String billStatus,String facilityId, Pageable pageable){
		Page<Bill> bill = repo.findAdmissionWithExamination(facilityId,pageable);
		return bill;
	}
	
	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}
	
	@Override
	public void removeBillActs(Bill bill) {
		
		repo.removeBillActs(bill.getId());
	}
	
	@Override
    public Page<Bill> findBillsByPatientName (String firstName, String lastName, String billStatus,String facilityId, Pageable pageable){
		return repo.findBillsByPatientName(firstName, lastName, billStatus,facilityId, pageable);
	}
	
	@Override
	public Page<Bill> findBillsByAdmissionNumber(String admissionNumber, String billStatus, Pageable pageable){
		return repo.findBillsByAdmissionNumber(admissionNumber, billStatus, pageable);
	}
	
	@Override
	public Page<Bill> findBillsByBillNumber(String billNumber, String billStatus, Pageable pageable){
		return repo.findBillsByBillNumber(billNumber, billStatus, pageable);
	}
	
	@Override
	public Page<Bill> findBillsByPatientExternalId(String patientExternalId, String billStatus, Pageable pageable){
		return repo.findBillsByPatientExternalId(patientExternalId, billStatus, pageable);
	}
	
	@Override
	public Page<Bill> findBillsByCellPhone(String cellPhone, String billStatus,  Pageable pageable){
		return repo.findBillsByCellPhone(cellPhone, billStatus, pageable);
	}
	
	@Override
	public Page<Bill> findBillsByCnamNumber(String cnamNumber, String billStatus, Pageable pageable){
		return repo.findBillsByCnamNumber(cnamNumber, billStatus, pageable);
	}
	
	@Override
	public Page<Bill> findBillsByIdCardNumber(String idCardNumber, String billStatus, Pageable pageable){
		return repo.findBillsByIdCardNumber(idCardNumber, billStatus, pageable);
	}
	
	@Override
	public Page<Bill> findBillsByDate (Date fromDate, Date toDate, String billStatus, Pageable pageable){
		return repo.findBillByDate(fromDate, toDate, billStatus, pageable);
	}
	
	@Override
	public Page<Bill> findBillsByInsurance (Long insurance, String billStatus, Pageable pageable){
		return repo.findBillsByInsurance(insurance, billStatus, pageable);
	}
	
	@Override
	public Page<Bill> findBillsBySubscriber (Long subscriber, String billStatus, Pageable pageable){
		return repo.findBillsBySubscriber(subscriber, billStatus, pageable);
	}
	
	@Override
	public Page<Bill> findBillsByConvention (Long convention, String billStatus, Pageable pageable){
		return repo.findBillsByConvention(convention, billStatus, pageable);
	}
	

	@Override
	public Page<Bill> findByBillStatus(String status, Pageable pageable){
		return repo.findByBillStatus(status, pageable);
	}
	
	@Override
	public Payment savePayment(Payment p) {
		return paymentRepo.save(p);
	}
	
	@Override
	public List<Bill> findNomCollectedBillByAdmission(Long admission_id){
		return repo.findNomCollectedBillByAdmission(admission_id);
	}

	@Override
	public Page<Bill> facilityInvoicesByPractician(String billStatus, String facilityId,Pageable pageable) {
		

		return repo.findAdmissionWithExaminationByPractician(billStatus, UUID.fromString(facilityId),getCurrentUser().getId(), pageable);
	}

	@Override
	public Page<Bill> facilityInvoicesByDate(String billStatus,String facilityId, String date, Pageable pageable)
			throws ParseException {
		String[] dates = date.split(","); 
		return repo.findAdmissionWithExaminationByDate(billStatus,facilityId,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[0]+" 00:00:00"),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[1]+" 23:59:59"), pageable);
	}

	@Override
	public Page<Bill> facilityInvoicesByPracticianAndDate(String billStatus, String facilityId, Long PracticianID,
			String date, Pageable pageable) throws ParseException {
		String[] dates = date.split(","); 
		return repo.findByBillhaInsuredInsuranceiDAndDate(billStatus,facilityId,PracticianID,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[0]+" 00:00:00"),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[1]+" 23:59:59"), pageable);
	}
}
