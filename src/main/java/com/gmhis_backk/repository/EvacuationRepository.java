package com.gmhis_backk.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Evacuation;

@Repository
public interface EvacuationRepository extends JpaRepository<Evacuation, UUID> {
	@Query("SELECT e FROM Evacuation e WHERE e.service.id =:serviceID")
	Page<Evacuation> findAllBYService(@Param("serviceID") Long service,Pageable pageable);
	
}
