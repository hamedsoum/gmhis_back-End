package com.gmhis_backk.serviceImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Role;
import com.gmhis_backk.dto.RoleAuthorityDto;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.repository.AuthorityRepository;
import com.gmhis_backk.repository.RoleRepository;
import com.gmhis_backk.service.EventLogService;
import com.gmhis_backk.service.RoleService;

import static com.gmhis_backk.constant.RoleImplConstant.*;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author adjara
 *
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	EventLogService eventLogServive;

	@Autowired
	AuthorityRepository authorityRepo;

//	Role role = null;

	@Override
	public Role addRole(Role role) throws ResourceNameAlreadyExistException {

		Role roleByName = roleRepo.findByName(role.getName());

		if (roleByName != null)
			throw new ResourceNameAlreadyExistException(ROLE_NAME_ALREADY_EXISTS);

		Role addRole = new Role();
		BeanUtils.copyProperties(role, addRole, "id");
		Role newRole = roleRepo.save(addRole);
//		  eventLogServive.addEvent("creation du role: " + newRole.getName(), newRole.getClass().getSimpleName());
		return newRole;
	}

	@Override
	public Role updateRole(Integer id, Role role)
			throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException {

		Role roleToUpdate = roleRepo.findById(id).orElse(null);

		if (roleToUpdate == null) {
			throw new ResourceNotFoundByIdException(NO_ROLE_FOUND_BY_ID + id);
		} else {

			Role getRoleByName = roleRepo.findByName(role.getName());

			if (getRoleByName != null) {
				if (getRoleByName.getId() != roleToUpdate.getId()) {
					throw new ResourceNameAlreadyExistException(ROLE_NAME_ALREADY_EXISTS);
				}
			}
		}

		BeanUtils.copyProperties(role, roleToUpdate, "id");
		Role updatedRole = roleRepo.save(roleToUpdate);
//		eventLogServive.addEvent("modification du role: " + updatedRole.getName(),  updatedRole.getClass().getSimpleName());
		return updatedRole;
	}

	@Override
	public Page<Role> findAll(Pageable pageable) {
		return roleRepo.findAll(pageable);
	}

	@Override
	public Role findRoleById(Integer id) {
		return roleRepo.findById(id).orElse(null);
	}

	@Override
	public List<Role> findAllActive() {

		return roleRepo.findAllActive();
	}

	@Override
	public List<String> findRolesByUserId(Long userId) {

		return roleRepo.findRolesByUserId(userId);
	}

	@Override
	public Role setAuthorities(RoleAuthorityDto roleAuthorityDto) throws ResourceNotFoundByIdException {
		Role role = roleRepo.findById(roleAuthorityDto.getRole()).orElse(null);
		if (role == null) {
			throw new ResourceNotFoundByIdException(NO_ROLE_FOUND_BY_ID);
		}

		List<Object> authorities = authorityRepo.findAuthoritiesByids(roleAuthorityDto.getAuthorities());
		role.setAuthorities(StringUtils.join(authorities, ","));

		return role;
	}

	@Override
	public Page<Role> findByNameAndIsActive(String name, Boolean isActive, Pageable pageable) {
		return roleRepo.findByNameAndIsActive(name, isActive, pageable);
	}

	@Override
	public Page<Role> findByName(String name, Pageable pageable) {

		return roleRepo.findByName(name, pageable);
	}

	@Override
	public Optional<Role> findById(int id) {
		return roleRepo.findById(id);
	}

	@Override
	public List<Role> findByUserId(Long userId) {
		return roleRepo.findByUser(userId);
	}

}
