package com.gmhis_backk.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.EventLog;

/**
 * 
 * @author adjara
 *
 */

@Repository
public interface EventLogRepository extends JpaRepository<EventLog, Long> {
	
	Page<EventLog> findByUserId(Long userId, Pageable pageable);

	Page<EventLog> findByCategory(String category, Pageable pageable);
     
	@Query("SELECT distinct e.category from EventLog e ")
	List<String> findAllDistinctCategory();
	
	@Query("SELECT e FROM EventLog e where date BETWEEN :start AND :end")
	Page<EventLog> findByDate(Date start, Date end, Pageable pageable);

	@Query("SELECT e FROM EventLog e where category like :category% AND userId= :userId")
	Page<EventLog> findByCategoryAndUser(String category, Long userId, Pageable pageable);

	@Query("SELECT e FROM EventLog e where category like :category% AND date BETWEEN :start AND :end")
	Page<EventLog> findByCategoryAndDate(String category, Date start, Date end, Pageable pageable);

	@Query("SELECT e FROM EventLog e where userId = :userId AND date BETWEEN :start AND :end")
    Page<EventLog> findByUserAndDate(Long userId, Date start, Date end, Pageable pageable);

	@Query("SELECT e FROM EventLog e where category like :category% AND userId = :userId AND date BETWEEN :start AND :end")
	Page<EventLog> findByCategoryAndUserAndDate(String category, Long userId, Date start, Date end, Pageable pageable);
}
