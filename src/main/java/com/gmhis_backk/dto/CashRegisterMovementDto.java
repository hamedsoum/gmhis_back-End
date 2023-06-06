package com.gmhis_backk.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashRegisterMovementDto {

	private String libelle;
	
	private double debit;
	
	private double credit;
	
	private String prestationNumber;
	
	private Long cashRegister;
	
	private Date date;
	
	private long userId;
}
