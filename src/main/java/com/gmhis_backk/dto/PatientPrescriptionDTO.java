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
public class PatientPrescriptionDTO {

	private Long id;
	private String drugs;  
	private String drugServed;
	private Long admission;
	private Long Examination;

}
