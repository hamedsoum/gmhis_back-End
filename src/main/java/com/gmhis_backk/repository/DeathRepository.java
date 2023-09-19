/**
 * 
 */
package com.gmhis_backk.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Death;

/** 
 * @author Hamed Soumahoro
 *
 */

@Repository
public interface DeathRepository extends JpaRepository<Death, UUID> {

}
