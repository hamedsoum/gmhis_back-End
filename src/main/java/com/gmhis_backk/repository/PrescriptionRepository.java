package com.gmhis_backk.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gmhis_backk.domain.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {

	@Query(value="select p from Prescription p where p.examination.admission.patient.id = :patient and p.examination.admission.id =:admissionID ORDER BY p.prescriptionDate DESC")
	public Page<Prescription> findAllPatientPrescriptions(@Param("patient") Long patient,@Param("admissionID") Long admissionID, Pageable pageable);
	
	@Query(value = "select * from prescription p order by p.prescription_date desc LIMIT 0,1", nativeQuery = true)
	public  Prescription findLastPrescription();
	
	@Query(value = "select Count(p) from Prescription p where p.examination.admission.id = :admissionID")
	public Long findPrescriptionNumber(@Param("admissionID") Long admissionID);
	
	@Query(value = "SELECT p FROM Prescription p WHERE p.prescriptionNumber =:query OR p.examination.admission.patient.cellPhone1 =:query OR (SELECT i.cardNumber FROM p.examination.admission.patient.insurances i WHERE i.society = 'CNAM') =:query ORDER BY p.prescriptionDate DESC ")
	public List<Prescription> retrievePrescription(@Param("query") String query);
}
