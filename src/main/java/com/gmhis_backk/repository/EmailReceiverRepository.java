package com.gmhis_backk.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.EmailReceiver;

/**
 * 
 * @author adjara
 *
 */
@Repository
public interface EmailReceiverRepository extends JpaRepository<EmailReceiver, Integer>{
	
	@Query("SELECT e FROM EmailReceiver e WHERE e.emailType.id = :emailType")
	List<EmailReceiver> getEmailReceiversByEmailType(int emailType); 

}
