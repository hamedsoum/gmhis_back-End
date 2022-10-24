package com.gmhis_backk.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Service;
import com.gmhis_backk.dto.ServiceDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@org.springframework.stereotype.Service 
@Transactional
public interface ServiceService {

	public Service saveService(ServiceDTO serviceDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException ;
	
	public Service updateService(Long id, ServiceDTO serviceDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException ;

	public Service findServiceByName(String service);
	
	public Service findServiceById(Long id);

	public List<Service> findServices();
	
	public Page<Service> findServices(Pageable pageable);
	
	public Page<Service> findServicesContaining(String name,Pageable pageable);
	
	public List<Service> findActiveServices();
	
	public Page<Service> findByActive(String namme, Boolean active, Pageable pageable);
}
