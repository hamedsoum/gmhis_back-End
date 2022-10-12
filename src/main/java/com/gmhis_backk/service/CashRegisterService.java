package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.CashRegister;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface CashRegisterService {
	Page<CashRegister> findAllCashRegister(Pageable pageable);
    
	  Page<CashRegister> findAllCashRegisterByActiveAndName(String name,Boolean active, Pageable pageable);
	    
	  Page<CashRegister> findAllCashRegisterByName(String name, Pageable pageable);
	  
	  List<CashRegister> findAllActGroups();

	  void deleteCashRegister(Integer id);
		
	  Optional<CashRegister> getCashRegisterDetails(Long id); 
	  
	  CashRegister addCashRegister(DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  CashRegister updateCashRegister(Long id,DefaultNameAndActiveDto defaultNameAndActiveDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
}
