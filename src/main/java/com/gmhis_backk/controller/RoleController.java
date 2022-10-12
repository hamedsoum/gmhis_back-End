package com.gmhis_backk.controller;

import static com.gmhis_backk.constant.SecurityConstant.ACTION_PERFORM_DENIED_MESSAGE;
import static org.springframework.http.HttpStatus.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmhis_backk.domain.Role;
import com.gmhis_backk.dto.RoleAuthorityDto;
import com.gmhis_backk.exception.ExceptionHandling;
import com.gmhis_backk.exception.domain.ApplicationErrorException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.service.AuthorityService;
import com.gmhis_backk.service.CurrentUserService;
import com.gmhis_backk.service.RoleService;

import io.swagger.annotations.ApiOperation;


/**
 * 
 * @author adjaratou
 *
 */

@RestController
@RequestMapping("/role")
public class RoleController  extends  ExceptionHandling{

	@Autowired
	RoleService roleService;
	
	@Autowired
	AuthorityService authorityService;
	
	@Autowired
	CurrentUserService currentUserService;
    
//	Role role = null; 
	
	@ApiOperation(value = "Ajouter un nouveau role dans le système")
	@PostMapping(value = "/add")
	public ResponseEntity<Role> addRole(@Valid @RequestBody Role role) throws ResourceNameAlreadyExistException, ApplicationErrorException {
//		if(!currentUserService.checkIfCurrentUserHasAuthority("role:add")) throw new ApplicationErrorException(ACTION_PERFORM_DENIED_MESSAGE);
		Role newRole = roleService.addRole(role);
		return new ResponseEntity<Role>(newRole, OK);
	}
	
	@ApiOperation(value = "Modifier un role dans le système")
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Role> updateRole(@PathVariable("id") Integer id,@Valid @RequestBody Role role) throws ResourceNotFoundByIdException, ResourceNameAlreadyExistException, ApplicationErrorException{
		if(!currentUserService.checkIfCurrentUserHasAuthority("role:update")) throw new ApplicationErrorException(ACTION_PERFORM_DENIED_MESSAGE);
		Role updateRole = roleService.updateRole(id, role);
		return new ResponseEntity<Role>(updateRole, OK);
	}
	
	
	@ApiOperation(value = "Lister paginée de tous les roles")
	@GetMapping(value = "/list")
	public ResponseEntity<Map<String, Object>>listRole(
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String isActive,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "name,asc") String[] sort) throws ApplicationErrorException {
		
//		if(!currentUserService.checkIfCurrentUserHasAuthority("role:list")) throw new ApplicationErrorException(ACTION_PERFORM_DENIED_MESSAGE);
		
		  List<Role> roles = new ArrayList<Role>();	  
          Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
	      Pageable paging = PageRequest.of(page, size, Sort.by(dir,sort[0]));
	      Page<Role> pageRoles;
	      pageRoles = roleService.findAll(paging);
	      
	      if (StringUtils.isNotBlank(isActive)) { 
	    	   pageRoles = roleService.findByNameAndIsActive(name.trim(), Boolean.parseBoolean(isActive), paging);  
	      } else if(StringUtils.isNotBlank(name)) {  
	    	  pageRoles = roleService.findByName(name.trim(), paging);
	      }
	      

	      roles = pageRoles.getContent();

	      Map<String, Object> response = new HashMap<>();
	      response.put("items", roles);
	      response.put("currentPage", pageRoles.getNumber());
	      response.put("totalItems", pageRoles.getTotalElements());
	      response.put("totalPages", pageRoles.getTotalPages());
		  response.put("size", pageRoles.getSize());
		  response.put("firstPage", pageRoles.isFirst());
		  response.put("lastPage", pageRoles.isLast());
		  response.put("empty", pageRoles.isEmpty());

		return new ResponseEntity<>(response, OK);
	}
	
	
	@ApiOperation(value = "Lister tous les roles actifs")
	@GetMapping(value = "/active-list")
	public ResponseEntity<List<Role>> listActiveRole() {
		List<Role> listRole = roleService.findAllActive();
		return new ResponseEntity<>(listRole, OK);
	}
	
	@ApiOperation(value = "Attribuer des permissions a un role")
	@PutMapping(value = "/set-authorities/{id}")
	public ResponseEntity<Role> setAuthorities(@PathVariable Integer id , @RequestBody RoleAuthorityDto roleAuthorityDto) throws ResourceNotFoundByIdException, ApplicationErrorException {
//		if(!currentUserService.checkIfCurrentUserHasAuthority("role:set_permission")) throw new ApplicationErrorException(ACTION_PERFORM_DENIED_MESSAGE);
		Role role = roleService.setAuthorities(roleAuthorityDto);
		return new ResponseEntity<Role>(role, OK);
	}
	
	@ApiOperation(value = "retourne la list des ids des permissions du role")
	@GetMapping(value = "/get-authority-ids/{id}")
	public List<Integer> getRoleAuthorityIds(@PathVariable Integer id){
		Role role = roleService.findRoleById(id);
		if(role != null) {
			if(StringUtils.isNotBlank(role.getAuthorities())) {
				
				return authorityService.findAuthorityIdsByNames(role.getAuthorities());
			}
		}
		
		return null;
	}
	
	@GetMapping("/get-detail/{id}")
	@ApiOperation("detail d'un transporteur")
	public  ResponseEntity<Optional<Role>> getDetail(@PathVariable int id){
		Optional<Role> role = roleService.findById(id);
		return new ResponseEntity<>(role,HttpStatus.OK);
	}
	
	
//	@GetMapping("/all-list")
//	@ApiOperation("Liste de tous les roles dans le système")
//	public  ResponseEntity<List<Role>> getAllRole(){
//		List<Role>  roleList = roleService.findAllRole();
//		return new ResponseEntity<>(roleList,HttpStatus.OK);
//	}

	
	
}
