package com.gmhis_backk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.EmailType;

/**
 * 
 * @author adjara
 *
 */
@Repository
public interface EmailTypeRepository extends JpaRepository<EmailType, Integer> {

}
