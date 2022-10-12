package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.ActGroup;

@Repository

public interface ActGroupRepository extends JpaRepository<ActGroup, Long> {
	
	
	ActGroup findByName(String name);
	
	@Query(value = "SELECT a FROM ActGroup a WHERE active=1")
	List<ActGroup> findAllActive();
	
	@Query(value = "SELECT a FROM ActGroup a")
	Page<ActGroup>findAllActGroup(Pageable pageable);
	
	@Query("SELECT a FROM ActGroup a WHERE a.active =:active AND a.name like %:name%")
	Page<ActGroup> findAllActGroupByActiveAndName(String name,Boolean active, Pageable pageable);
	
	@Query("SELECT a FROM ActGroup a WHERE a.name like %:name%")
	Page<ActGroup> findAllActGroupByName(String name, Pageable pageable);
	
	@Query("SELECT a FROM ActGroup a Where a.id =:id")
	ActGroup getActGroupDetails(Integer id);
	
	@Query("SELECT a FROM ActGroup a")
	List<ActGroup>findAllActGroupSimpleList();
	
	@Query(value = "SELECT a FROM ActGroup a where id= :id")
	Optional<ActGroup> findById(Long id);
}
