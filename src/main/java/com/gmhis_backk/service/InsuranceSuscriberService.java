package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.InsuranceSuscriber;
import com.gmhis_backk.dto.InsuranceSubscriberDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface InsuranceSuscriberService {
	 Page<InsuranceSuscriber> findAllInsuranceSuscriber(Pageable pageable);
	    
	  Page<InsuranceSuscriber> findAllInsuranceSuscriberByActiveAndName(String name,Boolean active, Pageable pageable);
	    
	  Page<InsuranceSuscriber> findAllInsuranceSuscriberByName(String name, Pageable pageable);
	  
	  List<InsuranceSuscriber> findAllInsuranceSuscribers();

	  void deleteInsuranceSuscriber(Integer id);
		
	  Optional<InsuranceSuscriber> getInsuranceSuscriberDetails(Long id); 
	  
	  InsuranceSuscriber addInsuranceSuscriber(InsuranceSubscriberDto insuranceSubscriberDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  InsuranceSuscriber updateInsuranceSuscriber(Long id,InsuranceSubscriberDto insuranceSubscriberDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
}
