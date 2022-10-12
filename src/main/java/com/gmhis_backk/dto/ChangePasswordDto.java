package com.gmhis_backk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Mathurin
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangePasswordDto {
	
	private String username;
	private String newPassword;
	private String confirmPassword;
}
