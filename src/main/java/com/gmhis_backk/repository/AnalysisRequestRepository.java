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
   
	@Query(value = "select a from AnalysisRequest a where a.admission.patient.id = :patient and a.admission.id =:admissionId")
	public Page<AnalysisRequest> findAnalysisRequestByPatient(@Param("patient") Long patient, @Param("admissionId") Long admissionId, Pageable pageable);
	
	@Query(value = "select a from AnalysisRequest a where a.admission.admissionNumber like %:admissionNumber%")
	public Page<AnalysisRequest> findAnalysisRequestsByAdmissionNumber(@Param("admissionNumber") String admissionNumber, Pageable pageable);

	@Query(value = "select a from AnalysisRequest a ORDER BY a.createdAt DESC")
	public Page<AnalysisRequest> findAllAnalysis(Pageable pageable);
	
	@Query(value = "select a from AnalysisRequest a where a.examenType=:examenType and a.facility.id=:facilityId ORDER BY a.createdAt")
	public Page<AnalysisRequest> findAllAnalysisRequestsByFacility(@Param("examenType") Boolean examenType,@Param("facilityId") UUID facilityId, Pageable pageable);
	
	@Query(value = "select Count(a) from AnalysisRequest a where a.admission.id = :admissionId")
	public Long findAnalyseRequestNumber(@Param("admissionId") Long admissionId);
	
	@Query(value = "select * from Analysis_request p order by p.created_at desc LIMIT 0,1", nativeQuery = true)
	public  AnalysisRequest findLastAnalysisMedical();
}
