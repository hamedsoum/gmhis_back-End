package com.gmhis_backk.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Service;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.ServiceDTO;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.ServiceRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ServiceService;

@org.springframework.stereotype.Service
public class serviceServiceImpl implements ServiceService {

	@Autowired 
	ServiceRepository serviceRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override @Transactional
	public Service saveService(ServiceDTO serviceDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException  {
		Service serviceByName = serviceRepository.findByName(serviceDto.getName());
		if(serviceByName!=null) {
			throw new ResourceNameAlreadyExistException("Le nom du service existe déjà ");  
		} 
		Service service = new Service();		
		BeanUtils.copyProperties(serviceDto,service,"id");
		service.setCreatedAt(new Date());
		service.setCreatedBy(getCurrentUserId().getId());
		return serviceRepository.save(service);	
	}

	@Override @Transactional
	public Service updateService(Long id, ServiceDTO serviceDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException  {
		return null;
	}

	@Override
	public Service findServiceByName(String service) {
		return serviceRepository.findByName(service);
	}

	@Override
	public Service findServiceById(Long id) {
		return serviceRepository.findById(id).orElse(null);
	}

	@Override
	public List<Service> findServices() {
		return serviceRepository.findAll();
	}

	@Override
	public Page<Service> findServices(Pageable pageable) {
		return serviceRepository.findAll(pageable);
	}

	@Override
	public Page<Service> findServicesContaining(String name, Pageable pageable) {
		return serviceRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public List<Service> findActiveServices() {
		return serviceRepository.findActiveServices();
	}

	@Override
	public Page<Service> findByActive(String namme, Boolean active, Pageable pageable) {
		return serviceRepository.findByActive(namme, active, pageable);
	}
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

}
