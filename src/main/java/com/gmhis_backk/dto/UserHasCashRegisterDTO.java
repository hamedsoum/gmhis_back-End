package com.gmhis_backk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHasCashRegisterDTO {

	private Long id;
	private Boolean active;
	private Long user;
	private Long cashRegister;
}
