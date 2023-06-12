package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.Role;
import com.gmhis_backk.dto.RoleAuthorityDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;


/**
 * 
 * @author adjara
 *
 */

@Service
public interface RoleService {

	Role addRole(Role role) throws ResourceNameAlreadyExistException;
	
	Role findRoleById(Integer id);
	
	Role updateRole(Integer id, Role role) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException;
	
	Page<Role> findAll(Pageable pageable);
	
	Page<Role> findByNameAndIsActive(String name, Boolean isActive, Pageable pageable);
	
	Page<Role> findByName(String name, Pageable pageable);
	
	Role findByRoleName(String name);
	
	List<Role> findAllActive();
	
    List<String> findRolesByUserId(Long userId);
    
    List<Role> findByUserId(Long userId);
    
    Role setAuthorities(RoleAuthorityDto roleAuthorityDto) throws ResourceNotFoundByIdException;
    
    Optional<Role> findById(int id);
    
//    List<Role> findAllRole();


}
