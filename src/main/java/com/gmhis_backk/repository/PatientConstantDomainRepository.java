package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.ActGroup;
import com.gmhis_backk.domain.PatientConstantDomain;

@Repository
public interface PatientConstantDomainRepository extends JpaRepository<PatientConstantDomain, Long> {

	PatientConstantDomain findByName(String name);
	
	@Query(value = "SELECT c FROM PatientConstantDomain c WHERE active=1")
	List<PatientConstantDomain> findAllActive();
	
	@Query(value = "SELECT c FROM PatientConstantDomain c")
	Page<PatientConstantDomain>findAllConstantDomain(Pageable pageable);
	
	@Query("SELECT c FROM PatientConstantDomain c WHERE c.active =:active AND c.name like %:name%")
	Page<PatientConstantDomain> findAllConstantDomainByActiveAndName(String name,Boolean active, Pageable pageable);
	
	@Query("SELECT c FROM PatientConstantDomain c WHERE c.name like %:name%")
	Page<PatientConstantDomain> findAllConstantDomainByName(String name, Pageable pageable);
	
	@Query("SELECT c FROM PatientConstantDomain c Where c.id =:id")
	ActGroup getActGroupDetails(Integer id);
	
	@Query("SELECT c FROM PatientConstantDomain c")
	List<PatientConstantDomain>findAllConstantDomainSimpleList();
	
	@Query(value = "SELECT c FROM PatientConstantDomain c where id= :id")
	Optional<PatientConstantDomain> findById(Long id);
}

