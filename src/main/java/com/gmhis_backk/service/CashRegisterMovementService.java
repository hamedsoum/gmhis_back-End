/**
 * 
 */
package com.gmhis_backk.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.CashRegisterManagement;
import com.gmhis_backk.domain.CashRegisterMovement;
import com.gmhis_backk.dto.CashRegisterMovementDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

/**
 * @author mamadou hamed soumahoro
 *
 */

@Service
public interface CashRegisterMovementService {
	
	public CashRegisterMovement addNewMovement(CashRegisterMovementDto cdDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
	
	public CashRegisterMovement updateMovement(UUID id, CashRegisterMovementDto cdDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
	
	public Page<CashRegisterMovement> getCaMovement(Pageable page);
	
	public Page<CashRegisterMovement> getCaMovementNByPrestationNumber(String prestationNumber, Pageable page);
	
	public Page<CashRegisterMovement> getCaMovementNByCashRegister(Long cashRegister, Pageable page);
	
	public Page<CashRegisterMovement> getCaMovementByUser(Long User, Pageable page);

	public Page<CashRegisterMovement> getCaMovementNByCashRegisterAndPrestationNumber(Long cashRegister,String prestationNumber, Pageable page);
	

}
