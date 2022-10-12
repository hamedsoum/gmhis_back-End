package com.gmhis_backk.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.EventLog;

/**
 * 
 * @author adjara
 *
 */
@Service
public interface EventLogService {

	public void addEvent(String eventName, String category);

	Page<EventLog> findAll(Pageable pageable);

	List<String> findAllDistinctCategory();

	Page<EventLog> findByDate(String date, Pageable pageable) throws ParseException;

	Page<EventLog> findByUserId(Long userId, Pageable pageable);

	Page<EventLog> findByCategory(String category, Pageable pageable);

	Page<EventLog> findByCategoryAndUser(String category, Long userId, Pageable pageable);

	Page<EventLog> findByCategoryAndDate(String category, String date, Pageable pageable) throws ParseException;

	Page<EventLog> findByUserAndDate(Long userId, String date, Pageable pageable) throws ParseException;

	Page<EventLog> findByCategoryAndUserAndDate(String category, Long userId, String date, Pageable pageable) throws ParseException;

}
