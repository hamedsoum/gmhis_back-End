package com.gmhis_backk.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashRegisterManagementDto {
	private String id;
	private Long cashRegister;
	private Long cashier;
	private Boolean state;
	private double openingBalance;
	private Date openingDate;

}
