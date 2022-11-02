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
public class PatientConstantTypeDTO {
	private Long id;
	private String name;
	private Boolean active;
	private Long constantDomain;
	private String description;
	private String shortName;
	private Long unitOfMesure;
	private int significantDigits = 0;
	private String resultType;

}
