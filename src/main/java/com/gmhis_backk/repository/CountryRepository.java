package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Country;


/**
 * 
 * @author Pascal
 *
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
	public Optional<Country> findById(Long id);

	@Modifying
	@Query(value="delete from country where id = :id", nativeQuery=true)
	public void deleteCountry(@Param("id") Long id);
	
	public List<Country> findByNameContainingIgnoreCase(String name);
	
	public Page<Country> findByNameContainingIgnoreCase(String name,Pageable page);
	
	

}
