package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.UserHasCashRegister;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service 
@Transactional
public interface UserHasCashRegisterService {

	public UserHasCashRegister saveCashier(UserHasCashRegister c);

	public Optional<UserHasCashRegister> findCashierById(Long id);

	public List<UserHasCashRegister> findCashiers();
	
	public List<UserHasCashRegister> findActiveCashiers();
	
	public Page<UserHasCashRegister> findCashiers(String firstName, String lastName, String phoneContact, Pageable pageable);
	
	public Page<UserHasCashRegister> findByActive(String firstName, String lastName, String phoneContact, String active, Pageable p);
	
	public Page<UserHasCashRegister> findByCashRegister(String firstName, String lastName, String phoneContact,  Long cashRegister, Pageable p);
	
	public Page<UserHasCashRegister> findCashierByAllFilters (String firstName , String lastName, String phoneContact, String active, Long cashRegister, Pageable pageable);
	
	public UserHasCashRegister findCashierByUser(Long user);
	
	public void desactivateAllByUser(Long userId);
	
	public UserHasCashRegister findByUserAndCashRegister(Long user, Long cashRegister);
}
