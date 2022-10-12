package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.ActGroup;
import com.gmhis_backk.domain.Convention;

@Repository
public interface ConventionRepository extends JpaRepository<Convention, Long> {
	
	
	Convention findByName(String name);
	
	@Query(value = "SELECT c FROM Convention c WHERE active=1")
	List<Convention> findAllActive();
	
	@Query(value = "SELECT c FROM Convention c")
	Page<Convention>findAllActConvention(Pageable pageable);
	
	@Query("SELECT c FROM Convention c WHERE c.active =:active AND c.name like %:name%")
	Page<Convention> findAllConventionByActiveAndName(String name,Boolean active, Pageable pageable);
	
	@Query("SELECT c FROM Convention c WHERE c.name like %:name%")
	Page<Convention> findAllConventionByName(String name, Pageable pageable);
	
	@Query("SELECT c FROM Convention c Where c.id =:id")
	ActGroup geConventionDetails(Integer id);
	
	@Query("SELECT c FROM Convention c")
	List<Convention>findAllConventionSimpleList();
	
	@Query(value = "SELECT c FROM ActGroup c where id= :id")
	Optional<Convention> findById(Long id);

}
