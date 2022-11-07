package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Insurance;

@Repository
public interface InsurranceRepository extends JpaRepository<Insurance, Long> {
	
		Insurance findByName(String name);
		
		@Query(value = "SELECT i FROM Insurance i WHERE active=1")
		List<Insurance> findAllActive();
		
		@Query(value = "SELECT i FROM Insurance i")
		Page<Insurance>findAllInsurance(Pageable pageable);
		
		@Query("SELECT i FROM Insurance i WHERE i.active =:active AND i.name like %:name%")
		Page<Insurance> findAllInsuranceByActiveAndName(String name,Boolean active, Pageable pageable);
		
		@Query("SELECT i FROM Insurance i WHERE i.name like %:name%")
		Page<Insurance> findAllInsuranceByName(String name, Pageable pageable);
		
		@Query("SELECT i FROM Insurance i Where i.id =:id")
		Insurance getActInsurranceDetails(Long id);
		
		@Query("SELECT i FROM Insurance i")
		List<Insurance>findAllinsuranceSimpleList();
		
		@Query(value = "SELECT i FROM Insurance i where id= :id")
		Optional<Insurance> findById(Long id);
}
