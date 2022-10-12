package com.gmhis_backk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Authority;

/**
 * 
 * @author adjara
 *
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer>{
 
	@Query(value = "SELECT r.authorities FROM user_role ur LEFT JOIN role r ON ur.role_id = r.id where ur.user_id = :userId and r.is_active= 1", nativeQuery = true)
	List<String> findAuthoritiesByUser(Long userId);
	
	@Query(value = "SELECT distinct(a.name) from authority a where id in :ids and a.is_active = 1", nativeQuery = true)
	List<Object> findAuthoritiesByids(List<Integer> ids);
	
	@Query(value = "SELECT id FROM authority where name in :names", nativeQuery = true)
	List<Integer> findAuthorityIdsByNames(String[] names);
}
