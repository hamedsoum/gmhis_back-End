package com.gmhis_backk.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Service;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

	public Service findByName(String name);

	@Query(value = "select s from Service s where active = 1")
	public List<Service> findActiveServices();

	public Page<Service> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	@Query(value = "select s from Service s where s.name like %:name% and s.active = :active")
	public Page<Service> findByActive(@Param("name") String name,
			@Param("active") Boolean active, Pageable p);

}
