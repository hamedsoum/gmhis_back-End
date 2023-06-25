package com.gmhis_backk.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Bill;
import com.gmhis_backk.domain.BillHasInsured;
import com.gmhis_backk.domain.Payment;


/**
 * 
 * @author Hamed SOUMAHORO
 *
 */
@Service
@Transactional
public interface BillService {

	public Bill saveBill(Bill b);
	
	public Optional<Bill> findBillById(Long id);
	
	public Bill findLastBill();
	
	public void deleteById(Long id);
	
	public void removeBillActs (Bill bill);

	public List<Bill> findBills();
	
	public List<Bill > findBillByAdmissionId(Long admissionId);
	
	public Page<Bill> findBills(String billStatus,String facilityId, Pageable pageable);
	
    public Page<Bill> findBillsByPatientName (String firstName, String lastName, String billStatus,String facilityId, Pageable pageable);
    
    public Page<Bill> findBillsByAdmissionNumber(String admissionNumber, String billStatus, Pageable pageable);
	
	public Page<Bill> findBillsByBillNumber(String billNumber, String billStatus, Pageable pageable);
	
	public Page<Bill> findBillsByPatientExternalId(String patientExternalId, String billStatus, Pageable pageable);
	
	public Page<Bill> findBillsByCellPhone(String cellPhone, String billStatus,  Pageable pageable);
	
	public Page<Bill> findBillsByCnamNumber(String cnamNumber, String billStatus, Pageable pageable);
	
	public Page<Bill> findBillsByIdCardNumber(String idCardNumber, String billStatus, Pageable pageable);
	
	public Page<Bill> findBillsByConvention (Long convention, String billStatus, Pageable pageable);
	
	public Page<Bill> findBillsByInsurance (Long insurance, String billStatus, Pageable pageable);
	
	public Page<Bill> findBillsBySubscriber (Long subscriber, String billStatus, Pageable pageable);
	
	public Page<Bill> findBillsByDate (Date fromDate, Date toDate, String billStatus, Pageable pageable);

	public Page<Bill> findByBillStatus(String status, Pageable pageable);
	
	public Payment savePayment(Payment p);
	
	public List<Bill> findNomCollectedBillByAdmission(Long admission_id);
	
	public Page<Bill> facilityInvoicesByPractician(String billStatus,String facilityId,Long PracticianID, Pageable pageable);
	
	public Page<Bill>facilityInvoicesByDate(String billStatus,String facilityId,String date, Pageable pageable) throws ParseException ;
	
	public Page<Bill> facilityInvoicesByPracticianAndDate(String billStatus,String facilityId,Long PracticianID,String date, Pageable pageable) throws ParseException;




	
}
