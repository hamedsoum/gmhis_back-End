package com.gmhis_backk.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gmhis_backk.domain.ExamenComplementary;

public interface ExamenComplementaryRepository extends JpaRepository<ExamenComplementary, UUID> {
	@Query("SELECT e FROM ExamenComplementary e WHERE e.facilityID =:facility")
	Page<ExamenComplementary> findAll(@Param("facility") UUID facility,Pageable pageable);
}
