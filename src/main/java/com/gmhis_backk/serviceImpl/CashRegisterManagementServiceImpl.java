package com.gmhis_backk.serviceImpl;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.CashRegister;
import com.gmhis_backk.domain.CashRegisterManagement;
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
			
			
		System.out.println(cashRegisterManagementDto.getCashier());
		CashRegisterManagement cashRegisterManagement = new CashRegisterManagement();	
		BeanUtils.copyProperties(cashRegisterManagementDto,cashRegisterManagement,"id");
		cashRegisterManagement.setCashier(cashier);
		cashRegisterManagement.setCashRegister(cashRegister);
		cashRegisterManagement.setOpeningDate(new Date());
		cashRegisterManagementRepository.save(cashRegisterManagement);

		return null;
	}

	@Override
	public CashRegisterManagement updateCashRegisterManagement(UUID id, CashRegisterManagementDto cashRegisterManagementDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CashRegisterManagement> findAllCashRegistersMangement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CashRegisterManagement> findAllCashRegistersMangement(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CashRegisterManagement> findAllCashRegistersMangementByCashRegister(Long cashRegister,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
