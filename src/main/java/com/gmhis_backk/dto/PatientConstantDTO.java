package com.gmhis_backk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Pascal
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientConstantDTO {
	private Long id;
	private String value;
	private Long patient;
	private Long constant;
	private String observation;
}
