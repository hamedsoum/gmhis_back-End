/**
 * 
 */
package com.gmhis_backk.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.PaymentHasPaymentType;
import com.gmhis_backk.dto.PaymentHasPaymentTypeDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @author mamadouhamedsoumahoro
 *
 */
@Service
@Transactional
public interface PaymentHasPaymentTypeService {
	
		public Page<PaymentHasPaymentType> findPaymentHasPaymentType(Pageable page);
		
		public Optional<PaymentHasPaymentType> findPaymentHasPaymentTypeById(String id);
		
		public PaymentHasPaymentType addPaymentHasPaymentType(PaymentHasPaymentTypeDTO pDTO)throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;

}
