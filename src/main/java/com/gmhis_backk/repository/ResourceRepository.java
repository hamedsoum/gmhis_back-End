package com.gmhis_backk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Resource;

/**
 * 
 * @author adjara
 *
 */
@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer>{

	@Query(value = "SELECT r FROM Resource r where isActive=1")
	List<Resource> findAll();
}
