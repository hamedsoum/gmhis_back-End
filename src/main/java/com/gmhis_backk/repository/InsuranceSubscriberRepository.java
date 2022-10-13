package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.ActGroup;
import com.gmhis_backk.domain.InsuranceSuscriber;

@Repository
public interface InsuranceSubscriberRepository extends JpaRepository<InsuranceSuscriber, Long> {
	InsuranceSuscriber findByName(String name);
	
	@Query(value = "SELECT i FROM InsuranceSuscriber i WHERE active=1")
	List<InsuranceSuscriber> findAllActive();
	
	@Query(value = "SELECT i FROM InsuranceSuscriber i")
	Page<InsuranceSuscriber>findAllInsuranceSuscriber(Pageable pageable);
	
	@Query("SELECT i FROM InsuranceSuscriber i WHERE i.active =:active AND i.name like %:name%")
	Page<InsuranceSuscriber> findAllInsuranceSuscriberByActiveAndName(String name,Boolean active, Pageable pageable);
	
	@Query("SELECT i FROM InsuranceSuscriber i WHERE i.name like %:name%")
	Page<InsuranceSuscriber> findAllInsuranceSuscriberByName(String name, Pageable pageable);
	
	@Query("SELECT i FROM InsuranceSuscriber i Where i.id =:id")
	InsuranceSuscriber getInsuranceSuscriberDetails(Long id);
	
	@Query("SELECT i FROM InsuranceSuscriber i")
	List<InsuranceSuscriber>findAllInsuranceSuscriberSimpleList();
	
	@Query(value = "SELECT i FROM InsuranceSuscriber i where id= :id")
	Optional<InsuranceSuscriber> findById(Long id);
}

