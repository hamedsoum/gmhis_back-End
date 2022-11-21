package com.gmhis_backk.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gmhis_backk.domain.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {

	@Query(value="select p from Prescription p where p.examination.admission.patient.id = :patient ORDER BY p.prescriptionDate DESC")
	public Page<Prescription> findAllPatientPrescriptions(@Param("patient") Long patient, Pageable pageable);
	
	@Query(value = "select * from prescription p order by p.prescription_date desc LIMIT 0,1", nativeQuery = true)
	public  Prescription findLastPrescription();
}
