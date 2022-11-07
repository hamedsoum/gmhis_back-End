package com.gmhis_backk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillHasInsuredDto {
	Long admission;
	Long bill;
	Long insured;
	int insuredCoverage;
	int insuredPart;
}
