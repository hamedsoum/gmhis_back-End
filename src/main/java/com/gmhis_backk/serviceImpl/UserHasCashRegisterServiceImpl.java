package com.gmhis_backk.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.UserHasCashRegister;
import com.gmhis_backk.repository.UserHasCashRegisterRepository;
import com.gmhis_backk.service.UserHasCashRegisterService;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public class UserHasCashRegisterServiceImpl implements UserHasCashRegisterService {


	@Autowired
	 UserHasCashRegisterRepository repo;

	
	public UserHasCashRegister findCashierByUser (Long user) {
		return repo.findByUser(user);
	}

	@Override
	public UserHasCashRegister saveCashier(UserHasCashRegister c) {
		return repo.save(c);
	}


	@Override
	public Optional<UserHasCashRegister> findCashierById(Long id) {
		return repo.findById(id);
	}

	
	@Override
	public List<UserHasCashRegister> findCashiers() {
		return repo.findAll();
	}

	
	@Override
	public Page<UserHasCashRegister> findCashiers(String firstName, String lastName, String phoneContact, Pageable pageable) {
		return repo.findCashiers(firstName, lastName, phoneContact, pageable);
	}
	
	
	@Override
	public List<UserHasCashRegister> findActiveCashiers() {
		return repo.findActiveCashiers();
	}

	@Override
	public Page<UserHasCashRegister> findByActive(String firstName, String lastName, String phoneContact, String active, Pageable pageable){
		return repo.findByActive(firstName, lastName, phoneContact, active, pageable);
	}

	
	@Override
	public Page<UserHasCashRegister> findByCashRegister(String firstName, String lastName, String phoneContact, Long cashRegister, Pageable pageable){
		return repo.findByCashRegister (firstName, lastName, phoneContact, cashRegister, pageable);
	}
	
	
	@Override
	public Page<UserHasCashRegister> findCashierByAllFilters (String firstName, String lastName, String phoneContact, String active, Long cashRegister, Pageable pageable){
		return repo.findCashierByAllFilters(firstName, lastName, phoneContact, active, cashRegister, pageable);
	}
	
	@Override
	public void desactivateAllByUser(Long userId) {
		repo.desactivateAllByUser(userId);
	}
	
	@Override
	public UserHasCashRegister findByUserAndCashRegister(Long user, Long cashRegister) {
		return repo.findByUserAndCashRegister(user, cashRegister);
	}

}
