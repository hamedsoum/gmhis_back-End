package com.gmhis_backk.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.PatientPrescription;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface PatientPrescriptionRepository extends JpaRepository<PatientPrescription, UUID>{

	@Query(value="select p from PatientPrescription p where p.admission.patient.id = :patient")
	public Page<PatientPrescription> findAllPatientPrescriptions(@Param("patient") Long patient, Pageable pageable);

	@Query(value = "select p from PatientPrescription p where p.admission.patient.firstName like %:firstName%  and p.admission.patient.lastName like %:lastName% and (p.admission.patient.cellPhone1 like %:cellPhone% or p.admission.patient.cellPhone2 like %:cellPhone%)"
			+ " and p.admission.patient.patientExternalId like %:patientExternalId% and p.admission.patient.cnamNumber like %:cnamNumber% and p.admission.patient.idCardNumber like %:idCardNumber% and p.state=:state")
	public Page<PatientPrescription> findAllPrescriptions(@Param("firstName") String firstName, @Param("lastName") String lastName,
			@Param("patientExternalId") String patientExternalId, @Param("cellPhone") String cellPhone,
			@Param("cnamNumber") String cnamNumber, @Param("idCardNumber") String idCardNumber, @Param("state") String state, Pageable pageable);
	
	@Query(value = "select * from  patient_prescription p order by p.created_at desc LIMIT 0,1", nativeQuery = true)
	public  PatientPrescription findLastPatientPrescription();
}