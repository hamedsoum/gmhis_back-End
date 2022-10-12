package com.gmhis_backk.controller;

import org.springframework.stereotype.Controller;

import com.gmhis_backk.AppUtils;

@Controller
public class baseController {
	
	
	
	public void getCurrentUserId() {
		System.out.println(AppUtils.getUsername());
//		return this.userService.findUserByUsername(AppUtils.getUsername()).getId();
	}
}
