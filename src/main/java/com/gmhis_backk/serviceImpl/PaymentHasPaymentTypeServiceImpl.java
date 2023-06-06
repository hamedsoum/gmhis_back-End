/**
 * 
 */
package com.gmhis_backk.serviceImpl;

import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.ActCategory;
import com.gmhis_backk.domain.Payment;
import com.gmhis_backk.domain.PaymentHasPaymentType;
import com.gmhis_backk.domain.PaymentType;
import com.gmhis_backk.dto.PaymentHasPaymentTypeDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.ActRepository;
import com.gmhis_backk.repository.PaymentHasPaymentTypeRepository;
import com.gmhis_backk.service.PaymentHasPaymentTypeService;
import com.gmhis_backk.service.PaymentTypeService;

/**
 * @author mamadouhamedsoumahoro
 *
 */
@Service
@Transactional
public class PaymentHasPaymentTypeServiceImpl implements PaymentHasPaymentTypeService {

	@Autowired
	private PaymentHasPaymentTypeRepository pRepository;
	
	@Autowired
	PaymentTypeService paymentTypeService;
	
	@Autowired
	PaymentHasPaymentTypeService PaymentHasPaymentTypeService;
	
	@Override
	public Page<PaymentHasPaymentType> findPaymentHasPaymentType(Pageable page) {
		return pRepository.findAll(page);
	}

	@Override
	public Optional<PaymentHasPaymentType> findPaymentHasPaymentTypeById(String id) {
		// TODO Auto-generated method stub
		return pRepository.findById(id);
	}

	@Override
	public PaymentHasPaymentType addPaymentHasPaymentType(PaymentHasPaymentTypeDTO pDTO)
			throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException {
		 PaymentType payment  = paymentTypeService.findPaymentTypeById(pDTO.getPaymentID()).orElse(null);
//			if (ObjectUtils.isNotEmpty(actDto.getActCategory()) && actCategory == null) {
//				throw new ResourceNotFoundByIdException("aucune categorie d'acte trouvé pour l'identifiant " );
//			}
		
		return null;
	}

}
