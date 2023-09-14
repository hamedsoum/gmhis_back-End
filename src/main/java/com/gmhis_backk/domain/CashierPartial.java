package com.gmhis_backk.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashierPartial {
	
	private Boolean active;
	
	private String firstName;
	
	private String lastName;
}
