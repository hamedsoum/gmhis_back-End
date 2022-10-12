package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.Insurance;
import com.gmhis_backk.dto.InsuranceDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;

@Service
public interface InsuranceService {
	Page<Insurance> findAllInsurance(Pageable pageable);
    
	  Page<Insurance> findAllInsuranceByActiveAndName(String name,Boolean active, Pageable pageable);
	    
	  Page<Insurance> findAllInsuranceByName(String name, Pageable pageable);
	  
	  List<Insurance> findAllInsurances();

	  void deleteInsurance(Integer id);
		
	  Optional<Insurance> getInsuranceDetails(Long id); 
	  
	  Insurance addInsurance(InsuranceDto insuranceDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException;
		
	  Insurance updateInsurance(Long id,InsuranceDto insuranceDto) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
}
