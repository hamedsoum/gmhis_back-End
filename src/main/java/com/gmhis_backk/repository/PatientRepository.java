package com.gmhis_backk.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Patient;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	@Query(value="select p from Patient p where p.correspondant like %:correspondant% and ( p.emergencyContact like %:emergencyContact%)")
	public Page<Patient> findByCorrespondantAndEmergencyContact(@Param("correspondant") String correspondant, @Param("emergencyContact") String emergencyContact, Pageable pageable);

	public Page<Patient> findByEmergencyContactContainingIgnoreCase(@Param("emergencyContact") String emergencyContact, Pageable pageable);

	public List<Patient> findByFirstName(String firstName);

	public List<Patient> findByLastName(String lastName);

	public List<Patient> findByEmail(String email);

	public List<Patient> findByPatientExternalId(String patientExternalId);

	public List<Patient> findByCellPhone1(String cellPhone1);
	
	public List<Patient> findByCnamNumber(String cnamNumber);

	public List<Patient> findByIdCardNumber(String idCardNumber);

	
	@Query(value = "select max(cast(substring(p.patient_external_id,:prefixLength) as UNSIGNED)) from patient p", nativeQuery = true)
	public String findLastExernalId(@Param("prefixLength") int prefixLength);

	@Query(value="select p from Patient p where p.firstName like %:firstName% and ( p.lastName like %:lastName%) and (p.cellPhone1 like %:cellPhone1%) and (p.correspondant like %:correspondant%) and (p.emergencyContact like %:emergencyContact%) and (p.patientExternalId like %:patientExternalId%) and (p.idCardNumber like %:idCardNumber%)")
	public Page<Patient> findByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName,@Param("cellPhone1") String cellPhone1,@Param("correspondant") String correspondant, @Param("emergencyContact") String emergencyContact, @Param("patientExternalId") String patientExternalId,@Param("idCardNumber") String idCardNumber, Pageable pageable);

	@Query(value = "select * from  patient p order by p.created_at desc LIMIT 0,1", nativeQuery = true)
	public Patient findLastPatient();
	
}
