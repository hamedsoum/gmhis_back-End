package com.gmhis_backk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Communes;

/**
 * 
 * @author Mathurin
 *
 */
@Repository
public interface CommunesRepository extends JpaRepository<Communes, Long> {
	
	Communes findByName(String name);

	@Query("SELECT c FROM Communes c WHERE c.name LIKE %:name% AND c.deletedAt IS null ORDER BY c.name ASC")
	List<Communes> findAllCommune(String name);
	
	@Query("SELECT c FROM Communes c WHERE c.name LIKE %:name% AND c.ville.id=:villeId AND c.deletedAt IS null ORDER BY c.name ASC")
	List<Communes> findByVille(String name,Long villeId);
	
	@Query("SELECT c FROM Communes c WHERE c.id=:id")
	Communes getDetail(Long id);
	
	@Query("SELECT c FROM Communes c WHERE  c.ville.id=:villeId AND c.deletedAt IS null")
	List<Communes> findByVille(Long villeId);
}
