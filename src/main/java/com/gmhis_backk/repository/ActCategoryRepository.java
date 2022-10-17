package com.gmhis_backk.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.ActCategory;

@Repository

public interface ActCategoryRepository extends JpaRepository<ActCategory, Long> {
	
	ActCategory findByName(String name);
	
	@Query(value = "SELECT a FROM ActCategory a WHERE active=1")
	List<ActCategory> findAllActive();
	
	@Query(value = "SELECT a FROM ActCategory a")
	Page<ActCategory>findAllActCategory(Pageable pageable);
	
	@Query("SELECT a FROM ActCategory a WHERE a.active =:active AND a.name like %:name%")
	Page<ActCategory> findAllActCategoryByActiveAndName(String name,Boolean active, Pageable pageable);
	
	@Query("SELECT a FROM ActCategory a WHERE a.name like %:name%")
	Page<ActCategory> findAllActCategoryByName(String name, Pageable pageable);
	
	@Query("SELECT a FROM ActCategory a Where a.id =:id")
	ActCategory getActCategoryDetails(Integer id);
	
	@Query("SELECT a FROM ActCategory a")
	List<ActCategory>findAllActCategorySimpleList();
	
}
