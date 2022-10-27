package com.gmhis_backk.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.PaymentType;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.PaymentTypeDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.PaymentTypeRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.PaymentTypeService;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public class PaymentTypeServiceImpl implements PaymentTypeService{



	@Autowired
	private PaymentTypeRepository repo;
	
	@Autowired
	private UserRepository userRepository;


	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	@Override
	public PaymentType createPaymentType(PaymentTypeDTO paymentDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException  {
		PaymentType PaymentTypeByName = repo.findByName(paymentDto.getName());
		if(PaymentTypeByName!=null) {
			throw new ResourceNameAlreadyExistException("Le type de payment existe déjà ");  
		} 
		PaymentType paymentType = new PaymentType();		
		BeanUtils.copyProperties(paymentDto,paymentType,"id");
		paymentType.setCreatedAt(new Date());
		paymentType.setCreatedBy(getCurrentUserId().getId());
		return repo.save(paymentType);	
	}

	@Override
	public PaymentType findPaymentTypeByName(String actCode) {
		return repo.findByName(actCode);
	}
	
	
	@Override
	public PaymentType findPaymentTypeByNameAndValue(String name, int value) {
		return repo.findPaymentTypeByNameAndValue(name, value);
	}

	@Override
	public Optional<PaymentType> findPaymentTypeById(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<PaymentType> findPaymentTypes() {
		return repo.findAll();
	}

	@Override
	public Page<PaymentType> findPaymentTypes(Pageable pageable) {
		return repo.findAll(pageable);
	}
	
	@Override
	public List<PaymentType> findActivePaymentTypes() {
		return repo.findActivePaymentTypes();
	}

	@Override
	public Page<PaymentType> findPaymentTypesContaining(String name, Pageable pageable) {
		return  repo.findByNameContainingIgnoreCase(name, pageable);
	}
	
	@Override
	public Page<PaymentType> findByActive(String name, Boolean active, Pageable pageable){
		return repo.findByActive(name, active, pageable);
	}

	@Override
	public PaymentType updatePaymentType(Long id, PaymentTypeDTO pDto)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		PaymentType updatePaymentType = repo.findById(id).orElse(null);
		
		if (updatePaymentType == null) {
			 throw new ResourceNotFoundByIdException("Aucun type de payement trouvé pour l'identifiant");
		} else {
			PaymentType paymentTypeByName = repo.findByName(pDto.getName());
			if(paymentTypeByName != null) {
				if(paymentTypeByName.getId() != updatePaymentType.getId()) {
					throw new ResourceNameAlreadyExistException("Le nom du type de payement existe déjà");
				}
			}
		}
		BeanUtils.copyProperties(pDto, updatePaymentType,"id");
		updatePaymentType.setUpdatedAt(new Date());
		updatePaymentType.setUpdatedBy(getCurrentUserId().getId());
		return repo.save(updatePaymentType);
	}

	

}
