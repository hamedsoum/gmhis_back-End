package com.gmhis_backk.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.AnalysisRequest;


/**
 * 
 * @author Hamed soumahoro
 *
 */

@Repository
public interface AnalysisRequestRepository extends JpaRepository<AnalysisRequest, UUID> {
   
	@Query(value = "select a from AnalysisRequest a where a.admission.patient.id = :patient")
	public Page<AnalysisRequest> findAnalysisRequestByPatient(@Param("patient") Long patient, Pageable pageable);
	
	@Query(value = "select a from AnalysisRequest a where a.admission.admissionNumber like %:admissionNumber%")
	public Page<AnalysisRequest> findAnalysisRequestsByAdmissionNumber(@Param("admissionNumber") String admissionNumber, Pageable pageable);
	
	@Query(value = "select a from AnalysisRequest a ORDER BY a.createdAt DESC")
	public Page<AnalysisRequest> findAllAnalysis(Pageable pageable);
	
	@Query(value = "select a from AnalysisRequest a where a.admission.patient.patientExternalId like %:patientExternalId% and a.admission.patient.cnamNumber like %:cnamNumber% and a.admission.patient.idCardNumber like %:idCardNumber% and a.state=:state ORDER BY a.createdAt")
	public Page<AnalysisRequest> findAllAnalysisRequests(String patientExternalId,@Param("cnamNumber") String cnamNumber, @Param("idCardNumber") String idCardNumber, @Param("state") String state, Pageable pageable);
	
	@Query(value = "select Count(a) from AnalysisRequest a where a.admission.patient.id = :patientId")
	public Long findAnalyseRequestNumber(@Param("patientId") Long patientId);
	
	@Query(value = "select * from Analysis_request p order by p.created_at desc LIMIT 0,1", nativeQuery = true)
	public  AnalysisRequest findLastAnalysisMedical();
}
