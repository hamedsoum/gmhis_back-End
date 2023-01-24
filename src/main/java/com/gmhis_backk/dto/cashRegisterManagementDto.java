package com.gmhis_backk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class cashRegisterManagementDto {
	
	private String id;
	private int cashRegister;
	private int cashier;
	private Boolean state;

}
