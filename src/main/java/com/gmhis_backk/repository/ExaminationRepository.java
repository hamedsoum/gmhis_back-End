package com.gmhis_backk.repository;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Examination;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {
	
	@Query(value = "select e from Examination e where e.pratician.id = :practicianID")
	public Page<Examination> retrievePracticianExaminations(@Param("practicianID") Long practicianID, Pageable pageable);
	
	@Query(value = "SELECT * FROM `examination` WHERE admission_id = (SELECT id FROM admission a WHERE a.patient_id =:patientID ORDER BY ID DESC LIMIT 1)", nativeQuery = true)
	public List<Examination> findPatientExaminationsOfLastAdmision(@Param("patientID") Long patientID);
	
	@Query(value = "SELECT * FROM `examination` WHERE admission_id = (SELECT id FROM admission a WHERE a.id =:admissionID ORDER BY ID DESC LIMIT 1) ORDER BY id ASC LIMIT 1", nativeQuery = true)
	public Examination findAdmissionFirstExamination(@Param("admissionID") Long admissionID);

	@Query(value = "SELECT * FROM examination e WHERE e.admission_id IN (SELECT id FROM admission a WHERE a.patient_id = :patientID) GROUP BY e.admission_id", nativeQuery = true)
	public Page<Examination> findPatientFirstExaminationsOfAdmisions(@Param("patientID") Long patientID, Pageable pageable);
	
	
	@Query(value = "SELECT * FROM examination e WHERE e.start_date IN (SELECT MIN(start_date) FROM examination e, admission a WHERE a.patient_id = :patientID GROUP BY admission_id)", nativeQuery = true)
	public Page<Examination> findPatientExaminationsOfAdmisions(@Param("patientID") Long patientID, Pageable pageable);
	
	
	@Query(value = "select e from Examination e where e.admission.patient.id = :patient AND e.admission.id =:admissionID  ")
	public Page<Examination> findPatientExaminations(@Param("patient") Long patient, @Param("admissionID") Long admissionID, Pageable pageable);
	
	@Query(value = "select Count(e) from Examination e where e.admission.id = :admissionID")
	public Long findPatientExaminationsNumber(@Param("admissionID") Long admissionID);
	
	@Modifying
	@Transactional
	@Query(value="delete from examination_has_pathology where examination_id = :id", nativeQuery = true)
	public void removeExaminationAllPathologies (Long id);
	
	@Query(value = "select * from  examination e where e.admission_id = :id  order by e.id desc LIMIT 0,1", nativeQuery = true)
	public Examination findLastExamination(Long id);
	
	@Query(value="select e from Examination e where e.id = :id")
	public Optional<Examination> findExaminationById(@Param("id") Long id);
}
