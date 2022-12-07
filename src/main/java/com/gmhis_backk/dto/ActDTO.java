package com.gmhis_backk.dto;



import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Adjara
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActDTO {

	private Long id;
	private String name;
	private Boolean active;	
	private String description;
	private String codification;
	private int coefficient;
	private Long actCategory;
	private Long actCode;
	private Long actGroup;
	private UUID medicalAnalysisSpeciality;

}
