package com.gmhis_backk.repository;


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
	

	@Query(value = "select a from Examination a where a.admission.patient.id = :patient")
	public Page<Examination> findPatientExaminations(@Param("patient") Long patient, Pageable pageable);
	
	@Query(value = "select Count(e) from Examination e where e.admission.patient.id = :patientId")
	public Long findPatientExaminationsNumber(@Param("patientId") Long patientId);
	
	@Modifying
	@Transactional
	@Query(value="delete from examination_has_pathology where examination_id = :id", nativeQuery = true)
	public void removeExaminationAllPathologies (Long id);
}
