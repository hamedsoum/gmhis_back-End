package com.gmhis_backk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuredDTO {
	private Long id;
	private String active;
	private int coverage;
	private String cardNumber;
	private String isPrincipalInsured;
	private String principalInsuredAffiliation;
	private String principalInsuredName;
	private String principalInsuredContact;
	private Long insurance;
	private Long insuranceSuscriber;
	private Long patient;

}
