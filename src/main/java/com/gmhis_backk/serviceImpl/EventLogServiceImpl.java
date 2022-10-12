package com.gmhis_backk.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.EventLog;
import com.gmhis_backk.repository.EventLogRepository;
import com.gmhis_backk.service.CurrentUserService;
import com.gmhis_backk.service.EventLogService;
import com.gmhis_backk.service.TranslatedClassNameService;

/**
 * 
 * @author Mathurin
 *
 */
@Service
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
public class EventLogServiceImpl implements EventLogService {

	private TranslatedClassNameService translatedClassNameService;
	private CurrentUserService currentUserService;
	private EventLogRepository eventRepo;
	
	@Autowired
	public EventLogServiceImpl(TranslatedClassNameService translatedClassNameService,
			CurrentUserService currentUserService, EventLogRepository eventRepo) {
		super();
		this.translatedClassNameService = translatedClassNameService;
		this.currentUserService = currentUserService;
		this.eventRepo = eventRepo;
	}


	@Override
	public void addEvent(String eventName, String category) {
		EventLog newEvent= new EventLog();
		newEvent.setEvent(eventName);
		newEvent.setCategory(translatedClassNameService.getClassNameInFrench().get(category));
		newEvent.setUserId(currentUserService.getCurrentUserId());
		newEvent.setUserName(currentUserService.getCurrentUserFullName());
		newEvent.setDate(new Date());
		eventRepo.save(newEvent);
	}


	@Override
	public Page<EventLog> findAll(Pageable pageable) {
		
		return eventRepo.findAll(pageable);
	}


	@Override
	public Page<EventLog> findByUserId(Long userId, Pageable pageable) {
		return eventRepo.findByUserId(userId, pageable);
	}


	@Override
	public Page<EventLog> findByCategory(String category, Pageable pageable) {
		return eventRepo.findByCategory(category, pageable);
	}


	@Override
	public List<String> findAllDistinctCategory() {
		return eventRepo.findAllDistinctCategory();
	}


	@Override
	public Page<EventLog> findByDate(String date, Pageable pageable) throws ParseException {
		String[] dates= date.split(",");
		return eventRepo.findByDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[0]+" 00:00:00"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[1]+" 23:59:59"), pageable);
	}


	@Override
	public Page<EventLog> findByCategoryAndUser(String category, Long userId, Pageable pageable) {
		return eventRepo.findByCategoryAndUser(category, userId, pageable);
	}


	@Override
	public Page<EventLog> findByCategoryAndDate(String category, String date, Pageable pageable) throws ParseException {
		String[] dates= date.split(",");
		return eventRepo.findByCategoryAndDate(category, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[0]+" 00:00:00"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[1]+" 23:59:59"), pageable);
	}


	@Override
	public Page<EventLog> findByUserAndDate(Long userId, String date, Pageable pageable) throws ParseException {
		String[] dates= date.split(",");
		return eventRepo.findByUserAndDate(userId, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[0]+" 00:00:00"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[1]+" 23:59:59"), pageable);
	}


	@Override
	public Page<EventLog> findByCategoryAndUserAndDate(String category, Long userId, String date, Pageable pageable) throws ParseException {
		String[] dates= date.split(",");
		return eventRepo.findByCategoryAndUserAndDate(category, userId, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[0]+" 00:00:00"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[1]+" 23:59:59"), pageable);
	}

	
	
}
