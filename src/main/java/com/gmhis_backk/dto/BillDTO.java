package com.gmhis_backk.dto;

import java.util.List;

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
public class BillDTO {
	private Long Id;
//	private String billStatus;
//	private String billNumber;
    private Long insured;
//	private String partTakenCareOfNumber;
	private String patientType;
	private int discountInCfa;
	private int patientPart;
	private int partTakenCareOf;
	private int discountInPercentage;
//	private String accountNumber;
	private Long convention;
	private String billType;
	private Long admission;
	private List<AdmisionHasActDTO> acts;
	private List <BillHasInsuredDto> insuredList;
}
