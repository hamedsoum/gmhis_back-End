package com.gmhis_backk.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Files;


@Repository 
@Transactional
public interface FileDbRepository extends JpaRepository<Files, UUID> {
		
	
}
