package com.gmhis_backk.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gmhis_backk.domain.User;
import com.gmhis_backk.domain.UserPrincipal;
import com.gmhis_backk.enumeration.RoleEnum;

/**
 * 
 * @author adjaratou
 *
 */
@Service
public class CurrentUserService {

	@Autowired
	UserService userService;

	public Long getCurrentUserId() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findUserByUsername(username);
		return user.getId();

	}

	public String getCurrentUserFullName() {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findUserByUsername(username);
		return user.getLastName() + " " + user.getFirstName();

	}

	public User getCurrentUser() {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findUserByUsername(username);
		return user;

	}

	public Boolean checkIfCurrentUserHasRole(Integer role) {

		String[] roleIds = getCurrentUser().getRoleIds().split(",");

		int size = roleIds.length;
		Integer[] arr = new Integer[size];
		for (int i = 0; i < size; i++) {
			arr[i] = Integer.parseInt(roleIds[i]);
		}

		List<Integer> intList = new ArrayList<>(Arrays.asList(arr));
		boolean hasRole = intList.contains(role) || intList.contains(RoleEnum.super_admin.getRole()) ? true : false;
		return hasRole;
	}

	public Boolean checkIfCurrentUserHasAuthority(String authority) {

		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = userService.findUserByUsername(user.getName());

    	if(StringUtils.isNoneBlank(currentUser.getAuthorities())) {
    		List<String> userAutorities = Arrays.asList(currentUser.getAuthorities().split(","));
        	
        	if (userAutorities.contains("tous") || userAutorities.contains(authority)) {
    			return true;
    		} else {
    			return false;
    		}
    	}
    	return false;
    	
		
	}

}
