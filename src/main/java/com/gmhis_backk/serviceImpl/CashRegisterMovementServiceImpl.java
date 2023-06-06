/**
 * 
 */
package com.gmhis_backk.serviceImpl;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.CashRegister;
import com.gmhis_backk.domain.CashRegisterMovement;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.CashRegisterMovementDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.CashRegisterMovementRepository;
import com.gmhis_backk.repository.CashRegisterRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.CashRegisterMovementService;

/**
 * @author mamadouhamedsoumahoro
 *
 */

@Service
public class CashRegisterMovementServiceImpl implements CashRegisterMovementService  {

	@Autowired
	private CashRegisterMovementRepository cashRegisterMovementRepository;
	
	@Autowired
	private CashRegisterRepository cashRegisterRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override @Transactional
	public CashRegisterMovement addNewMovement(CashRegisterMovementDto cdDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {

			CashRegister cashRegister = cashRegisterRepository.findById(cdDto.getCashRegister()).orElse(null);
			
			if (cashRegister == null) 
			throw new ResourceNotFoundByIdException("Aucune caisse trouve pour l'identidifiant");
			
			User createdBy = userRepository.findById(this.getCurrentUserId().getId()).orElse(null);

			if (createdBy == null) 
				throw new ResourceNotFoundByIdException("Aucune utilisateur trouve pour l'identidifiant");
			
			CashRegisterMovement newcashRegisterMovement = new CashRegisterMovement();
			BeanUtils.copyProperties(cdDto, newcashRegisterMovement);
			newcashRegisterMovement.setCashRegister(cashRegister);
			
			newcashRegisterMovement.setUser(createdBy);
			return cashRegisterMovementRepository.save(newcashRegisterMovement);
	}

	@Override
	public CashRegisterMovement updateMovement(UUID id, CashRegisterMovementDto cdDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CashRegisterMovement> getCaMovement(Pageable page) {
		return cashRegisterMovementRepository.getCas(page);
	}

	@Override
	public Page<CashRegisterMovement> getCaMovementNByPrestationNumber(String prestationNumber, Pageable page) {
		return cashRegisterMovementRepository.getCaPageByPrestationNumber(prestationNumber, page);
	}

	@Override
	public Page<CashRegisterMovement> getCaMovementNByCashRegister(Long cashRegister, Pageable page) {
		return cashRegisterMovementRepository.getCaPageByCashRegister(cashRegister, page);
	}

	@Override
	public Page<CashRegisterMovement> getCaMovementNByCashRegisterAndPrestationNumber(Long cashRegister, String prestationNumber,
			Pageable page) {
		return cashRegisterMovementRepository.getCaPageByCashRegisterAndPrestationNumber(cashRegister, prestationNumber, page);
	}

	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override
	public Page<CashRegisterMovement> getCaMovementByUser(Long User, Pageable page) {
		return cashRegisterMovementRepository.getCaPageByUser(User, page);
	}
}
