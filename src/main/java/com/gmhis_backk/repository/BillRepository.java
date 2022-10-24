package com.gmhis_backk.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Bill;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Long>{
	
	@Query(value = "select * from  bill d order by d.created_at desc LIMIT 0,1", nativeQuery = true)
	public Bill findLastBill();
	
	@Query(value = "SELECT b FROM Bill b WHERE b.admission.id = :admissionId")
	public List<Bill > findBillByAdmissionId(Long admissionId);
	
	@Query(value = "SELECT b FROM Bill b WHERE b.admission.patient.firstName like %:firstName% and  (b.admission.patient.lastName like %:lastName% or b.admission.patient.maidenName like %:lastName% ) and b.billStatus= :billStatus")
	public Page<Bill> findBillsByPatientName(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("billStatus") String billStatus, Pageable pageable);
	
	@Query(value = "SELECT b FROM Bill b WHERE b.admission.admissionNumber like %:admissionNumber% and b.billStatus= :billStatus")
    public Page<Bill> findBillsByAdmissionNumber(@Param("admissionNumber") String admissionNumber, @Param("billStatus") String billStatus, Pageable pageable);
	
	@Query(value = "SELECT b FROM Bill b WHERE b.billNumber like %:billNumber% and b.billStatus = :billStatus")
    public Page<Bill> findBillsByBillNumber(@Param("billNumber") String billNumber, @Param("billStatus") String billStatus, Pageable pageable);
	
	@Query(value = "SELECT b FROM Bill b WHERE b.admission.patient.patientExternalId like %:patientExternalId% and b.billStatus= :billStatus")
	public Page<Bill> findBillsByPatientExternalId(@Param("patientExternalId") String patientExternalId, @Param("billStatus") String billStatus, Pageable pageable);
	
	@Query(value = "SELECT b FROM Bill b WHERE b.admission.patient.cellPhone1 like %:cellPhone% or b.admission.patient.cellPhone2 like %:cellPhone% and b.billStatus= :billStatus")
	public Page<Bill> findBillsByCellPhone(@Param("cellPhone") String cellPhone, @Param("billStatus") String billStatus,  Pageable pageable);
	
	@Query(value = "SELECT b FROM Bill b WHERE b.admission.patient.cnamNumber like %:cnamNumber% and b.billStatus= :billStatus")
	public Page<Bill> findBillsByCnamNumber(@Param("cnamNumber")String cnamNumber, @Param("billStatus") String billStatus, Pageable pageable);
	
	@Query(value = "SELECT b FROM Bill b WHERE b.admission.patient.idCardNumber like %:idCardNumber% and b.billStatus= :billStatus")
	public Page<Bill> findBillsByIdCardNumber(@Param("idCardNumber") String idCardNumber, @Param("billStatus") String billStatus, Pageable pageable);
		
	@Query(value = "SELECT b FROM Bill b WHERE b.insured.insurance.id = :insurance and b.billStatus= :billStatus")
	public Page<Bill> findBillsByInsurance(@Param("insurance") Long insurance, @Param("billStatus") String billStatus, Pageable pageable);
	
	@Query(value = "SELECT b FROM Bill b WHERE b.insured.insuranceSuscriber.id = :subscriber and b.billStatus= :billStatus")
	public Page<Bill> findBillsBySubscriber(@Param("subscriber") Long subscriber, @Param("billStatus") String billStatus, Pageable pageable);

	@Query(value = "SELECT b FROM Bill b WHERE b.convention.id = :convention and b.billStatus= :billStatus")
	public Page<Bill> findBillsByConvention(@Param("convention") Long convention, @Param("billStatus") String billStatus, Pageable pageable);

	
	@Query(value = "SELECT b FROM Bill b WHERE b.createdAt between :fromDate and :toDate and b.billStatus= :billStatus")
	public Page<Bill> findBillByDate (@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("billStatus") String billStatus, Pageable pageable);
	
	@Query(value = "SELECT b FROM Bill b WHERE b.billStatus='N'")
	public Page<Bill> findByBillStatus(String status, Pageable pageable);
	
	@Query(value = "SELECT b FROM Bill b WHERE b.billStatus= :billStatus")
	public Page<Bill> findBills(@Param("billStatus") String billStatus, Pageable pageable);
	
	
	@Modifying
	@Transactional
	@Query(value = "delete from admission_has_act where bill_id = :bill_id", nativeQuery = true)
	public void removeBillActs(@Param("bill_id") Long billId);
	
	@Query(value = "SELECT b FROM Bill b WHERE b.billStatus= 'R' and b.admission.id = :admission_id")
	public List<Bill> findNomCollectedBillByAdmission(@Param("admission_id") Long admission_id);
	
	
}
