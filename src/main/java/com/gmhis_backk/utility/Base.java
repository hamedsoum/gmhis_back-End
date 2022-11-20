package com.gmhis_backk.utility;

import org.springframework.beans.factory.annotation.Autowired;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.repository.UserRepository;

public class Base {

	@Autowired
	private UserRepository userRepository;
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
}
