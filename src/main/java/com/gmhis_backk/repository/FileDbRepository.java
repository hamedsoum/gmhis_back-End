package com.gmhis_backk.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Files;


@Repository 
@Transactional
public interface FileDbRepository extends JpaRepository<Files, UUID> {
		
	@Query(value ="SELECT f FROM Files f WHERE f.entityId =:facilityId")
	public List<Files> findFIleByFacilityId(@Param("facilityId") String facilityId);
}
