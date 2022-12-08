package com.gmhis_backk.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gmhis_backk.domain.AnalysisRequestItem;

public interface AnalysisRequestItemRepository extends JpaRepository<AnalysisRequestItem, UUID> {
	@Query(value = "select p from AnalysisRequestItem p where p.analysisRequest.id = :analysisRequest")
	public List<AnalysisRequestItem> findAnalysisRequestItemByAnalysisRequest(@Param("analysisRequest") Long analysisRequest);

}
