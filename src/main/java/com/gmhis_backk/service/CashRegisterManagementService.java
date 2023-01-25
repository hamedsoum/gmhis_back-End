package com.gmhis_backk.service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
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
	
	

}
