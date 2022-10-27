package com.gmhis_backk.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.CashRegister;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.DefaultNameAndActiveDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.CashRegisterRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.CashRegisterService;

@Service
public class CashRegisterServiceImpl implements CashRegisterService {

	@Autowired
	CashRegisterRepository cashRegisterRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Page<CashRegister> findAllCashRegister(Pageable pageable) {
		return cashRegisterRepository.findAllCashRegister(pageable);
	}

	@Override
	public Page<CashRegister> findAllCashRegisterByActiveAndName(String name, Boolean active, Pageable pageable) {
		return cashRegisterRepository.findAllCashRegisterByActiveAndName(name, active, pageable);
	}

	@Override
	public Page<CashRegister> findAllCashRegisterByName(String name, Pageable pageable) {
		return cashRegisterRepository.findAllCashRegisterByName(name, pageable);
	}

	@Override
	public List<CashRegister> findAllActGroups() {
		return cashRegisterRepository.findAllCashRegisterSimpleList();
	}

	@Override
	public void deleteCashRegister(Integer id) {
		// TODO Auto-generated method stub
	}
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override
	public Optional<CashRegister> getCashRegisterDetails(Long id) {
		return cashRegisterRepository.findById(id);
	}

	@Override @Transactional 
	public CashRegister addCashRegister(DefaultNameAndActiveDto defaultNameAndActiveDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		CashRegister cashRegisterByName = cashRegisterRepository.findByName(defaultNameAndActiveDto.getName());
		if(cashRegisterByName!=null) {
			throw new ResourceNameAlreadyExistException("Le nom de la caisse existe déjà ");  
		} 
		CashRegister cashRegister = new CashRegister();		
		BeanUtils.copyProperties(defaultNameAndActiveDto,cashRegister,"id");
		cashRegister.setCreatedAt(new Date());
		cashRegister.setCreatedBy(getCurrentUserId().getId());
		return cashRegisterRepository.save(cashRegister);
	}

	@Override @Transactional 
	public CashRegister updateCashRegister(Long id, DefaultNameAndActiveDto defaultNameAndActiveDto)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {
		CashRegister updatecashRegister = cashRegisterRepository.findById(id).orElse(null);
		
		if (updatecashRegister == null) {
			 throw new ResourceNotFoundByIdException("Aucune caisse trouvé pour l'identifiant");
		} else {
			CashRegister cashRegisterByName = cashRegisterRepository.findByName(defaultNameAndActiveDto.getName());
			if(cashRegisterByName != null) {
				if(cashRegisterByName.getId() != updatecashRegister.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom de la caisse existe déjà");
				}
			}
		}
		BeanUtils.copyProperties(defaultNameAndActiveDto, updatecashRegister,"id");
		updatecashRegister.setUpdatedAt(new Date());
		updatecashRegister.setUpdatedBy(getCurrentUserId().getId());
		return cashRegisterRepository.save(updatecashRegister);
	}

	@Override
	public List<CashRegister> findActiveActs() {
		
		return cashRegisterRepository.findActiveActs();
	}

	@Override
	public Optional<CashRegister> findCashRegisterById(Long id) {
		return cashRegisterRepository.findById(id);
	}

}
