package com.gmhis_backk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.ConventionHasActCode;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface ConventionHasActCodeRepository extends JpaRepository<ConventionHasActCode, Long> {

	@Modifying
	@Query(value = "delete from convention_has_act_code c where c.convention_id = :id", nativeQuery = true)
	public void removeAllActCodes(@Param("id") Long conventionId);

	@Modifying
	@Query(value = "delete from convention_has_act_code  c where c.convention_id = :convention_id and c.act_code_id = :act_code_id", nativeQuery = true)
	public void removeActCode(@Param("act_code_id") Long actCodeId, @Param("convention_id") Long conventionId);
}
