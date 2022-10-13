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

	public List<Patient> findByFirstName(String firstName);

	public List<Patient> findByLastName(String lastName);

	public List<Patient> findByEmail(String email);

	public List<Patient> findByPatientExternalId(String patientExternalId);

	public List<Patient> findByCellPhone1OrCellPhone2(String cellPhone1, String cellPhone2);

	public List<Patient> findByCnamNumber(String cnamNumber);

	public List<Patient> findByIdCardNumber(String idCardNumber);

	@Query(value = "select max(cast(substring(p.patient_external_id,:prefixLength) as UNSIGNED)) from patient p", nativeQuery = true)
	public String findLastExernalId(@Param("prefixLength") int prefixLength);

	@Query(value = "select p from Patient p where (p.firstName like %:firstName%  and p.lastName like %:lastName%) and (p.cellPhone1 like %:cellPhone% or p.cellPhone2 like %:cellPhone% "
			+ " or p.patientExternalId like %:patientExternalId% or p.cnamNumber like %:cnamNumber% or p.idCardNumber like %:idCardNumber% )")
	public Page<Patient> findPatients(@Param("firstName") String firstName, @Param("lastName") String lastName,
			@Param("patientExternalId") String patientExternalId, @Param("cellPhone") String cellPhone,
			@Param("cnamNumber") String cnamNumber, @Param("idCardNumber") String idCardNumber, Pageable pageable);

	public Page<Patient> findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseOrPatientExternalIdContainingIgnoreCaseOrCnamNumberContainingIgnoreCaseOrIdCardNumberContainingIgnoreCaseOrCellPhone1ContainingIgnoreCase(
			String firstName, String lastName, String patientExternalId, String cellPhone, String cnamNumber,
			String idCardNumber, Pageable pageable);
	
//	@Query(value =" Select p from Patient p where p.firsName like %:firstName%")
	public Page<Patient> findByFirstNameContainingIgnoreCase(@Param("firstName") String firstName, Pageable pageable);

	public Page<Patient> findByLastNameContainingIgnoreCase(@Param("lastName") String lastName, Pageable pageable);

	public Page<Patient> findByPatientExternalIdContainingIgnoreCase(@Param("patientExternalId") String patientExternalId, Pageable pageable);

	@Query(value="select p from Patient p where p.cellPhone1 like %:cellPhone% or p.cellPhone2 like %:cellPhone% ")
	public Page<Patient> findByCellPhone(@Param("cellPhone") String cellPhone, Pageable pageable);

	public Page<Patient> findByCnamNumberContainingIgnoreCase(@Param("cnamNumber") String cnamNumber, Pageable pageable);

	public Page<Patient> findByIdCardNumber(@Param("idCardNumber") String idCardNumber, Pageable pageable);

	@Query(value="select p from Patient p where p.firstName like %:firstName% and ( p.lastName like %:lastName% or p.maidenName like %:lastName%)")
	public Page<Patient> findByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName, Pageable pageable);

	
}
