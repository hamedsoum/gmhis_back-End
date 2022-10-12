package com.gmhis_backk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author adjara
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResetPasswordDto {
	
	private String code;
	private String newPassword;
	private String confirmPassword;
}
