package com.gmhis_backk.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.CashRegister;
import com.gmhis_backk.domain.CashRegisterManagement;
import com.gmhis_backk.domain.CashRegisterMovement;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.CashRegisterManagementDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.CashRegisterManagementRepository;
import com.gmhis_backk.service.CashRegisterManagementService;
import com.gmhis_backk.service.CashRegisterService;
import com.gmhis_backk.service.UserService;

@Service
public class CashRegisterManagementServiceImpl implements CashRegisterManagementService {

	@Autowired
	private CashRegisterManagementRepository cashRegisterManagementRepository;
	
	@Autowired
	private CashRegisterService cashRegisterService;
	
	@Autowired
	private UserService userService;
	
	@Override @Transactional 
	public CashRegisterManagement addCashRegisterManagement(CashRegisterManagementDto cashRegisterManagementDto)
		throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		
		
		CashRegister cashRegister = cashRegisterService.findCashRegisterById(cashRegisterManagementDto.getCashRegister()).orElse(null);
		if (cashRegister == null) 
			throw new ResourceNotFoundByIdException("aucune caisse trouvé pour l'identifiant");
		
		User cashier = userService.findUserById(cashRegisterManagementDto.getCashier());
		if (cashier == null) 
		throw new ResourceNotFoundByIdException("aucun utilisateur trouve pour l'identifiant");
			
	      this.verifyCashRegisterAndCashier(cashRegisterManagementDto.getCashier(), cashRegisterManagementDto.getCashRegister());

		CashRegisterManagement cashRegisterManagement = new CashRegisterManagement();	
		BeanUtils.copyProperties(cashRegisterManagementDto,cashRegisterManagement,"id");
		if (cashRegisterManagementDto.getOpeningBalance() != 0) {
			cashRegisterManagement.setCashRegisterBalance(cashRegisterManagementDto.getOpeningBalance());
		}
		cashRegisterManagement.setCashier(cashier);
		cashRegisterManagement.setCashRegister(cashRegister);
		cashRegisterManagement.setOpeningDate(new Date());
		cashRegisterManagement.setState(true);

		return cashRegisterManagementRepository.save(cashRegisterManagement);

	}

	@Override @Transactional
	public CashRegisterManagement updateCashRegisterManagement(UUID id, CashRegisterManagementDto cashRegisterManagementDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		CashRegisterManagement updateCashRegisterManagement = cashRegisterManagementRepository.findById(id).orElse(null);
		
		
		if (cashRegisterManagementDto.getRealClosingBalance() == 0) {
			if (updateCashRegisterManagement == null) 
				throw new ResourceNotFoundByIdException("Aucune gestion de caisse trouve pour l'identifiant");
			
			CashRegister cashRegister = cashRegisterService.findCashRegisterById(cashRegisterManagementDto.getCashRegister()).orElse(null);
			if (cashRegister == null) 
				throw new ResourceNotFoundByIdException("aucune caisse trouvé pour l'identifiant");
			
			User cashier = userService.findUserById(cashRegisterManagementDto.getCashier());
			if (cashier == null) 
			throw new ResourceNotFoundByIdException("aucun utilisateur trouve pour l'identifiant");	
			updateCashRegisterManagement.setCashier(cashier);
			updateCashRegisterManagement.setCashRegister(cashRegister);
			updateCashRegisterManagement.setCashRegisterBalance(cashRegisterManagementDto.getCashRegisterBalance());
			updateCashRegisterManagement.setOpeningBalance(cashRegisterManagementDto.getOpeningBalance());
			updateCashRegisterManagement.setOpeningDate(new Date());
			updateCashRegisterManagement.setState(true);
		}else {
			updateCashRegisterManagement.setRealClosingBalance(cashRegisterManagementDto.getRealClosingBalance());
			updateCashRegisterManagement.setClosingDate(new Date());
			updateCashRegisterManagement.setState(false);
		}
	
		
//		System.out.println(newCashRegisterBalance);
		return cashRegisterManagementRepository.save(updateCashRegisterManagement);
	}

	@Override
	public List<CashRegisterManagement> findAllCashRegistersMangement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CashRegisterManagement> findAllCashRegistersMangement(Pageable page) {
		return cashRegisterManagementRepository.findAll(page);
	}

	@Override
	public Page<CashRegisterManagement> findAllCashRegistersMangementByCashRegister(Long cashRegister,
			Pageable pageable) {
		return cashRegisterManagementRepository.findAllCashRegisterManagementByCashRegister(cashRegister, pageable);
	}

	@Override
	public Optional<CashRegisterManagement> getCashRegisterManagement(UUID id) {
		return cashRegisterManagementRepository.findById(id);
	}

	@Override
	public Page<CashRegisterManagement> findAllCashRegistersMangementByCashier(Long cashier, Pageable pageable) {
		return cashRegisterManagementRepository.findAllCashRegisterManagementByCashier(cashier, pageable);
	}

	@Override
	public Page<CashRegisterManagement> findAllCashRegistersMangementByState(Boolean state, Pageable pageable) {
		return cashRegisterManagementRepository.findAllCashRegisterManagementByState(state, pageable);
	}

	@Override
	public Page<CashRegisterManagement> findAllCashRegistersMangementByCashRegisterAndCashier(Long cashRegister,
			Long cashier, Pageable pageable) {
		return cashRegisterManagementRepository.findAllCashRegisterManagementByCashRegisterAndCashier(cashRegister, cashier, pageable);
	}

	@Override
	public Page<CashRegisterManagement> findAllCashRegistersMangementByCashRegisterAndState(Long cashRegister,
			Boolean state, Pageable pageable) {
		return cashRegisterManagementRepository.findAllCashRegisterManagementByCashRegisterAndState(cashRegister, state, pageable);
	}

	@Override
	public Page<CashRegisterManagement> findAllCashRegistersMangementByCashierAndState(Long cashier, Boolean state,
			Pageable pageable) {
		return cashRegisterManagementRepository.findAllCashierManagementByCashierAndState(cashier, state, pageable);
	}

	@Override
	public Page<CashRegisterManagement> findAllCashRegistersMangementByCashRegisterAndCashierAndState(Long cashRegister,
			Long cashier, Boolean state, Pageable pageable) {
		return cashRegisterManagementRepository.findAllCashRegisterManagementByCashRegisterAndCashierAndState(cashRegister, cashier, state, pageable);
	}
	
	protected  void  verifyCashRegisterAndCashier(Long cashier, Long cashRegister) throws ResourceNotFoundByIdException{
		List<CashRegisterManagement> cashRegisterManagementList = cashRegisterManagementRepository.findAllCashRegisterManagementByCashRegisterAndStateOpenened(cashRegister);
		
		List<CashRegisterManagement> cashierManagementList = cashRegisterManagementRepository.findAllCashierrManagementByCashierAndStateOpened(cashier);
		

		if (cashRegisterManagementList.size() > 0) {
			throw new ResourceNotFoundByIdException("caisse deja en activitee");
		}
		
		if (cashierManagementList.size() > 0) {
			throw new ResourceNotFoundByIdException("caissier deja en activitee");
		}
		
	}

	@Override
	public List<CashRegisterManagement> getOpenCaByCashier(Long cashRegister) {
		return cashRegisterManagementRepository.getOpenCaByCashier(cashRegister);
	}

	@Override
	public CashRegisterManagement getCashierrManagementByCashierAndStateOpened(Long cashier) {
		System.out.println("cashier ID ==>" + cashier);
		return cashRegisterManagementRepository.getCashierrManagementByCashierAndStateOpened(cashier);
	}

	public void closeCashRegisterManagement(Long cashier, double realClosingBalance) {
		CashRegisterManagement cashRegisterManagement = cashRegisterManagementRepository.getCashierrManagementByCashierAndStateOpened(cashier);
		cashRegisterManagement.setState(false);
		cashRegisterManagement.setRealClosingBalance(realClosingBalance);
		cashRegisterManagement.setClosingDate(new Date());
		cashRegisterManagementRepository.save(cashRegisterManagement);
	}
}
