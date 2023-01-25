package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.CashRegisterManagement;
import com.gmhis_backk.dto.CashRegisterManagementDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface CashRegisterManagementService {
	
	public CashRegisterManagement addCashRegisterManagement(CashRegisterManagementDto cashRegisterManagementDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
	
	public CashRegisterManagement updateCashRegisterManagement(UUID id, CashRegisterManagementDto cashRegisterManagementDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;

	public Optional<CashRegisterManagement> getCashRegisterManagement(UUID id);
	
	public List<CashRegisterManagement> findAllCashRegistersMangement();
	
	public Page<CashRegisterManagement> findAllCashRegistersMangement(Pageable page);
	
	public Page<CashRegisterManagement> findAllCashRegistersMangementByCashRegister(Long cashRegister,Pageable pageable);
	
	public Page<CashRegisterManagement> findAllCashRegistersMangementByCashier(Long cashier,Pageable pageable);
	
	public Page<CashRegisterManagement> findAllCashRegistersMangementByState(Boolean state,Pageable pageable);
	
	public Page<CashRegisterManagement> findAllCashRegistersMangementByCashRegisterAndCashier(Long cashRegister, Long cashier, Pageable pageable);
	
	public Page<CashRegisterManagement> findAllCashRegistersMangementByCashRegisterAndState(Long cashRegister, Boolean state, Pageable pageable);

	public Page<CashRegisterManagement> findAllCashRegistersMangementByCashierAndState(Long cashier, Boolean state, Pageable pageable);
	
	public Page<CashRegisterManagement> findAllCashRegistersMangementByCashRegisterAndCashierAndState(Long cashRegister,Long cashier, Boolean state, Pageable pageable);



	
	


}
