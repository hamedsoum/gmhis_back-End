package com.gmhis_backk.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.City;


/**
 * 
 * @author Pascal
 *
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	@Modifying
	@Query(value = "delete from city where id = :id", nativeQuery = true)
	public void deleteCity(@Param("id") Long id);

	@Query(name = "select c from City c join c.country co where co.id = :id")
	public List<City> findCitiesByCountryId(@Param("id") Long countryId);
	
	@Query(name = "select c from City c join c.country co where co.id = :id")
	public Page<City> findCitiesByCountryId(@Param("id") Long countryId,Pageable page);

	public List<City> findByNameContainingIgnoreCase(String name);

	public Page<City> findByNameContainingIgnoreCase(String name, Pageable page);
	
	@Query(name = "select c from City c join c.country co where co.id = :id and c.name like %:name%")
	public Page<City> findCitiesByNameContainingIgnoreCaseAndCountryId(@Param("name") String name,@Param("id") Long countryId, Pageable page);

}
