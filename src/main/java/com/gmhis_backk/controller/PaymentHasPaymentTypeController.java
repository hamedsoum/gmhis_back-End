/**
 * 
 */
package com.gmhis_backk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.Act;
import com.gmhis_backk.domain.PaymentHasPaymentType;
import com.gmhis_backk.dto.ActDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.PaymentHasPaymentTypeService;

import io.swagger.annotations.ApiOperation;

/**
 * @author mamadouhamedsoumahoro
 *
 */

@RestController
@RequestMapping("/PaymentHasPaymentType")
public class PaymentHasPaymentTypeController {
	@Autowired
	PaymentHasPaymentTypeService paymentHasPaymentTypeService;
	
	
	@PostMapping()
	@ApiOperation("Ajouter un PaymentHasPaymentType")
	public ResponseEntity<PaymentHasPaymentType> addPaymentHasPaymentType(@RequestBody ActDTO actDto) throws ResourceNameAlreadyExistException,
	ResourceNotFoundByIdException {
		
		return null;
//		Act act = paymentHasPaymentTypeService.addAct(actDto);
//		return new ResponseEntity<PaymentHasPaymentType>(act,HttpStatus.OK);
	}

}
