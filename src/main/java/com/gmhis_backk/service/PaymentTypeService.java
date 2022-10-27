package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.PaymentType;
import com.gmhis_backk.dto.PaymentTypeDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Service
@Transactional
public interface PaymentTypeService {

	public PaymentType createPaymentType(PaymentTypeDTO a) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException ;
	
	public PaymentType updatePaymentType(Long id, PaymentTypeDTO a) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException ;

	public PaymentType findPaymentTypeByName(String paymentType);
		
	public PaymentType findPaymentTypeByNameAndValue(String name, int value);
	
	public Optional<PaymentType> findPaymentTypeById(Long id);

	public List<PaymentType> findPaymentTypes();
	
	public Page<PaymentType> findPaymentTypes(Pageable pageable);
	
	public Page<PaymentType> findPaymentTypesContaining(String name,Pageable pageable);
	
	public List<PaymentType> findActivePaymentTypes();
	
	public Page<PaymentType> findByActive(String name, Boolean active, Pageable pageable);
}
