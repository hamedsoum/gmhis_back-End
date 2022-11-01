package com.gmhis_backk.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.PatientConstant;


/**
 * 
 * @author Pascal
 *
 */
@Repository
public interface PatientConstantRepository extends JpaRepository<PatientConstant, Long> {

	@Query(value = "select p from PatientConstant p join p.patient pat where pat.id = :id ")
	public List<PatientConstant> findPatientConstant(@Param("id") Long patientId);

	@Query(value = "select p from PatientConstant p join p.patient pat where pat.id = :id")
	public Page<PatientConstant> findPatientConstant(@Param("id") Long patientId, Pageable pageable);
	
	@Query(value = "select p from PatientConstant p join p.patient pat where pat.id = :id and p.takenAt between :date1 and :date2")
	public Page<PatientConstant> findPatientConstantByDate(@Param("id") Long patientId, @Param("date1") Date date1, @Param("date2") Date date2, Pageable pageable);

}
